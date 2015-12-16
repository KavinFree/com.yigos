Ext.define('Ext.yigosui.form.ComboTreeField',{
	extend : 'Ext.form.field.ComboBox',
	alternateClassName: 'Ext.yigosui.form.ComboTreeField',
	xtype: 'ux-combotreeField',
	alias: [
	        'widget.combotreeField'
	],
	requires : 'Ext.tree.Panel',
	displayField : 'text',
	valueField : 'id',
	filterLeafOnly : true,
	defaultTreeConfig : {
		floating : true,
		autoScroll : true,
		selectPath : function(path, field, separator, callback, scope) {
			var me = this, keys, last;
			field = field || me.getRootNode().idProperty;
			separator = separator || '/';
			keys = path.split(separator);
			last = keys.pop();
			me.expandPath(keys.join('/'), field, separator,
					function(success, node) {
						var doSuccess = false;
						if (success && node) {
							node = node.findChild(field, last);
							if (node) {
								me.getSelectionModel().select(node, true);
								Ext.callback(callback, scope || me, [ true, node ]);
								doSuccess = true;
							}
						} else if (node === me.getRootNode()) {
							doSuccess = true;
						}
						Ext.callback(callback, scope || me, [ doSuccess, node ]);
					}, me);
		}
	},
	createPicker : function() {
		var me = this, picker;
		picker = me.picker = Ext.create('Ext.tree.Panel', Ext.apply({
					height : 200,
					ownerCt : me.ownerCt,
					store : me.treeStore,
					selModel : {
						mode : me.multiSelect ? 'SIMPLE' : 'SINGLE'
					}
				}, me.treeConfig, me.defaultTreeConfig));
		me.mon(picker, {
			selectionchange : me.onTreeSelectionchange,
			scope : me
		});
		return picker;
	},
	onTreeSelectionchange : function(view, selections) {
		var me = this, values = [];
		Ext.Array.each(selections, function(item) {
			values.push(item.get(me.valueField));
		});
		me.setValue(values, false);
		if (!me.multiSelect) {
			me.collapse();
		}
	},
	createStore : function() {
		var me = this, treeStore = me.treeStore, data = [], 
		createData = function(node, arr) {
			var nodeData = {};
			if (node) {
				data.push(node.data);
				Ext.apply(nodeData, node.data);
				delete nodeData.loaded;
				if (arr) {
					arr.push(nodeData);
				}
				if (node.hasChildNodes()) {
					nodeData.children = [];
					node.eachChild(function(childNode) {
						createData(childNode,nodeData.children);
					});
				}
			}
			return nodeData;
		};
		if (treeStore.queryMode !== 'remote') {
			treeStore.treeData = createData(treeStore.getRootNode());
		}
		me.store = Ext.create('Ext.data.Store', {
			model : treeStore.model,
			proxy : treeStore.proxy,
			data : data,
			queryMode : 'local'
		});
	},
	initComponent : function() {
		var me = this;
		if (!me.treeStore) {
			Ext.Error.raise('Either a valid treeStore must be configured on the combo.');
		}
		me.createStore();
		me.queryMode = 'local';
		me.callParent();
	},
	getPath : function(node) {
		return node.parentNode ? this.getPath(node.parentNode)
				+ '/' + node.getId() : '/' + node.getId();
	},
	doAutoSelect : function() {
		var me = this, picker = me.picker;
		if (picker && me.autoSelect && me.store.getCount() > 0) {
			var ExtArray = Ext.Array, root = me.treeStore.getRootNode();
			ExtArray.each(ExtArray.from(me.value), function(item) {
				if (item == root.getId()) {
					picker.getSelectionModel().select(root, true);
				} else {
					var node = root.findChild(me.valueField, item, true);
					if (node) {
						picker.selectPath(me.getPath(node));
					}
				}
			});
		}
	},
	alignPicker : function() {
		var me = this, picker, isAbove, aboveSfx = '-above';
		if (me.isExpanded) {
			picker = me.getPicker();
			if (me.matchFieldWidth) {
				picker.setWidth(me.bodyEl.getWidth());
			}
			if (picker.isFloating()) {
				picker.alignTo(me.inputEl, me.pickerAlign,
						me.pickerOffset);
				isAbove = picker.el.getY() < me.inputEl.getY();
				me.bodyEl[isAbove ? 'addCls' : 'removeCls']
						(me.openCls + aboveSfx);
				picker.el[isAbove ? 'addCls' : 'removeCls']
						(picker.baseCls + aboveSfx);
			}
		}
	},
	doQuery : function(queryString, forceAll) {
		queryString = queryString || '';
		var me = this, qe = {
			query : queryString,
			forceAll : forceAll,
			combo : me,
			cancel : false
		}, store = me.treeStore, isLocalMode = store.queryMode !== 'remote';
		if (me.fireEvent('beforequery', qe) === false || qe.cancel) {
			return false;
		}
		queryString = qe.query;
		forceAll = qe.forceAll;
		if (forceAll || queryString.length >= me.minChars) {
			me.expand();
			if (!me.queryCaching || me.lastQuery !== queryString) {

				me.lastQuery = queryString;
				if (isLocalMode) {
					store.setRootNode(Ext.clone(store.treeData));
					me.filter(store.getRootNode(),
						new RegExp('^' + Ext.String.escapeRegex(queryString), 'i'));
					me.inputEl.focus();
				} else {
					store.load({
						params : me.getParams(queryString),
						callback : Ext.Function.bind(
								me.syncStore, me)
					});
				}
			}
			if (me.getRawValue() !== me.getDisplayValue()) {
				me.ignoreSelection++;
				me.picker.getSelectionModel().deselectAll();
				me.ignoreSelection--;
			}
			if (isLocalMode) {
				me.doAutoSelect();
			}
			if (me.typeAhead) {
				me.doTypeAhead();
			}
		}
		return true;
	},
	filter : function(node, regExp) {
		var me = this;
		if (node.hasChildNodes()) {
			var n = node.findChildBy(function(node) {
				return regExp.test(node.get(me.displayField))
						&& (me.filterLeafOnly ? node.isLeaf() : true);
			}, null, true);
			if (n) {
				for (var i = 0; i < node.childNodes.length; i++) {
					if (me.filter(node.getChildAt(i), regExp)) {
						i--;
					}
				}
			} else if (me.filterLeafOnly) {
				return me.removeNode(node);
			} else {
				if (regExp.test(node.get(me.displayField))) {
					node.removeAll(true);
				} else {
					return me.removeNode(node);
				}
			}
		} else {
			if (!regExp.test(node.get(me.displayField))) {
				return me.removeNode(node);
			}
		}
	},
	removeNode : function(node) {
		if (node.isRoot()) {
			this.treeStore.removeAll();
		} else {
			node.remove(true);
		}
		return true;
	},
	syncStore : function(nodes, operation, success) {
		if (success) {
			var me = this, data = [], createData = function(node) {
				if (node) {
					data.push(node.data);
					if (node.hasChildNodes()) {
						node.eachChild(function(childNode) {
							createData(childNode);
						});
					}
				}
			}
			Ext.Array.each(nodes, function(node) {
				createData(node);
			});
			me.store.loadData(data);
		}
	},
	onTypeAhead : function() {
		var me = this, displayField = me.displayField, 
			node = me.treeStore.getRootNode().findChild(displayField, me.getRawValue(), true), 
			picker = me.getPicker(), newValue, len, selStart;
		if (node) {
			newValue = node.get(displayField);
			len = newValue.length;
			selStart = me.getRawValue().length;
			picker.selectPath(me.getPath(node));
			if (selStart !== 0 && selStart !== len) {
				me.setRawValue(newValue);
				me.selectText(selStart, newValue.length);
			}
		}
	}
});
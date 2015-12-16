Ext.define('auth.RolePanel', {
	
	constructor: function(config) {
		var me = this;
		me.configParam = config.configParam;
		me.init();
	},
	
	init : function(){
		var me = this;
		me.initStore();
		me.initGrid();
		me.initView();
		me.createFormWindow();
	},
	
	initStore : function(){
		var me = this;
		me.store = Ext.create('Ext.data.TreeStore', {
			autoLoad: true,
			nodeParam: 'pid',
			defaultRootId: '0',
			defaultRootText: '顶级角色',
			// 数据代理
            proxy : {
				type : 'ajax',
				url : me.configParam.urlsPath.list,
				reader: {
					type:'json',
					root: 'result',
	            	totalProperty: 'totalCount'
				}
			},
			fields: [
		    	{ name: 'id',    type: 'string' },
		    	{ name: 'pid',  type: 'string' },
		    	{ name: 'text',  type: 'string' },
		    	{ name: 'code',  type: 'string' },
		        { name: 'name',  type: 'string' },
		        { name: 'orderFlag',  type: 'string' },
		        { name: 'sysDelFlag',  type: 'string' }
	        ]
		});
	},
	
	initGrid : function(){
		var me = this;
		me.menuGrid = Ext.create('Ext.tree.Panel', {
			layout: 'fit',
	        loadMask: true,
	        useArrows: true,
	        rootVisible: false,
	        columnLines: true,//列分割线
	        rowLines: true,//行分割线
	        multiSelect: true,
	        store: me.store,
          	dockedItems:[{
	        	xtype:'toolbar',
	        	dock:'top',
	        	ui:'footer',
	        	layout:{
	        		pack:'left'
	        	},
	        	items:[{text:me.configParam.i18n.addButton, minWidth:80, id:'add-button',
	        		xtype: 'button', disabled:false, //iconCls:'table_add',//图标样式
	        		handler:function(){
	        			me.updateFlag = false;
	        			me.formPanel.getForm().reset();
	        			me.loadFormData(null);
	        		}
	        	}, {text:me.configParam.i18n.modifyButton, minWidth:80, id:'edit-button',
	        		xtype: 'button', disabled:true,
	        		handler:function(){
	        			var selModel = me.menuGrid.getSelectionModel();
		                var ids = [];
		                if(selModel.hasSelection()){
		                	me.updateFlag = true;
		                	var selected = selModel.getSelection();
		                	Ext.each(selected, function(item){
                                ids.push(item.data.id);
                            });
		                	me.loadFormData(ids);
		                }
	        		}
	        	}, {text:me.configParam.i18n.deleteButton, minWidth:80, id:'delete-button',
	        		xtype: 'button', disabled:true, 
	        		handler:function(){
	        			var selModel = me.menuGrid.getSelectionModel();
		                var ids = [];
		                if(selModel.hasSelection()){
		                	var selected = selModel.getSelection();
		                	Ext.each(selected, function(item){
                                ids.push(item.data.id);
                            });
		                	me.createDeleteWindow(ids);
		                }
	        		}
	        	}]
	        }],
	        columns: [
  			    { text: me.configParam.i18n.id, dataIndex: 'id', hidden:true},
  			    { text: me.configParam.i18n.pid, dataIndex: 'pid', hidden:true},
  			    { text: me.configParam.i18n.name, dataIndex: 'name', xtype: 'treecolumn', flex: 1 },//iconCls
  			    { text: me.configParam.i18n.code, dataIndex: 'code',flex: 1 },
  	        	{ text: me.configParam.i18n.orderFlag, dataIndex: 'orderFlag',flex: 1 },
  	        	{ text: me.configParam.i18n.sysDelFlag, dataIndex: 'sysDelFlag', renderer: me.sysDelFlagFormat }
  	        ],
	        listeners: {
		    	selectionchange: function(sm, selections){
		    		if(selections.length===1){
		    			Ext.getCmp('edit-button').setDisabled(false);
		    			Ext.getCmp('delete-button').setDisabled(false);
		    		}else if(selections.length>1){
		    			Ext.getCmp('edit-button').setDisabled(true);
		    			Ext.getCmp('delete-button').setDisabled(false);
		    		}else if(selections.length<1){
		    			Ext.getCmp('edit-button').setDisabled(true);
		    			Ext.getCmp('delete-button').setDisabled(true);
		    		}
		    	}
		    }
	    });
	},
	
	initView : function(){
		var me = this;
		me.view = Ext.create('Ext.Viewport',{
			layout : 'fit',
			items: me.menuGrid
		});
	},
	
	updateFlag : false,
	
	loadMask : null,
	
	createFormWindow : function(){
		var me = this;
		
		me.formPanel = Ext.create('Ext.form.Panel', {
        	minHeight: 400,
        	width: '98%',
            border: false,
            fieldDefaults: {
                labelWidth: 70,
                labelAlign: "right",
                allowBlank: true
            },
        	items:[
        		{xtype : 'hiddenfield', name: 'id', id:'formId'},
				{
                    xtype: "fieldcontainer", layout: "hbox", defaults:{xtype:'textfield'},
                    items: [
                    	{
		        			fieldLabel : '编号', name: 'code', width:'30%', allowBlank: false
						},
						{
		        			fieldLabel : '名称', name: 'name', flex: 1, allowBlank: false
						},
						{
							fieldLabel : '状态', width:'22%', allowBlank: false, xtype: 'radiogroup',
							items: [
								{boxLabel: '激活', name: 'sysDelFlag', inputValue: 0, checked: true},
								{boxLabel: '禁用', name: 'sysDelFlag', inputValue: 1}
							]
						}
                    ]
				},
				{
                    xtype: "fieldcontainer", layout: "hbox", defaults:{xtype:'treepicker'},
                    items: [
                    	{
		        			fieldLabel : '上层角色', name: 'pid', value:'0',
							multiSelect: true,
					        store: me.store,
							displayField : 'text',
							valueField : 'id',
							editable : true, //启用编辑，主要是为了清空当前的选择项
							enableKeyEvents : true, //激活键盘事件
							forceSelection : true,// 只能选择下拉框里面的内容
							minPickerHeight: 270,
							width: '78%',
							rootVisible: false
						}
					]
				},
				{
					xtype: "fieldcontainer", layout: "hbox", defaults:{xtype:'ckeditor'},//htmleditor
					items:[
						{
							fieldLabel : '描述', name: 'remark', flex: 1, allowBlank: false
						}
					]
				},
				{
                    xtype: "fieldcontainer", layout: "hbox", defaults:{xtype:'textfield'},
                    items: [
						{
		        			fieldLabel : '创建人', name: 'sysCreateUser', width:'25%'
						},
						{
		        			fieldLabel : '更新人', name: 'sysUupdateUser', width:'25%'
						},
						{
		        			fieldLabel : '创建时间', name: 'sysCreateDateTime', xtype : 'datetimefield', width:'24%', value: new Date
						},
						{
		        			fieldLabel : '更新时间', name: 'sysUpdateDateTime', xtype : 'datetimefield', width:'24%', value: new Date
						}
					]
				}
        	]
        });
        
        me.formWindow = Ext.create('Ext.ux.window.FormWindow', {
            title: me.configParam.i18n.title,
			items: me.formPanel,
			buttons: [
				{
	                text: '保存',
	                handler : function() {
	                	if (!(me.formPanel.getForm().isValid())){
	                		return ;
	                	}
	                	
	                	var savePath = "";
	                	if(me.updateFlag){
	                		savePath = me.configParam.urlsPath.updateRole;
	                	}else{
	                		savePath = me.configParam.urlsPath.saveRole;
	                	}
	                	me.formPanel.getForm().submit({
	                            waitMsg:'请稍等,提交中...',
	                            url : savePath,
	                            method : 'post',
	                            success : function(form, dataResult) {
	                                Ext.Msg.alert('提示', '操作成功');
	                                me.formWindow.hide();
	                                me.store.load();
	                            },
	                            failure : function(form, dataResult) {
	                                Ext.Msg.alert('提示', '操作失败');
	                            }
	                        });
	                }
				},
				{
	                text: '重置',
	                handler : function() {
	                    me.formPanel.getForm().reset();
	                }
	            }
            ]
        });
	},
	
	loadFormData : function(ids){
		var me = this;
		me.formWindow.show();
		me.loadMask = new Ext.LoadMask({
			target : me.formWindow,
			msg:"请稍等,加载中..."
		});
		me.loadMask.show();
		
		if(typeof(ids)==='undefined'
		|| ids===null || ids.length===0){
			if(me.loadMask != undefined||me.loadMask != null){
				me.loadMask.hide();
			}
			return ;
		}
		Ext.Ajax.request({
			url : me.configParam.urlsPath.findRole,
			params : {id : ids},
			method : 'POST',
			timeout: 60000,
			callback: function (options, success, response) {
				if(me.loadMask != undefined||me.loadMask != null){ 
					me.loadMask.hide();
				}
            	if (success) {
            		var jsonRecord = Ext.JSON.decode(response.responseText);
					me.formPanel.getForm().setValues(jsonRecord.result);
				}else{
					Ext.MessageBox.alert('提示', '请求超时或网络故障');
				}
			}
		});
	},
	
	createDeleteWindow : function(ids){
		var me = this;
		if(typeof(ids)==='undefined' || ids===null || ids.length===0){
			Ext.MessageBox.alert('提示', '请选择数据进行操作');
			return ;
		}
		
		Ext.Msg.confirm("提示","请确定要执行删除操作吗?", function (btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : me.configParam.urlsPath.deleteRole,
					params : {ids : ids},
					method : 'POST',
					timeout: 60000,
					callback: function (options, success, response) {
		            	if (success) {
		            		var jsonRecord = Ext.JSON.decode(response.responseText);
							Ext.MessageBox.alert('提示', '操作成功', function(){
								me.store.load();
							});
						}else{
							Ext.MessageBox.alert('提示', '请求超时或网络故障');
						}
					}
				});
			}
		});
	},
	
	sysDelFlagFormat : function(value, p, record) {
		if(value==='1'){
			return '禁用';
		}else if(value==='0'){
			return '激活';
		}
        return value;
    }
	
})
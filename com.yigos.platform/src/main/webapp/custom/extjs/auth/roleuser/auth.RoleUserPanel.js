Ext.define('auth.RoleUserPanel', {
	
	constructor: function(config) {
		var me = this;
		me.configParam = config.configParam;
		me.init();
	},
	
	init : function(){
		var me = this;
		me.initRoleTreePanel();
		me.initMenuTreePanel();
		me.initButtonTreePanel();
		me.initView();
	},
	
	initView : function(){
		var me = this;
		me.panel = Ext.create('Ext.panel.Panel', {
			layout : 'border',
			//frame : true,
			defaults : {split : true},
			width: '100%',
			height: '100%',
			items : [{
					title : '角色数据',
					region : 'west',
					collapsible : true,
					width : 270,
					minSize: 220,
	    			maxSize: 220,
	    			items: me.roleTreePanel
				}, {
					title : '功能菜单',
					region: 'center',
	     			items: me.menuTreePanel
				}, {
					title : '功能按钮',
					region : 'east',  
					width : 270,
					minSize: 220,
	    			maxSize: 220,
	    			items: me.buttonTreePanel
				} ]
		});
		Ext.create('Ext.Viewport', {
			layout : 'fit',
			items : me.panel
		});
	},

	initRoleTreePanel : function(){
		var me = this;
		me.initRoleTreeStore();
		me.roleTreePanel = Ext.create('auth.TreeMenuPanel', {
			store: me.roleTreeStore,
			rootVisible : false,//根节点是否可见
			lines : true,
		    border : false,
		    listeners: {
		    	itemclick : function (view, record, item, index, e, eOpts){
		    		me.roleId = record.get('id');
		    		var data = {
		    			id : '0',
				        text : 'root',
				        checked: false,
				        leaf: false,
				        expanded: true,
				        children : [
		    		        {
		    		        	id : 'system',
		    		            text : '系统管理',
		    		            checked: false,
		    		            leaf: false,
		    		            expanded: true}
		    		    ]
		    		};
		    		me.menuTreeStore.setRootNode(data);
		    	}
		    }
		});
	},
	
	initRoleTreeStore : function(){
		var me = this;
		me.roleId='';
		me.roleTreeStore = Ext.create('Ext.data.TreeStore', {
			autoLoad: true,
			nodeParam: 'pid',
			defaultRootId: '0',
			defaultRootText: '顶级角色',
			// 数据代理
            proxy : {
				type : 'ajax',
				url : me.configParam.urlsPath.roleList,
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

	initMenuTreePanel : function(){
		var me = this;
		me.initMenuTreeStore();
		me.menuTreePanel = Ext.create('auth.TreeMenuPanel', {
			store : me.menuTreeStore,
			rootVisible : false,//根节点是否可见
			lines : true,
			border : false,
			listeners: {
				itemclick : function (view, record, item, index, e, eOpts){
					me.menuId = record.get('id');
		    		
				}
			}
		});
		me.menuTreePanel.getRootNode().removeAll(false);
		me.menuTreePanel.store.load();
	},

	initMenuTreeStore : function(){
		var me = this;
		me.menuId = '';
		me.menuTreeStore = Ext.create('Ext.data.TreeStore', {
			queryMode : 'local',
			defaultRootId: 0,
			defaultRootText: 'root',
			root : {
		    	id : '0',
		        text : 'root',
		        checked: false,
		        leaf: false,
		        expanded: true,
		        children : [
		        {
		        	id : 'system',
		            text : '系统管理',
		            checked: false,
		            leaf: false,
		            expanded: true,
		            children : [
		            	{
			        	id : 'auth1',
			            text : '权限管理1',
			            checked: false,
			            leaf: true,
			            expanded: true
			            }
		            ]
		        }
		        ]
			}
		});
		/*
		me.menuTreeStore = Ext.create('Ext.data.TreeStore', {
			autoLoad: false,
			//nodeParam: 'pid',
			//defaultRootId: '0',
			//defaultRootText: '顶级',
			// 数据代理
			proxy : {
				type : 'ajax',
				url : me.configParam.urlsPath.roleMenuList,
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
		
		me.menuTreeStore.on('beforeload', function (store, options) {
			var newParams = {
					'roleId': me.roleId
				};
		    Ext.apply(me.menuTreeStore.proxy.extraParams, newParams);
		});
		*/
	},

	initButtonTreePanel : function(){
		var me = this;
		me.initButtonTreeStore();
		me.buttonTreePanel = Ext.create('auth.TreeMenuPanel', {
			//store: me.buttonTreeStore,
			rootVisible : false,//根节点是否可见
			lines : true,
			border : false,
			listeners: {
				itemclick : function (view, record, item, index, e, eOpts){

				}
			}
		});
	},

	initButtonTreeStore : function(){
		var me = this;
		me.buttonTreeStore = Ext.create('Ext.data.TreeStore', {
			autoLoad: true,
			//nodeParam: 'pid',
			defaultRootId: '0',
			defaultRootText: '顶级',
			// 数据代理
			proxy : {
				type : 'ajax',
				url : me.configParam.urlsPath.menuButtonList,
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
		
		me.buttonTreeStore.on('beforeload', function (store, options) {
			var newParams = {
					'menuId': me.menuId
				};
		    Ext.apply(me.buttonTreeStore.proxy.extraParams, newParams);
		});
	}
});
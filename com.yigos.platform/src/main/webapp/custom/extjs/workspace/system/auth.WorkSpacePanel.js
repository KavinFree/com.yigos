Ext.define('auth.WorkSpacePanel', {
	constructor: function(config) {
		var me = this;
		me.configParam = config.configParam;
		me.init();
	},
	
	init : function(){
		var me = this;
		me.initStore();
		me.treePanel = Ext.create('auth.TreeMenuPanel', {
			store: me.store,
			rootVisible : false,//根节点是否可见
		    border : false,
		    listeners: {
		    	itemclick : function (view, record, item, index, e, eOpts){
		    		var baseURI = CommonsUtil.contextPath;
		    		if (typeof(record.get('id'))=='undefined' || record.get('id')==''
		    			|| typeof(record.get('link'))=='undefined' 
		    			|| record.get('link')==''){
		    			return;
		    		}
		    		masterPageTab = Ext.getCmp("masterPageTab");
		            var tab = masterPageTab.getComponent(record.get('id'));
		            if (!tab) {
		                tab = masterPageTab.add({
		                    id: record.get('id'),
		                    title: record.get('text'),
		                    closable: true,
		                    //fitToFrame: true,
		                    html: '<iframe id="tabPanel-iframe" frameborder="0" scrolling="auto" src="'
		                    + baseURI + record.get('link') 
		                    //+ 'http://127.0.0.1:88/yigos/workspace/system/index.html'
		                    + '" class="tabPanel-iframe"></iframe>'
		                });
		                masterPageTab.setActiveTab(tab);
		            } else {
		            	masterPageTab.setActiveTab(tab);
		            }
				}
		    }
		});
		me.initView();
	},
	
	initStore : function(){
		var me = this;
		me.store = Ext.create('Ext.data.TreeStore', {
			autoLoad: true,
			nodeParam: 'pid',
			defaultRootId: '0',
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
		        { name: 'link',  type: 'string' },
		        { name: 'orderFlag',  type: 'string' },
		        { name: 'sysDelFlag',  type: 'string' }
	        ]
		});
	},
	
	initView : function(){
		var me = this;
		me.panel = Ext.create('Ext.panel.Panel', {
			layout : 'border',
			frame : true,
			defaults : {split : true},
			items : [
				{
					html : '上边',
					region : 'north',
					height : 30
				}, {
					html : '下边',
					region : 'south',
					height : 18
				}, {
					title : '功能菜單',
					collapsible : true,
					region : 'west',  
					width : 220,
					minSize: 220,
	    			maxSize: 220,
					items: me.treePanel
				}, {
					region: 'center',
			        xtype: 'tabpanel',
			        id: "masterPageTab",
			        activeTab: 0,
			        split: true,
			        items : [
	             	{
	     				title : "关于我们",
	     				html : '关于我们'
	     			}]
				} ]
		});
		
		Ext.create('Ext.Viewport', {
	        layout : 'fit',
	        items : me.panel
	    });
		/*
		Ext.EventManager.onWindowResize(function(width, height) {
			alert(width);
			form.setWidth(width);
			form.setHeight(height);
			tabPanelMain.autoScroll=true,
		});
		*/
	}
})
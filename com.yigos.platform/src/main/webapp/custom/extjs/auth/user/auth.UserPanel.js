Ext.define('auth.UserPanel', {
	
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
		me.store = Ext.create('Ext.data.Store',{
			autoLoad: true,
		    pageSize: CommonsUtil.pageSize,
		    remoteSort: true,
			proxy:{
				type: 'ajax',
				url: me.configParam.urlsPath.list,
				reader: {
					type:'json',
					root: 'result',
	            	totalProperty: 'totalCount'
				}
			},
			fields: [
		    	{ name: 'id',    type: 'string' },
		    	{ name: 'code',  type: 'string' },
		        { name: 'name',  type: 'string' },  
		        { name: 'email', type: 'string' },
		        { name: 'phone', type: 'string' },
		        { name: 'address', type: 'string' },
		        { name: 'remark',  type: 'string' },
		        { name: 'sysDelFlag', type: 'string' },
		        { name: 'sysCreateUser', type: 'string' },
		        { name: 'sysCreateDateTime', type: 'string' },
		        { name: 'sysUpdateUser', type: 'string' },
		        { name: 'sysUpdateDateTime', type: 'string' },
		        { name: 'sysVersion', type: 'string' },
		        { name: 'sysSource', type: 'string' },
		        { name: 'sysArchive', type: 'string' }
		    ]
		});
		me.store.on('beforeload', function (store, options) {
			var newParams = {
					'code': Ext.getCmp('code-search').getValue(),
					'name': Ext.getCmp('name-search').getValue()
				};
		    Ext.apply(me.store.proxy.extraParams, newParams);
		});
	},
	
	initGrid : function(){
		var me = this;
		me.userGrid = Ext.create('Ext.grid.Panel', {
			layout: 'fit',
			xtype: 'grid',
		    loadMask: true,
		    columnLines: true,
		    selModel: {selType:'checkboxmodel'},
		    multiSelect: true,
	        store: me.store,
			bbar: Ext.create('Ext.PagingToolbar', {
	            store: me.store,
	            displayInfo: true,
	            displayMsg: me.configParam.i18n.displayMsg,
	            emptyMsg: me.configParam.i18n.emptyMsg
	        }),
	        dockedItems:[{
	        	xtype:'toolbar',
	        	dock:'top',
	        	ui:'footer',
	        	layout:{
	        		pack:'left'
	        	},
	        	items:[{text:me.configParam.i18n.addButton, minWidth:80, id:'add-button',
	        		xtype: 'button', disabled:false, iconCls:'table_add',//图标样式
	        		handler:function(){
	        			me.updateFlag = false;
	        			me.formPanel.getForm().reset(); 
	        			me.loadFormData(null);
	        		}
	        	}, {text:me.configParam.i18n.modifyButton, minWidth:80, id:'edit-button',
	        		xtype: 'button', disabled:true,
	        		handler:function(){
	        			var selModel = me.userGrid.getSelectionModel();
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
	        			var selModel = me.userGrid.getSelectionModel();
		                var ids = [];
		                if(selModel.hasSelection()){
		                	var selected = selModel.getSelection();
		                	Ext.each(selected, function(item){
                                ids.push(item.data.id);
                            });
		                	me.createDeleteWindow(ids);
		                }
	        		}
	        	}, {fieldLabel:me.configParam.i18n.code, labelWidth: 70, labelAlign : 'right',
	        		id:'code-search', xtype: 'textfield'
				}, {fieldLabel:me.configParam.i18n.name, labelWidth: 70, labelAlign : 'right',
	        		id:'name-search', xtype: 'textfield'
				}, {text:me.configParam.i18n.searchButton, minWidth:80,
					handler:function(){
						me.store.loadPage(1);
					}
	    		}]
	        }],
		    columns: [
		    	Ext.create('Ext.grid.RowNumberer'),
		    	{ text: me.configParam.i18n.id,  dataIndex: 'id', hidden:true},
		    	{ text: me.configParam.i18n.code, dataIndex: 'code', flex: 20, menuDisabled:true, sortable: true},
		        { text: me.configParam.i18n.name, dataIndex: 'name', flex: 40, menuDisabled:true, sortable: true},
		        { text: me.configParam.i18n.email, dataIndex: 'email', flex: 20, menuDisabled:false},
		        { text: me.configParam.i18n.phone, dataIndex: 'phone', flex: 20, menuDisabled:false},
		        {
			        text: me.configParam.i18n.sysDelFlag, dataIndex: 'sysDelFlag', flex: 10, menuDisabled:true, sortable: true, 
			        renderer:CommonsUtil.sysDelFlagFormat
		        }
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
			items:me.userGrid
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
                    xtype: "fieldcontainer", layout: "hbox", defaults:{xtype:'textfield'},
                    items: [
                    	{
		        			fieldLabel : '电话', name: 'phone', width:'30%'
						},
						{
		        			fieldLabel : '邮箱', name: 'email', flex: 1, allowBlank: false
						},
						{
		        			fieldLabel : '密码', name: 'password', width:'22%'
						}
					]
				},
				{
                    xtype: "fieldcontainer", layout: "hbox", defaults:{xtype:'textfield'},
                    items: [
                    	{
		        			fieldLabel : '地址', name: 'address', flex: 1
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
	                		savePath = me.configParam.urlsPath.updateUser;
	                	}else{
	                		savePath = me.configParam.urlsPath.saveUser;
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
			msg:"请稍等,加载中...",
			autoShow:true
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
			url : me.configParam.urlsPath.findUser,
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
					url : me.configParam.urlsPath.deleteUser,
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
	}
	
})
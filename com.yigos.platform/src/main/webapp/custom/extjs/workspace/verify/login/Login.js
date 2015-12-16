Ext.onReady(function () {
        var form = Ext.create('Ext.form.Panel', {
            layout: 'absolute',
            defaults:{
                labelAlign:'right'
            },
            defaultType: 'textfield',
            border: false,
            items: [
            	{
                fieldLabel: '用戶編號',
                fieldWidth: 40,
                msgTarget: 'side',
                allowBlank: false,
                minLength: 3,
                maxLength: 27,
                x: 5,
                y: 5,
                name: 'code',
                anchor: '90%',
                value: '${js.custom.extjs.workspace.verify.Login.code}'
            }, 
            {
                fieldLabel: '用戶密碼',
                fieldWidth: 40,
                inputType:'password',
                msgTarget: 'side',
                allowBlank : false,
                blankText : '密码不能为空',
                minLength: 3,
                maxLength: 30,
                x: 5,
                y: 35,
                name: 'password',
                anchor:'90%',
                value: '${js.custom.extjs.workspace.verify.Login.password}'
            }, 
            {
                fieldLabel: '驗&nbsp;證&nbsp;碼',
                fieldWidth: 40,
                msgTarget: 'side',
                allowBlank: false,
                minLength: 4,
                maxLength: 4,
                x: 5,
                y: 65,
                name: 'securityCode',
                anchor: '70%',
                value: '${js.custom.extjs.workspace.verify.Login.securityCode}'
            }, 
            {
            	id:'securityCodeImg',
                xtype:'image',
                src: CommonsUtil.contextPath + '/workspace/verify/login/security-code.json',
                style: 'width:80px;margin-right:20px;',
                x: 350,
                y: 65,
                listeners: {
			        el: {
			            click: function() {
			                Ext.getCmp('securityCodeImg').getEl().dom.src = CommonsUtil.contextPath 
			                + '/workspace/verify/login/security-code.json?time=' + new Date().getMilliseconds();
			            }
			        }
			    }
            }],
            listeners:{
                "beforeaction":function(_form, _action){
                    
                }
            },
            buttonAlign: 'center',
            buttons: [{
                text: '登陆',
                handler : function() {
                    if (form.form.isValid()) {
                        //this.disabled = true;
                        form.form.submit({
                            waitMsg:"请稍等,提交中...",
                            url : CommonsUtil.contextPath + '/workspace/verify/login/chech-user.json',
                            method : 'post',
                            success : function(form, dataResult) {
                                //Ext.Msg.alert('操作', dataResult.result.result.msg);
                                location.href = CommonsUtil.contextPath + dataResult.result.result.link;
                            },
                            failure : function(form, dataResult) {
                                Ext.Msg.alert('操作', dataResult.result.result.msg);
                            }
                        });
                    }
                }
            },{
                text: '重置',
                handler : function() {
                    form.form.reset();
                }
            }]
        });
		
        var loginWin = Ext.create('Ext.window.Window', {
            autoShow: true,
            title: '系统登陆',
            width: 500,
            height: 200,
            minWidth: 450,
            minHeight: 200,
            maxWidth:500,
            maxHeight:200,
            layout: 'fit',
            plain:true,
            closable: false,
            items: form
        });
    });
Ext.define("Ext.ux.form.field.CKEditor", {
	extend : 'Ext.form.field.TextArea',
	alias : 'widget.ckeditor',
	constructor : function() {
		this.callParent(arguments);
		// 必须先构造父类对象
		this.addEvents("instanceReady");
		// 注册一个instanceReady事件
	},
	height : 250,
	CKConfig : {
		//CommonsUtil.contextPath:'toolkit/ckeditor',
		height : 150,
		//如果选择字体样式等的弹出窗口被当前window挡住，就设置这个为一个较大的值
		baseFloatZIndex : 99999,
		//图片和flash都上传到这里
		filebrowserUploadUrl : '/6666upload4ckeditor',
		filebrowserImageBrowseUrl : 'browerServer.do?type=image',
		language : CommonsUtil.ckEditorLanguage,//zh(繁体)\zh-cn(简体)\en
		//去掉底下的body p标签
		removePlugins : 'elementspath',
		//拖拽以改变尺寸功能 plugins/resize/plugin.js
		resize_enabled : false,
		resize_maxHeight : 500,
		resize_maxWidth : 1000,
		resize_minHeight : 200,
		resize_minWidth : 450,
		toolbar : [
				[ '-', 'NewPage', 'Preview', '-', 'Print', 'Templates' ],
				[ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-',
						'SpellChecker' ],
				[ 'Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll',
						'Scayt', 'RemoveFormat' ],
				[ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea',
						'Select', 'Button', 'HiddenField' ],
				'/',
				[ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript',
						'Superscript' ],
				[ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent',
						'Blockquote' ],
				[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight',
						'JustifyBlock' ],
				[ 'Link', 'Unlink', 'Anchor' ],
				[ 'Image', 'Flash', 'Table', 'HorizontalRule', 'SpecialChar',
						'PageBreak' ],//, 'Smiley' , 'Iframe'
				'/', [ 'Styles', 'Format', 'Font', 'FontSize' ],
				[ 'TextColor', 'BGColor', 'Source' ] ]
	},
	initComponent : function() {
		var me = this;
		this.callParent(arguments);
		this.on("afterrender", function() {
			/*
			Ext.apply(me.CKConfig, {
						height : this.getHeight(),
						width : this.getWidth()
			});
			 */
			this.editor = CKEDITOR.replace(this.inputEl.id, me.CKConfig);
			this.editor.name = this.name;
			// 把配置中的name属性赋值给CKEditor的name属性
			this.editor.on("instanceReady", function() {
				this.fireEvent("instanceReady", this, this.editor);
				// 触发instanceReady事件
			}, this);
		}, this);
	},
	onRender : function(ct, position) {
		if (!this.el) {
			this.defaultAutoCreate = {
				tag : 'textarea',
				autocomplete : 'off'
			};
		}
		this.callParent(arguments)
	},
	setValue : function(value) {
		this.callParent(arguments);
		if (this.editor) {
			this.editor.setData(value);
		}
	},
	getRawValue : function() {
		//要覆盖getRawValue方法，否则会取不到值       
		if (this.editor) {
			return this.editor.getData();
		} else {
			return ''
		}
	},
	getValue : function() {
		return this.getRawValue();
	}
});

Ext.define('Ext.yigosui.form.PageComboBox', {
	extend:'Ext.form.ComboBox',
	alias: 'widget.PageComboBox',
	triggerAction: 'all',
    typeAhead: true,
    minListWidth: 200,
    root:'root',
    editable: false,
    pageSize: CommonsUtil.pageSize,
    labelAlign: 'right',
    url: '',
    fields:[],
    initComponent: function () {
       // var tpl = new Ext.XTemplate('<tpl for="."><div class="x-combo-list-item" ext:qtitle="标题" ext:qtip="{NewsTitle}">{NewsTitle}</div></tpl>');添加这句为提示
        var tpl = new Ext.XTemplate(
        '<tpl for="."><div style=" padding:5px;" id="{NewsID}" class="x-combo-list-item">' +
        '<img src="/default.jpg" width="30" style="float:left; margin-right:4px;" height="30"/>' +
        '<p>标题：{NewsTitle}</p><p>类型：{NewsType}</p></div></tpl>');
        this.addEvents('afterchange');
        this.store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: this.url,
                method: 'GET'
            }),
            reader: new Ext.data.JsonReader({ root: this.root }, this.fields)
        });
        Ext.apply(this, {
            store: this.store,
            mode: 'remote',
            fieldLabel: '新闻信息',
            labelWidth: 100,
            tpl: tpl,
            onSelect: this.onSelectItem //当选择时获得数据
        });
        Ext.yigosui.form.PageComboBox.superclass.initComponent.call(this);
    },
    
    onSelectItem: function (text, value) {
      //text.data包含了： ['NewsID', 'NewsTitle', 'NewsType']对应的值
        this.setValue(text.data.NewsTitle, value);
    },
    setValue: function (text, value) {//选择后进行赋值
        this.lastSelectionText = text;
        Ext.form.ComboBox.superclass.setValue.call(this, text);
        this.value = value;
    }
})
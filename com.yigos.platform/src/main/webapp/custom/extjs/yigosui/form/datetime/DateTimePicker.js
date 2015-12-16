Ext.define("Ext.locale.en.yigosui.form.picker.DateTimePicker", {
			override : "Ext.yigosui.form.DateTimePicker"
		});

Ext.define('Ext.yigosui.form.DateTimePicker', {
	extend : 'Ext.picker.Date',
	alias : 'widget.datetimepicker',
	todayText : '现在',
	timeLabel : '时间',
	configText : '确定',
	configTip : '确定',
	requires : ['Ext.yigosui.form.TimePickerField'],

	initComponent : function() {
		// keep time part for value
		var value = this.value || new Date();
		this.callParent();
		this.value = value;
	},
	onRender : function(container, position) {
		var me = this;
		if (!this.timefield) {
			this.timefield = Ext.create('Ext.yigosui.form.TimePickerField', {
					fieldLabel : this.timeLabel,
					labelWidth : 40,
					value : Ext.Date.format(this.value, 'H:i:s')
				});
		}
		this.timefield.ownerCt = this;
		this.timefield.on('change', this.timeChange, this);
		this.callParent(arguments);

		var table = Ext.get(Ext.DomQuery.selectNode('table',
				this.el.dom));
		var tfEl = Ext.core.DomHelper.insertAfter(table, {
					tag : 'div',
					style : 'border:0px;',
					children : [{
								tag : 'div',
								cls : 'x-datepicker-footer ux-timefield'
							}]
				}, true);
		this.timefield.render(this.el.child('div div.ux-timefield'));

		var p = this.getEl().parent('div.x-layer');
		if (p) {
			p.setStyle("height", p.getHeight() + 31);
		}
		//确定
		me.configBnt();
	},
	// listener 时间域修改, timefield change
	timeChange : function(tf, time, rawtime) {
		// if(!this.todayKeyListener) { // before render
		this.value = this.fillDateTime(this.value);
		// } else {
		// this.setValue(this.value);
		// }
	},
	// @private
	fillDateTime : function(value) {
		if (this.timefield) {
			var rawtime = this.timefield.getRawValue();
			value.setHours(rawtime.h);
			value.setMinutes(rawtime.m);
			value.setSeconds(rawtime.s);
		}
		return value;
	},
	// @private
	changeTimeFiledValue : function(value) {
		this.timefield.un('change', this.timeChange, this);
		this.timefield.setValue(this.value);
		this.timefield.on('change', this.timeChange, this);
	},

	/* TODO 时间值与输入框绑定, 考虑: 创建this.timeValue 将日期和时间分开保存. */
	// overwrite
	setValue : function(value) {
		this.value = value;
		this.changeTimeFiledValue(value);
		return this.update(this.value);
	},
	// overwrite
	getValue : function() {
		return this.fillDateTime(this.value);
	},
	
	// overwrite : fill time before setValue
	handleDateClick : function(e, t) {
		var me = this, handler = me.handler;
		e.stopEvent();
		if (!me.disabled && t.dateValue
				&& !Ext.fly(t.parentNode).hasCls(me.disabledCellCls)) {
			me.doCancelFocus = me.focusOnSelect === false;
			// overwrite: fill time before setValue
			me.setValue(this.fillDateTime(new Date(t.dateValue))); 
			delete me.doCancelFocus;
			me.fireEvent('select', me, me.value);
			if (handler) {
				handler.call(me.scope || me, me, me.value);
			}
			me.onSelect();
		}
	},
	
	// overwrite : fill time before setValue
	selectToday : function() {
		var me = this, btn = me.todayBtn, handler = me.handler;
		
		if (btn && !btn.disabled) {
			// me.setValue(Ext.Date.clearTime(new Date())); //src
			me.setValue(new Date());// overwrite: fill time before
									// setValue
			me.fireEvent('select', me, me.value);
			if (handler) {
				handler.call(me.scope || me, me, me.value);
			}
			me.onSelect();
		}
		return me;
	},
	
	configBntHandler : function() {
		var me = this;
		if (me.configBtn && !me.configBtn.disabled) {
			me.setValue(me.getValue());
			me.fireEvent('select', me, me.value);
			me.onSelect();
		}
		return me;
	},
	configBnt : function() {
		var me = this;
		me.footer = Ext.get(Ext.DomQuery.selectNode(
				'.x-datepicker-footer:last-child', me.el.dom));
		me.tfEl = Ext.core.DomHelper.insertAfter(me.footer, {
			tag : 'div',
			style : 'border:0px;margin-left:50px;float:right',
			children : [ {
				tag : 'a',
				cls : 'x-datepicker-footer x-datepicker-configBtn'
			} ]
		}, true);
		me.configBtn = Ext.create('Ext.button.Button', {
			ownerCt : me,
			ownerLayout : me.getComponentLayout(),
			text : me.configText,
			tooltip : me.configTip,
			tooltipType : 'title',
			handler : me.configBntHandler,//确认按钮的事件委托
			scope : me
		});
		me.configBtn.render(this.el.child('div div.x-datepicker-footer:last-child'));
	},
});
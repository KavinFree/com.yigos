Ext.define('Ext.ux.form.NumberTextSpinnerField', {
	extend : 'Ext.form.NumberField',
    xtype: 'ux-numberTextSpinnerField',
    alias : 'widget.numberTextSpinnerField',
	requires: ['Ext.form.NumberField'],
	value : '00',
	minValue: 0,
	maxValue: 60,
	addLength: 2,
	valueToRaw: function(value) {
        var me = this;
        value = me.parseValue(value);
        value = me.fixPrecision(value);
        value = Ext.isNumber(value) ? value : parseFloat(String(value).replace(me.decimalSeparator, '.'));
        value = isNaN(value) ? '' : String(value).replace('.', me.decimalSeparator);
        value = Ext.String.leftPad(value, me.addLength, '0');
        return value;
    }
});
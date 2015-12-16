Ext.define("Ext.ux.window.FormWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.FormWindow',
	width: '96%',
    minWidth: 880,
    maxWidth: '98%',
    autoScroll: true,
    height: '95%',
    constrainHeader: true,
    constrain: true,
    modal: true,
	buttonAlign: 'center',
	closeAction: 'hide',
	defaults: { bodyStyle: 'padding: 8px 8px 8px 0px' }
})
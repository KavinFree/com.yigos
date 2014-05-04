
//http://www.easyui.info/archives/216.html
//http://www.tuicool.com/articles/7rmU7r
(function($){
	var oldQuery = $.fn.combobox.defaults.keyHandler.query;
	$.fn.combobox.defaults.keyHandler.query = function(q){
		oldQuery.call(this, q);
		var opts = $(this).combobox('options');
		if (opts.mode == 'local'){
			var data = $(this).combobox('getData');
			for(var i=0; i<data.length; i++){
				if (data[i][opts.textField].toLowerCase() == q.toLowerCase()){
					$(this).combobox('setValue', data[i][opts.valueField]);
					return;
				}
			}
		}
	};
})(jQuery);
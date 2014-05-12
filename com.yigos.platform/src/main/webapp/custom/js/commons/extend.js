$.extend($.fn.treegrid.defaults, {
    fitColumns : true,
    striped : true,
    loadMsg : i18n_buttons.loadMsg, /*讀取中*/
    onLoadError: function() {
        if (arguments.length == 1 && Util.isNotEmpty(arguments[0])) { 
           $.messager.alert(i18n_buttons.prompt,/*提示*/
           arguments[0].root); 
        } else {
           $.messager.alert(i18n_buttons.prompt,/*提示*/
        		   i18n_buttons.serverConnectionAnomaly); /*伺服器連接異常*/
        } 
    }
});

$.fn.combobox.defaults.filter=function(q, row) {// q是你输入的值，row是数据集合
	var opts = $(this).combobox('options');
	if(typeof(q)!='undefined' && q.length>0){
		return row[opts.textField].toUpperCase().indexOf(q.toUpperCase()) >= 0; // 同一转换成大写做比较，==0匹配首位，>=0匹配所有
	}else{
		return true;
	}
	return true;
	//return row[ $(this).combobox("options").textField].indexOf(q) != -1;
};

//重寫日期控制項 yyyy-MM-dd
$.fn.datebox.defaults.formatter = function(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	//return (d < 10 ? ('0' + d) : d) + '-' + (m < 10 ? ('0' + m) : m) + '-' + y ;
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
};

//匹配 yyyy-MM-dd  yyyy/MM/dd  yyyyMMdd
$.fn.datebox.defaults.parser = function(date) {
	if (!date){
		return new Date();
	}
	date = date.replace("-", "").replace("-", "");
	date = date.replace("/", "").replace("/", "");
	var yy = ""; var mm = ""; var dd = "";
	
	yy = date.substr(0, 4);
	mm = date.substr(4, 2);
	dd = date.substr(6, 2);
	
	var y = parseInt(yy, 10);
	var m = parseInt(mm, 10);
	var d = parseInt(dd, 10);
	
	var result = "";
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		var result = new Date(y, m - 1, d);
		return result;
	} else {
		result = new Date();
		return result;
	}
};

$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
};

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
};

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
};

$.extend($.fn.validatebox.defaults.rules, {
    checkDate: {//驗證 是否為時間格式 dd-MM-yyyy
        validator: function(value, param){
        	var formatDate = value;
        	formatDate = formatDate.replace("-", "").replace("-", "");
            formatDate = formatDate.replace("/", "").replace("/", "");
          
          var reg=/^[0-9]{8}$/; //驗證八位數字
          var fal = reg.test(formatDate);
          if(fal){
                var yy = formatDate.substr(0, 4);
                var mm = formatDate.substr(4, 2);
                var dd = formatDate.substr(6, 2);
                var y = parseInt(yy, 10);
                var m = parseInt(mm, 10);
                var d = parseInt(dd, 10);
                if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
                      var result = new Date(y, m - 1, d);
                      return (result.getFullYear()==y&&(result.getMonth()+1)==m&&result.getDate()==d); 
                      
                 }else{
                      return false;
                 }
            }else{
              return false;
            }
        },
        message: i18n_buttons.checkDate
    },
	TimeCheck : {// 驗證兩個時間的比較
		validator : function(value, param) {
			this.message = this.defaultMsg;
			//驗證是否合法的時間格式
			if(!$.fn.validatebox.defaults.rules.checkDate.validator(value)){
				this.message = $.fn.validatebox.defaults.rules.checkDate.message;
				return false;
			}else{
				var s1 = $(param[0]).datebox("getValue");// 第一个参数的日期值
				var s2 = $(param[1]).datebox("getValue");// 第二个参数的日期值
				//對進行比較的兩個時間進行驗證 是否合法格式
				if($.fn.validatebox.defaults.rules.checkDate.validator(s1) && $.fn.validatebox.defaults.rules.checkDate.validator(s2)){
					var s1Data = $.fn.datebox.defaults.parser(s1);
					var s2Data = $.fn.datebox.defaults.parser(s2);
				    var result =  s1Data <= s2Data;
                    if(result){
                        $(param[0]).next().children("input:text").removeClass("validatebox-invalid");
                        $(param[1]).next().children("input:text").removeClass("validatebox-invalid");
                        $(param[2]).text(Util.getDays($.fn.datebox.defaults.formatter(s2Data), $.fn.datebox.defaults.formatter(s1Data)));
                        return true;
                    }else{
                        this.message = this.defaultMsg;
                        return false;
                    }   
				}
			}
			return true;
		},
		defaultMsg : i18n_buttons.endmustbstart
	},
    selectValueRequired: {//ComboBox 驗證是否 等於''或者‘請選擇’
        validator: function (value, param) {
            if (value == "" || value == i18n_buttons.select) {//|| value.indexOf('請選擇') >= 0
            	return false; 
            }else{
            	return true;
            }
        },
        message: i18n_buttons.selectNotNull	//该下拉框为必选项
    },
    comboboxCompareAndRequired : {
        	validator : function(val,param){
        		if(!$.fn.validatebox.defaults.rules.selectValueRequired.validator(val)){
    				this.message = $.fn.validatebox.defaults.rules.selectValueRequired.message;
    				return false;
    			}else if(!$.fn.validatebox.defaults.rules.comboboxCompare.validator(val,param)){
    				this.message = $.fn.validatebox.defaults.rules.comboboxCompare.message;
    				return false;
    			}else{
    				return true;
    			}
        	}
     },
     comboboxCompare : {
     	validator : function(val,param){
			var flag = false;
			var v = this;
			var v1 = $(param[0]);
			
	    	$($(param[0]).combobox('getData')).each(function(idx,obj){
		    	if(obj.text==val){
			    	//$(param[0]).parent().find('.combo-value').val(obj.valueField);
			    	flag = true;
			    	return;
		    	}
	    	});
	    	if(flag){
	    		return true;
	    	}else{
	    		this.message = '您輸入的内容不存在！';
	    		return false;
	    	}
     	}
     }
});
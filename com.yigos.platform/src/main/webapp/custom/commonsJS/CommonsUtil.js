var CommonsUtil = {};
CommonsUtil.apply = function(o, c, defaults) {
    if (defaults) { CommonsUtil.apply(o, defaults); }
    if (o && c && typeof c == 'object') {
        for (var p in c) { o[p] = c[p];}
    }
    return o;
};

CommonsUtil.apply(CommonsUtil, {
	contextPath : '',
	ckEditorLanguage:'zh-cn',//zh(繁体)\zh-cn(简体)\en
	pageSize:25,
	pageSizeForCombo:1000,
	/**
     * 日期时间格式化
     * dateStr为需要转换的字符串(格式為yyyy-MM-dd hh24:mi:ss)
     * formate转换后的格式化:yyyy-MM-dd hh24:mi:ss、yyyy-MM-dd、yyyy/MM/dd
     */
	datetimeFormat : function(dateStr, formate){
    	if(dateStr){
    		switch(typeof dateStr) {
                case "string":
                	dateStr = dateStr.replace(/-/g, "/");
                    break;
                case "number":
                    break;
    		}
    		var datetime = new Date(dateStr);
			var year = datetime.getFullYear();
			var month = datetime.getMonth() + 1;
			var date = datetime.getDate();
			var hour = datetime.getHours();
			var mm = datetime.getMinutes();
			var s = datetime.getSeconds();
			if(formate=='yyyy-MM-dd hh24:mi:ss'){
				return year+'-'+(month < 10 ? ('0' + month) : month)+'-'
				+(date < 10 ? ('0' + date) : date)
				+' '+(hour < 10 ? ('0' + hour) : hour)+':'
				+(mm < 10 ? ('0' + mm) : mm)+':'
				+(s < 10 ? ('0' + s) : s);
			}else if(formate=='yyyy-MM-dd'){
				return year+'-'+(month < 10 ? ('0' + month) : month)+'-'
				+(date < 10 ? ('0' + date) : date);
			}else if(formate=='yyyy/MM/dd'){
				return year+'/'+(month < 10 ? ('0' + month) : month)+'/'
				+(date < 10 ? ('0' + date) : date);
			}else if(formate=='yyyy MM dd'){
				return year+' '+(month < 10 ? ('0' + month) : month)+' '
				+(date < 10 ? ('0' + date) : date);
			}else if(formate=='yyyyMMddhh24miss'){
				return year+''+(month < 10 ? ('0' + month) : month)+''
				+(date < 10 ? ('0' + date) : date)
				+''+(hour < 10 ? ('0' + hour) : hour)+''
				+(mm < 10 ? ('0' + mm) : mm)+''
				+(s < 10 ? ('0' + s) : s);
			}else{
				return v;
			};
    	}else{
    		return "";
    	};
    },
    
    /**
     * 
     * @param {} sysDelFlag 当前单元格的值
     * @param {} cellMeta 保存的是cellId单元格id，id是列号，css是这个单元格的css样式
     * @param {} record 这行的所有数据，record.data["id"]
     * @param {} rowIndex 行号，分页以后的结果
     * @param {} columnIndex 列号
     * @param {} store 表格里所有的数据
     * @param {} view 
     * @return {String} 
     */
    sysDelFlagFormat : function(sysDelFlag, cellMeta, record, rowIndex, columnIndex, store, view){
    	if(sysDelFlag==='0'){
    		return '<font color="blue">激活</font>';
    	}else{
    		return '<span color="red">禁用</span>';
    	}
    },
    
	/*
	 * 處理鍵盤事件 禁止後退鍵（Backspace）、禁用Esc鍵
	 */
	shieldBackSpaceAndEsc : function (e) {
		var ev = e || window.event;// 獲取event對象
		var obj = ev.target || ev.srcElement;// 獲取事件源
		var t = obj.type || obj.getAttribute('type');// 獲取事件源類型
		// 獲取作為判斷條件的事件類型
		var vReadOnly = obj.readonly || obj.getAttribute('readonly');
		var vEnabled = obj.enabled || obj.getAttribute('enabled');
		// 處理null值情況
		vReadOnly = (vReadOnly == null) ? false : vReadOnly;
		vEnabled = (vEnabled == null) ? true : vEnabled;
		if (ev.keyCode == 8) {// 禁止後退鍵（Backspace）
			// 密碼或單行、多行文字方塊除外，當敲Backspace鍵時，事件源類型為密碼或單行、多行文本的，並且readonly屬性為true或enabled屬性為false的，則倒退鍵失效
			var flag1=((t=="password"||t=="text"||t =="textarea")&&(vReadOnly=='readonly'||vEnabled!=true))?true:false;
			// 當敲Backspace鍵時，事件源類型非密碼或單行、多行文本的，則倒退鍵失效
			var flag2 = (t != "password" && t != "text" && t != "textarea") ? true : false;
			if (flag2) {
		        ev.returnValue = false;
				return false;
			}
			if (flag1) {
		        ev.returnValue = false;
				return false;
			}
		} else if (ev.keyCode == 27) {// 禁用Esc鍵
			ev.returnValue = false;
			return false;
		}
	}
});
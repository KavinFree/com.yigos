
/*
 * 處理鍵盤事件 禁止後退鍵（Backspace）、禁用Esc鍵
 */
function shieldBackSpaceAndEsc(e) {
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
};

$(function() {
	// 禁止後退鍵、禁用Esc鍵 作用於Firefox、Opera
	document.onkeypress = shieldBackSpaceAndEsc;
	// 禁止後退鍵、禁用Esc鍵 作用於IE、Chrome
	document.onkeydown = shieldBackSpaceAndEsc;
	//對window拖動不能夠編輯問題 2013-04-09
	$("div.window-proxy-mask").live({
		mouseup:function(e){
			$(this).remove();
		}
	});
});
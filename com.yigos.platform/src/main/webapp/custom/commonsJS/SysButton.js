/**
 * 授權按鈕js公共部分 根據用戶 角色 授權情況從後臺獲取按鈕的json字串過來， 把json轉換為js對象，放到toolbar中,用法：
 * 1.將該js引入jsp頁面； 2.toolbar:buttons 3.然後寫對應的事件處理 vbuttons:
 * id:addBtn;event:showWin('scwj');icon:icon-add 4.根據上面vbuttons欄位 即可寫js方法
 * function showWin(event){ alert(event); }
 */
// 全域公共按鈕組件 [{id:'id',text:'text',handler:myhadnler=function(){}}]
var buttons = [];
/**
 * 封裝一個基本的工具條類ToolButton 新增:addEvent(); 刪除:delEvnet(); 編輯:editEvent();
 * 查看:viewEvent(); 查詢:queryEvnet(); extend:擴展方法 屬性
 */  
var ToolButton = function() {
	this.addEvent = function() {// 新增
		// 只有firebug支持console
		// console.debug('調用了公共工具條按鈕ToolButton的addEvent方法!');
		$.messager.alert(i18n_buttons.prompt, String.format(
				i18n_buttons.rewrite, 'toolbutton', 'addEvent()'));
	};
	this.delEvent = function() {// 刪除
		// console.debug('調用了公共工具條按鈕ToolButton的delEvent方法!');
		$.messager.alert(i18n_buttons.prompt, String.format(
				i18n_buttons.rewrite, 'toolbutton', 'delEvent()'));
	};
	this.editEvent = function() {// 編輯 修改
		// console.debug('調用了公共工具條按鈕ToolButton的editEvent方法!');
		$.messager.alert(i18n_buttons.prompt, String.format(
				i18n_buttons.rewrite, 'toolbutton', 'editEvent()'));
	};
	this.viewEvent = function() {// 查看
		// console.debug('調用了公共工具條按鈕ToolButton的viewEvent方法!');
		$.messager.alert(i18n_buttons.prompt, String.format(
				i18n_buttons.rewrite, 'toolbutton', 'viewEvent()'));
	};
	this.queryEvnet = function() {// 查詢
		// console.debug('調用了公共工具條按鈕ToolButton的queryEvnet方法!');
		$.messager.alert(i18n_buttons.prompt, String.format(
				i18n_buttons.rewrite, 'toolbutton', 'queryEvnet()'));
	};
	this.noHandlerEvent = function(){//多加这个数据库没有为按钮配置事件方法
		//數據庫中沒有配置event:方法
		$.messager.alert(i18n_buttons.prompt, i18n_buttons.nomethod);
	};
	this.idCssArray = [];//按鈕id和樣式數組 {id:'id1',css:'classz'}
	this.addBtnClass = function(){
		//Empty method 向按鈕添加css樣式 
	};
	this.extend = ToolButton.prototype; // 用以擴展自己的方法
};
// 工具條按鈕全域變量
var toolbutton = new ToolButton();

/**
 * 根據按鈕btn id按鈕國際化
 */
function buttons_MulLanguage(btn_id) {
	if ((typeof (btn_id) != 'undefined') && (btn_id != null) && (btn_id != "")) {
		if (btn_id.indexOf('add') >= 0) {
			return i18n_buttons.add;
		} else if (btn_id.indexOf('edit') >= 0) {
			return i18n_buttons.edit;
		} else if (btn_id.indexOf('del') >= 0) {
			return i18n_buttons.del;
		} else if (btn_id.indexOf('view') >= 0) {
			return i18n_buttons.view;
		} else if (btn_id.indexOf('querybar') >= 0) {
			return i18n_buttons.querybar;
		} else if (btn_id.indexOf('save') >= 0) {
			return i18n_buttons.save;
		} else if (btn_id.indexOf('cancel') >= 0) {
			return i18n_buttons.cancel;
		} else {
			return btn_id;
		}
	} 
	return "";
}

/**
 * 載入buttons 同步加載按鈕
 */
var buttons_str;//用於存放返回Buttons的json
function loadButtons() {
	$.ajax({
		type : "post", // 提交方式
		async : false, // 使用同步的Ajax請求
		cache : false, // 不使用緩存
		url : basePath + '/auth/authorize/role-function/build-buttons.json', // 提交地址
		datetype : 'json',
		success : function(data) {// 根據返回狀態，給出相關提示
			if (data.success) {
				buttons_str=data.root;
				buttons = buildButtons(data.root);//保留之前版本按鈕仍然可以使用
				return true;
			} else if (!data.success) {
				$.messager.alert(i18n_buttons.error, data.root); // 提示錯誤資訊
			}
		}
	});
}
/**
 * 構建Button控件
 * 
 * @param data
 * @returns
 */
function buildButtons(data) {
	// 組合json
	// data=$.parseJSON(data);
	if (data == null || typeof (data) == 'undefined') {
		return [];
	}
	var len = data.length;
	if (len == 0) {
		return [];
	}
	var btn_json = '[';
	for (var i = 0; i < len; i++) {
		var btn = data[i];
		if (i > 0)
			btn_json += ',"-" ,';
		var uuid=Util.uuidFast();//使用uuid管理按鈕的id
		var classz=btn.classz; //css樣式
		var idCssArray = {id:uuid,css:classz};
		toolbutton.idCssArray.push(idCssArray);
		btn_json += '{';
		btn_json += '"id":"' + uuid + '",';
		btn_json += '"iconCls":"' + btn.iconCls + '",';
		var handler = btn.handler;
		// 此處需要判斷handler是否有值
		if ((typeof (handler) != 'undefined') && (handler != null)
				&& (handler != "")) {
			// handler此處需要構造一個內部方法 並且賦值個一個變量
			btn_json += '"handler":"' + 'btn_' + i
					+ ' = function(){toolbutton.' + btn.handler + ';}",';
		} else {
			//FIXME jimeng.luo sys.tool.buttons.nomethod= 此處沒有給指定的方法
			//*i18n_buttons.nomethod 在easyui.jsp中少了这句国际化 会报undefined错误
			btn_json += '"handler":"' + 'btn_' + i
					+ ' = function(){toolbutton.noHandlerEvent();}",';
		}
		// btn_json += '"text":"' + buttons_MulLanguage(btn.id) + '"}';
		btn_json += '"text":"' + btn.text + '"}';
	}
	btn_json += ']';
	return $.parseJSON(btn_json); // json -> js
}
/**
 * 根據給定Tab索引 獲取指定索引部份的按鈕
 */
function getButtons(tabIndex) {
	if(tabIndex==''||tabIndex == 'undefined'){
		return buildButtons(buttons_str);
	}
	var data = buttons_str;
	// 組合json
	// data=$.parseJSON(data);
	if (data == null || typeof (data) == 'undefined') {
		return [];
	}
	var len = data.length;
	if (len == 0) {
		return [];
	}
	var btn_json = '[';
	var j = 0;
	for ( var i = 0; i < len; i++) {
		var btn = data[i];
		if(btn.tabIndex != tabIndex)
			continue;
		if (j > 0)
			btn_json += ',"-" ,';
		j++;
		var uuid=Util.uuidFast();//使用uuid管理按鈕的id
		var classz=btn.classz; //css樣式
		var idCssArray = {id:uuid,css:classz};
		toolbutton.idCssArray.push(idCssArray);
		btn_json += '{';
		btn_json += '"id":"' + uuid + '",';
		btn_json += '"iconCls":"' + btn.iconCls + '",';
		var handler = btn.handler;
		// 此處需要判斷handler是否有值
		if ((typeof (handler) != 'undefined') && (handler != null)
				&& (handler != "")) {
			// handler此處需要構造一個內部方法 並且賦值個一個變量
			btn_json += '"handler":"' + 'btn_' + j
					+ ' = function(){toolbutton.' + btn.handler + ';}",';
		} else {
			//FIXME jimeng.luo sys.tool.buttons.nomethod= 此處沒有給指定的方法
			//*i18n_buttons.nomethod 在easyui.jsp中少了这句国际化 会报undefined错误
			btn_json += '"handler":"' + 'btn_' + i
					+ ' = function(){toolbutton.noHandlerEvent();}",';
		}
		// btn_json += '"text":"' + buttons_MulLanguage(btn.id) + '"}';
		btn_json += '"text":"' + btn.text + '"}';
	}
	btn_json += ']';
	if(btn_json == '[]')
		return [];
	return $.parseJSON(btn_json); // json -> js
}
/**
 * 向按鈕id添加css樣式
 */
toolbutton.addBtnClass = function(){
	var data = toolbutton.idCssArray;
	$.each(data , function(i, val) {
		if(val){
			if($('#'+val.id)){
				$('#'+val.id).addClass(val.css);
			}
		}
	});
};

$(function() {
	loadButtons();
});
// -----------------------------以上為生成按鈕-----------------------------------
// -----------------------------以下為生成高級查詢框-------------------------------
var QueryTemplate = function() {
	this.extend = QueryTemplate.prototype; // 用以擴展自己的方法
	this.url = ''; // url 請求資源url
	this.datagrid = ''; // datagrid的id
	this.querydlg_id = '#dlg_query'; // 查詢對話方塊的id
	this.querydlg_fmid = '#fm_query'; // 查詢對話方塊form 的id
	this.querydlg_tableid = '#query_tb'; // 查詢對話框table的id
	this.searchbox_queryid = '#searchbox_query';
	this.searchBox_Query = function(value, name) {
		$.messager.alert(i18n_buttons.prompt, String.format(i18n_buttons.rewrite, 'queryButton', 'searchBox_Query()'));
	};
};
var querybar = new QueryTemplate();
var searchboxHTML = '<div id="ext_searchbox" style="float:right;height: auto"><input id="ext_searchbox_ss" class="easyui-searchbox" searcher="querybar.searchBox_Query" prompt="'
		+ i18n_buttons.queryprompt
		+ '" menu="#ext_searchbox_mm" style="width: 300px"></input>'
		+ '<a href="javascript:void(0);" onclick="advancedQueryEvent()" class="easyui-linkbutton l-btn l-btn-plain" data-options="plain:true,iconCls:\'icon-search\'" id=""><span class="l-btn-left"><span class="l-btn-text icon-search" style="padding-left: 20px;">'
		+ i18n_buttons.advancedquery
		+ '</span></span></a>'
		+ '</div><div id="ext_searchbox_mm" style="width: 120px"></div>';
/**
 * 查詢對話框的入口
 * 
 * @returns
 */
var addQueryBar = function() {
	$(querybar.searchbox_queryid).html(searchboxHTML);
	// 先向搜索框添加datagrid的列名
	appendColumn2Datagrid(querybar.datagrid);
	$('#ext_searchbox').appendTo('.datagrid-toolbar');
	$('#ext_searchbox_ss').searchbox({
		menu : '#ext_searchbox_mm'
	});
	tb_queryBuild(); // 構建查詢對話方塊
};
/*
 * bugs:這個是用js插入 不能夠選擇 渲染前後問題 向功能表mm添加datagrid的列 <div style="height: 20px;"
 * class="menu-item" href="" name="all"><div class="menu-text">All News</div></div>
 * <div data-options="name:'fff',iconCls:'icon-ok'">All Newsfff</div>
 */
function appendColumn2Datagrid(datagrid_id) {
	var fields = $(datagrid_id).datagrid('getColumnFields');
	if (fields) {
		var html = '';
		for ( var i = 0; i < fields.length; i++) {
			var opts = $(datagrid_id).datagrid('getColumnOption', fields[i]);
			if (opts.hidden == true)
				continue;
			html += '<div name="' + fields[i] + '">' + opts.title + '</div>';
		}
		$('#ext_searchbox_mm').html(html);
	}
}
/**
 * 可以重寫此方法
 */
querybar.searchBox_Query = function(value, name) {
	// 重新刷新datagrid，並增加兩個參數key、type，這裡是POST傳值
	$(querybar.datagrid).datagrid('reload', {
		value : value, // 值
		field : name, // 欄位
		condition : 'like', // 條件
		logic : 'and' // 邏輯
	});
};
/**
 * 高級查詢事件
 * 
 * @param datagrid_id '#dlg_query'
 * @returns
 */
function advancedQueryEvent() {
	$(querybar.querydlg_id).dialog('open').dialog('setTitle',
			i18n_buttons.advancedquery);
}

/**
 * 高級查詢 去後臺查詢
 */
var advancedQuery = function() {
	var url = querybar.url;
	// 組合sql
	var frm = $(querybar.querydlg_fmid);
	var data = "where=" + frm.serialize();
	data = data.replace(/&/g, ";");
	$.ajax({
		type : 'post',
		url : url,
		data : data,
		success : function(data) {
			$(querybar.querydlg_id).dialog('close');
			$(querybar.datagrid).datagrid('loadData', data);
		}
	});
};

/**
 * 動態構造查詢對話方塊條件
 * 
 * @returns
 */
function tb_queryBuild() {
	var datagrid_id = querybar.datagrid; // 獲取datagrid
	var fields = $(datagrid_id).datagrid('getColumnFields');
	if (fields) {
		var tablehtml = '';
		var k = -1;
		for ( var i = 0; i < fields.length; i++) {
			var opts2 = $(datagrid_id).datagrid('getColumnOption', fields[i]);
			if (opts2.hidden == true)
				continue;
			k++;
			if (k == 0) {
				tablehtml += "<tr>"
						+ "<td><select id=\"logic\" name=\"logic\" style=\"width: 60px;\">" + "</select>" + "</td>"
						+ "<td><select id=\"field\" name=\"field\" style=\"width: 100px;\">";
			} else {
				tablehtml += "<tr>"
						+ "<td><select id=\"logic\" name=\"logic\" style=\"width: 60px;\">"
						+ "<option value=\"and\">"
						+ i18n_buttons.logicand
						+ "</option>"
						+ "<option value=\"or\">"
						+ i18n_buttons.logicor
						+ "</option>"
						+ "</select>"
						+ "</td>"
						+ "<td><select id=\"field\" name=\"field\" style=\"width: 100px;\">";
			}
			for ( var j = 0; j < fields.length; j++) {
				var opts = $(datagrid_id)
						.datagrid('getColumnOption', fields[j]);
				if (opts.hidden == true)
					continue;
				if (i == j) {
					tablehtml += "<option value=" + fields[j]
							+ " selected=\"selected\">" + opts.title
							+ "</option>";
				} else {
					tablehtml += "<option value=" + fields[j] + ">"
							+ opts.title + "</option>";
				}
			}
			tablehtml += "</select>"
					+ "</td>"
					+ "<td><select id=\"condition\" name=\"condition\" style=\"width: 60px;\">"
					+ "<option value=\"=\">" + i18n_buttons.equal + "</option>"
					+ "<option value=\"like\">" + i18n_buttons.like + "</option>"
					+ "<option value=\">\">" + i18n_buttons.greaterthan + "</option>"
					+ "<option value=\">=\">" + i18n_buttons.greaterthanequal + "</option>"
					+ "<option value=\"<\">" + i18n_buttons.lessthan + "</option>"
					+ "<option value=\"<=\">" + i18n_buttons.lessthanequal + "</option>"
					+ "</select>"
					+ "</td>" 
					+ "<td><input name=\"value\" />" + "</td>" 
					+ "<td></td>" + "</tr>";
		}
		$(querybar.querydlg_tableid).html($(querybar.querydlg_tableid).html() + tablehtml);
	}
}

////////////////////////////////////////////////////////////////////////////////
////////////
////////////
////////////////////////////////////////////////////////////////////////////////
/**
 * 按鈕是否可用控制
 */
var ToolBarContr = {};
ToolBarContr.apply = function(o, c, defaults) {
	if (defaults) {
		ToolBarContr.apply(o, defaults);
	}
	if (o && c && typeof c == 'object') {
		for (var p in c) {
			o[p] = c[p];
		}
	}
	return o;
};
ToolBarContr.apply(ToolBarContr, {
	defaults : function(dataGridId,eventMethod){
		if(dataGridId=='' || eventMethod==''){
			return ;
		}
		var length = $(dataGridId).datagrid('getSelections').length;
		if(eventMethod=='onBeforeLoad'){
			//加載數據之前
			$('.selectOne').linkbutton('disable');
			$('.selectMulti').linkbutton('disable');
			$('.selectZero').linkbutton('enable');
		}else if(eventMethod=='onUnselect' || eventMethod=='onSelect'){
		//選中或取消被選中的一條數據
			if(length==0){
				$('.selectOne').linkbutton('disable');
				$('.selectMulti').linkbutton('disable');
				$('.selectZero').linkbutton('enable');
			}else if(length==1){
				$('.selectMulti').linkbutton('disable');
				$('.selectZero').linkbutton('disable');
				$('.selectOne').linkbutton('enable');
			}else if(length>1){
				$('.selectZero').linkbutton('disable');
				$('.selectOne').linkbutton('disable');
				$('.selectMulti').linkbutton('enable');
			}
		}else if(eventMethod=='onUnselectAll'){
		//全取消選擇
			$('.selectOne').linkbutton('disable');
			$('.selectMulti').linkbutton('disable');
			$('.selectZero').linkbutton('enable');
		}else if(eventMethod=='onSelectAll'){
		//全選擇
			$('.selectZero').linkbutton('disable');
			$('.selectOne').linkbutton('disable');
			$('.selectMulti').linkbutton('enable');
		}
	},
	/**
	 * 紙禀申請
	 * 狀態：審批退回
	 * 不可以編輯，不可以發送
	 */
	licenseApplyState : function(dataGridId){
		var tmpData = {};
		tmpData.getSelectionsData = $(dataGridId).datagrid('getSelections');
		tmpData.length = tmpData.getSelectionsData.length;
		if(tmpData.length==0){
			$('.edit, .sendApprove').linkbutton('disable');
		}else if(tmpData.length>=1){
			$.each(tmpData.getSelectionsData, function(j, o) {
				if(o.state=="4"){
					$('.edit, .sendApprove, .del').linkbutton('disable');
				}
			});
		}
	},
    licensePastexhibitions : function(dataGridId){
        var tmpData = {};
        tmpData.getSelectionsData = $(dataGridId).datagrid('getSelections');
        tmpData.length = tmpData.getSelectionsData.length;
        if(tmpData.length>=1){
            if(tmpData.getSelectionsData[0].flag){
                $('.rePrint').linkbutton('disable');
            }else{
                $('.rePrint').linkbutton('enable');
            }
        }
    },
	 /**
     * 收件
     * 發送狀態 發送成功 不可以發送 編輯 刪除
     */
    receiptSendState : function(dataGridId){
        var tmpData = {};
        tmpData.getSelectionsData = $(dataGridId).datagrid('getSelections');
        tmpData.length = tmpData.getSelectionsData.length;
        if(tmpData.length==0){
            $('.deleteReceiptState, .editReceiptState, .sendReceiptState').linkbutton('disable');
        }else if(tmpData.length>=1){
            $.each(tmpData.getSelectionsData, function(j, o) {
                if(o.sendStatus=="1"){
                    $('.deleteReceiptState, .editReceiptState, .sendReceiptState').linkbutton('disable');
                }
            });
        }
    }
});

//搜索欄熱鍵
ToolBarContr.apply(ToolBarContr, {
	searchHotKey : function(divIDs, divNextIDs){
		if(divIDs!=''){
			$(divIDs).keyup(function(event){
				var e = event || window.event, key = e.keyCode || e.which;
		        if(key==13){ query(); }
		    });
		}
		if(divNextIDs!=''){
			//日期控件綁定回車事件
			$(divNextIDs).next().find('.combo-text').keyup(function(event){
				var e = event || window.event, key = e.keyCode || e.which;
		        if(key==13){ query(); }
		    });
		}
	}
});

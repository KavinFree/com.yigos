Ext.define("auth.TreeMenuPanel", {
	extend : 'Ext.tree.TreePanel',
	alias : 'widget.TreeMenuPanel',
	layout: 'anchor',
	useArrows : true,
	split : true,
	border : false,
    animate : true,
    enableDD : true,
    autoScroll : false,
    containerScroll : false,
    enableTabScroll: true,
    lines : true,//节点间的虚线条
    rootVisible : false,//根节点是否可见
    height : '100%',
    fields : [
    	{name : 'id', type : 'string'}, {name : 'text', type : 'string'},
    	{name : 'link', type : 'string'}, {name : 'iconCls', type : 'string'}
	],
	listeners: {
    	checkchange : function(node, checked) {
    		var me = this;
			// 获得父节点
			pNode = node.parentNode;
			// 改变当前节点的选中状态
			node.checked = checked;
			// 判断当前节点是否为叶子节点
			var isLeaf = node.isLeaf();
			// 当该节点有子节点时,将所有子节点的选中状态同化
			if (!isLeaf) {
				// cascade是指从当前节点node开始逐层下报，即遍历当前节点的每一个节点(无论有多少层级结构,详参API)
				node.cascade(function(node) {
					node.set("checked", checked);
				});
			}
			// 如果当前节点是选中状态
			if (checked == true) {
				// 将当前节点的所有未选中的父节点选中
				for (; pNode != null && !pNode.get("checked"); pNode = pNode.parentNode) {
					pNode.set("checked", true);
				}
			} else {
				// 取消当前节点的所有不包含选中的子节点的父节点的选中状态
				for (; pNode != null; pNode = pNode.parentNode) {
					// 如果当前的父节点包含选中的子节点，则终止搜索过程
					if (me.hasCheckedNode(pNode)) {
						break;
					} else {
						// 否则取消当前父节点的选中状态
						pNode.set("checked", false);
					}
				}
			}
		}
    },
	// 检查指定的父节点是否包含有选中的子节点
	hasCheckedNode : function(node) {
		var me = this;
		var has = false;
		// 当前节点是否含有子节点
		if (node.childNodes) {
			// 遍历子节点
			Ext.each(node.childNodes, function(item) {
				// 如果当前节点是叶子节点
				if (item.isLeaf()) {
					// 遇到选中的子节点时终止搜索过程
					if (has = item.get("checked")) {
						return false;
					}
					// 遇到含有选中的子节点的父节点时终止搜索过程
				} else if (has = me.hasCheckedNode(item)) {
					return false;
				}
			});
		}
		return has;
	}
})
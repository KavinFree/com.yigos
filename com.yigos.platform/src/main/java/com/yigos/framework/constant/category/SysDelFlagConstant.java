package com.yigos.framework.constant.category;

import com.yigos.framework.annotation.FieldComment;

public interface SysDelFlagConstant {
	@FieldComment(
			name="是否可用：可用(0)、不可用(1)", 
			type="Integer", 
			value="0")
	//false:false/off/no/0
	
	public final static Integer SYS_DEL_FLAG_ENABLE = 0;
	@FieldComment(name="是否可用：可用(0)、不可用(1)", 
			type="Integer", 
			value="1")
	//true:true/on/yes/1
	public final static Integer SYS_DEL_FLAG_UNABLE = 1;
}

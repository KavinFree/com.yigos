package com.yigos.framework.constant.category;

import com.yigos.framework.annotation.FieldComment;

public interface EnableConstant {
	@FieldComment(
			name="是否可用：可用(1)、不可用(0)", 
			type="Integer", 
			value="1")
	public final static Integer ENABLE_1 = 1;
	@FieldComment(name="是否可用：可用(1)、不可用(0)", 
			type="Integer", 
			value="0")
	public final static Integer ENABLE_0 = 0;
}

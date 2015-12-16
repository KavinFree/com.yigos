package com.yigos.framework.model;

import java.util.Collection;

public class PageModel {
	/*
	 * 总记录数量
	 */
	private Integer counts;

    private Collection<?> items;

    /*
     * 当前页码
     */
    private Integer page = 1;
    
    /*
     * 需要查找的记录下标
     */
    private Integer start = 1;
    
    /*
     * 需要找出的记录数量
     */
    private Integer limit = 15;

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public Collection<?> getItems() {
		return items;
	}

	public void setItems(Collection<?> items) {
		this.items = items;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}

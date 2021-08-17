package com.nimai.admin.payload;

import java.util.List;
import java.util.Map;

public class PagedResponse<T> {

	private List<T> content;
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean last;
	private Map<String, String> counts;

	public PagedResponse() {

	}

	public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last) {
		this.content = content;
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
	}

	public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last,
			Map<String, String> counts) {
		super();
		this.content = content;
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
		this.counts = counts;
	}

	public PagedResponse(List<T> content) {
//		super();
		this.content = content;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public Map<String, String> getCounts() {
		return counts;
	}

	public void setCounts(Map<String, String> counts) {
		this.counts = counts;
	}

}

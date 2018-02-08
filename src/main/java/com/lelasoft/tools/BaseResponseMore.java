package com.lelasoft.tools;

import com.lelasoft.tools.BaseResponse.Head;


/*
 * @author KhaledLela
 *
 * @param <D>
 *            Data
 * @param <M>
 *            More
 */
public class BaseResponseMore<D,M> {
	private Head head;
	private D data;
	private M more;
	
	
	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}

	public M getMore() {
		return more;
	}

	public void setMore(M more) {
		this.more = more;
	}
}

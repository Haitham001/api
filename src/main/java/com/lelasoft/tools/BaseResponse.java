package com.lelasoft.tools;


/*
 * @author KhaledLela
 *
 * @param <D>
 *            Data
 * @param <M>
 *            More
 */
public class BaseResponse<D> {
	private Head head;
	private D data;
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

	public static class Head {
		private String message = "";
		private int status;
		private boolean print;

		public String getMessage() {
			return message;
		}

		public int getStatus() {
			return status;
		}

		public boolean isPrint() {
			return print;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public void setPrint(boolean print) {
			this.print = print;
		}
	}
}

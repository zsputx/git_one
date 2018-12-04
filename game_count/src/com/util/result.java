package com.util;

import java.io.Serializable;

public class result  implements Serializable{

	

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int status;
		private String msg;
		private Object data;
		private int count;
		public int getStatus() {
			return status;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		
	}




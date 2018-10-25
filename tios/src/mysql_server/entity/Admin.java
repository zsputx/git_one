package mysql_server.entity;

import java.io.Serializable;
import java.sql.Date;

public class Admin {

	/**
	 * 
	 * 
	 * <<===========Admin 实体类================>>
	 * 
	 *                                   @author tx
	 *                                   
	 *                                   
	 *   备注sql 中的 date继承了util的date但是将时分秒去掉了
	 *   所以date可以是util
	 */

		private Integer admin_id;
		private String admin_code;
		private String password;
		private String name;
		private String telephone;
		private String email;
		private Date enrolldate;

		public Integer getAdmin_id() {
			return admin_id;
		}

		public void setAdmin_id(Integer admin_id) {
			this.admin_id = admin_id;
		}

		public String getAdmin_code() {
			return admin_code;
		}

		public void setAdmin_code(String admin_code) {
			this.admin_code = admin_code;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getName() {
			return name;
		}

		public Admin setName(String name) {
			this.name = name;
			return this;
		
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Date getEnrolldate() {
			return enrolldate;
		}

		public void setEnrolldate(Date enrolldate) {
			this.enrolldate = enrolldate;
		}

		public String toString() {
			return "Admin [admin_id=" + admin_id + ", admin_code=" + admin_code
					+ ", password=" + password + ", name=" + name + ", telephone="
					+ telephone + ", email=" + email + ", enrolldate=" + enrolldate
					+ "]";
		}

	}


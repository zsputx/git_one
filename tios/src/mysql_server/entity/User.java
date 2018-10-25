package mysql_server.entity;

import java.sql.Date;

public class User{

	private Integer id;
	private String name;
	private String password;
	private Integer state;
	private Integer score;
	private   Date zc_time;
	private String email;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getZc_time() {
		return zc_time;
	}
	public void setZc_time(Date zc_time) {
		this.zc_time = zc_time;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", state=" + state + ", score=" + score
				+ ", zc_time=" + zc_time + ", email=" + email + "]";
	}
	
	
}

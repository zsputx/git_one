package mysql_server.dao;


import java.util.ArrayList;


import mysql_server.entity.User;

public interface UserDao {
	public User findByName(String name);
	public void add_user(User admin);
	public void update_state(User admin);
	public void update_score(User admin);
	public void delete(String name);
	public ArrayList<User> get_all() ;

}

package mysql_server.dao;

import mysql_server.entity.Admin;

public interface AdminDao {
public Admin findByCode(String adminCode);
public void add_admin(Admin admin);
public void update(Admin admin);
public void delete(Integer id);
}

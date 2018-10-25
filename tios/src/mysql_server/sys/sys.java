package mysql_server.sys;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import mysql_server.dao.AdminDao;
import mysql_server.dao.UserDao;
import mysql_server.entity.Admin;
import mysql_server.entity.User;

public class sys {
	public static String conf = "config/applicationContext.xml";
	public static ApplicationContext app;
	public static UserDao userDao;

	public static void init_app() {
		app = new ClassPathXmlApplicationContext(conf);
		userDao = app.getBean("UserDao", UserDao.class);
	}

	public static Admin select(String code) {

		Admin admin = null;
		try {
			AdminDao dao = app.getBean("AdminDao", AdminDao.class);
			admin = dao.findByCode(code);
			System.out.println(AdminDao.class.getSimpleName());
		} catch (BeansException e) {

			e.printStackTrace();
			System.out.println("��ѯ����");
			return null;
		}

		if (null != admin) {
			return admin;
		} else {
			return null;
		}

	}

	public static boolean add_admin(Admin ad) {

		try {
			if (null != ad) {
				AdminDao dao = app.getBean("AdminDao", AdminDao.class);
				dao.add_admin(ad);
			} else {
				return false;
			}
		} catch (BeansException e) {

			e.printStackTrace();
			System.out.println("����ʧ��");
			return false;

		}
		return true;

	}

	public static boolean update_admin(Admin ad) {
		try {
			if (null != ad) {
				AdminDao dao = app.getBean("AdminDao", AdminDao.class);
				dao.update(ad);
			} else {
				return false;
			}
		} catch (BeansException e) {
			e.printStackTrace();
			System.out.println("�޸�ʧ��");
			return false;

		}
		return true;

	}

	public static boolean delete_admin(Integer id) {
		try {
			if (null != id) {
				AdminDao dao = app.getBean("AdminDao", AdminDao.class);
				dao.delete(id);
			} else {
				return false;
			}
		} catch (BeansException e) {
			e.printStackTrace();
			System.out.println("ɾ��ʧ��");
			return false;

		}
		return true;

	}

	public static List<User> get_all_user() {

		List list = userDao.get_all();
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}

	public static boolean add_user(User admin) {

		try {

			userDao.add_user(admin);
			return true;
		} catch (BeansException e) {

			System.out.println("����ʧ��");
			e.printStackTrace();
			return false;
		}

	}

	public static User find_user(String name) {

		try {

			User user = userDao.findByName(name);
			return user;
		} catch (BeansException e) {
			System.out.println("��ѯʧ��");
			e.printStackTrace();
		}
		return null;

	}

	public static int login(String name, String pwd) {
		// ��¼ҵ��
		//0 �û��Ѿ���¼      1�ɹ�      2���û������������
		User u = find_user(name);
		if (null != u) {
			if(u.getpassword().equals(pwd)) {
				
				if(u.getState()==0) {
					return 1;
				}else {
					
					return 0;
				}
				
			}else {
				return 2;
			}

		} else {
			return 2;
		}

		
	}

	public static int zc(String name, String pwd, String email) {
		// ע��ҵ��
		// 1�ɹ� 2 �û����ظ�  0 ע��ʧ��������
		User u = find_user(name);
		if (null != u) {
			return 2;

		} else {
			User u1 = new User();
			u1.setEmail(email);
			u1.setName(name);
			u1.setpassword(pwd);
			u1.setScore(100);
			u1.setState(0);
			u1.setZc_time(new Date(System.currentTimeMillis()));

			if (add_user(u1)) {

				return 1;
			} else {
				return 0;
			}

		}

	}

	public static boolean change_score(int score, String name) {
		// �ӷ�ҵ��
		User u = find_user(name);
		if (null != u) {

			 
			try {
				
				u.setScore(u.getScore() + score);
				if(u.getScore()>=0) {
				
				userDao.update_score(u);
				return true;
				}else {
					return false;
				}
			
			} catch (Exception e) {
				System.out.println("���ķ���ʧ��");
				e.printStackTrace();
				return false;
			}

		}
		return false;

	}

	public static boolean change_state(int a, String name) {
		// ����������ҵ��
		// 1���� 0 ����
		User u = find_user(name);
		if (null != u) {

			try {
				u.setState(a);
				userDao.update_state(u);
				return true;
			} catch (Exception e) {
				System.out.println("���ķ���ʧ��");
				e.printStackTrace();
				return false;
			}

		}
		return false;
		//
	}

}

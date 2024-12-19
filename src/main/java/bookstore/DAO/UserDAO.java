package bookstore.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.RolesEntity;
import bookstore.Entity.UsersEntity;
import bookstore.Utils.PasswordUtil;

@Repository
@Transactional
public class UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public UsersEntity getUserById(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		// Sử dụng session.get với User.class
		return (UsersEntity) session.get(UsersEntity.class, userId);
	}

	public UsersEntity getUserByIdHQL(Long id) {
		Session session = sessionFactory.getCurrentSession();
		UsersEntity user = (UsersEntity) session.createQuery("FROM UsersEntity WHERE id = :id").setParameter("id", id)
				.uniqueResult();
		return user;
	}

	public boolean saveNewUser(UsersEntity user) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(user);
			t.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		} finally {
			session.close();
		}

		return false;
	}

	public boolean checkOldPassword(Long userId, String oldPassword) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE id = :userId";
		UsersEntity user = (UsersEntity) session.createQuery(hql).setParameter("userId", userId).uniqueResult();

		if (user != null) {
			// So sánh mật khẩu cũ với mật khẩu đã băm trong cơ sở dữ liệu
			return PasswordUtil.verifyPassword(oldPassword, user.getPassword());
		}

		return false; // Trường hợp người dùng không tồn tại
	}

	public int updateUserById(Long id, UsersEntity updatedUser) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		int result = 0;
		try {
			String hql = "UPDATE UsersEntity "
					+ "SET fullname = :fullname, phone = :phone, avatar = :avatar, updated_at = :updatedAt WHERE id = :id";
			result = session.createQuery(hql).setParameter("fullname", updatedUser.getFullname())
					.setParameter("phone", updatedUser.getPhone()).setParameter("avatar", updatedUser.getAvatar())
					.setParameter("updatedAt", new Date()).setParameter("id", id).executeUpdate();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		} finally {
			session.close();
		}
		return result;
	}

	public int updatePasswordUserById(Long id, String newPassword) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		int result = 0;
		try {
			String hql = "UPDATE UsersEntity SET password = :password, updated_at = :updatedAt WHERE id = :id";
			result = session.createQuery(hql).setParameter("password", newPassword)
					.setParameter("updatedAt", new Date()).setParameter("id", id).executeUpdate();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		} finally {
			session.close();
		}
		return result;
	}

	public UsersEntity getUserByEmailPass(String email, String password) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE email = :email AND password = :password";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		query.setParameter("password", password);

		UsersEntity user = (UsersEntity) query.uniqueResult();

		return user;
	}

	public UsersEntity getUserByEmai(String email) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM UsersEntity WHERE email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);

		UsersEntity user = (UsersEntity) query.uniqueResult();

		return user;
	}

	public boolean updateUser(UsersEntity user) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.merge(user);
			t.commit();

			return true;
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return false;
	}

	public List<UsersEntity> searchUsers(String fullname, String role, Integer enabled, String fromDate, String toDate)
			throws ParseException {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM UsersEntity u WHERE 1=1";

		if (fullname != null && !fullname.isEmpty()) {
			hql += " AND u.fullname LIKE :fullname";
		}

		if (role != null && !role.isEmpty()) {
			hql += " AND u.role = :role";
		}

		if (enabled != null) {
			hql += " AND u.enabled = :enabled";
		}

		if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
			hql += " AND CONVERT(date, u.updated_at) BETWEEN :fromDate AND :toDate";
		}

		Query query = session.createQuery(hql);

		if (fullname != null && !fullname.isEmpty()) {
			query.setParameter("fullname", "%" + fullname + "%");
		}
		if (role != null && !role.isEmpty()) {
			query.setParameter("role", role);
		}
		if (enabled != null) {
			query.setParameter("enabled", enabled);
		}
		if (fromDate != null && !fromDate.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(fromDate);
			query.setParameter("fromDate", date);
		}
		if (toDate != null && !toDate.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(toDate);
			query.setParameter("toDate", date);
		}

		List<UsersEntity> users = query.list();

		for (UsersEntity user : users) {
			user.setRoles(user.getRoles()); // Ensure roles are loaded
		}

		// Query for roles
		Query queryRole = session.createQuery("FROM RolesEntity");
		List<RolesEntity> roles = queryRole.list();
		return users;
	}

	/*
	 * public long countUsers(String search, String role, Integer enabled) { Session
	 * session = sessionFactory.getCurrentSession(); StringBuilder countQuery = new
	 * StringBuilder("SELECT count(u) FROM bookstore.Entity.UsersEntity u WHERE 1=1"
	 * );
	 * 
	 * if (search != null && !search.isEmpty()) {
	 * countQuery.append(" AND (u.fullname LIKE :search OR u.email LIKE :search)");
	 * } if (role != null && !role.isEmpty()) { countQuery.
	 * append(" AND EXISTS (SELECT r FROM u.roles r WHERE r.name = :role)"); } if
	 * (enabled != null) { countQuery.append(" AND u.enabled = :enabled"); }
	 * 
	 * Query query = session.createQuery(countQuery.toString());
	 * 
	 * if (search != null && !search.isEmpty()) { query.setParameter("search", "%" +
	 * search + "%"); } if (role != null && !role.isEmpty()) {
	 * query.setParameter("role", role); } if (enabled != null) {
	 * query.setParameter("enabled", enabled); }
	 * 
	 * return (long) query.uniqueResult(); }
	 */

}

package bookstore.DAO;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.UsersEntity;

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
        UsersEntity user = (UsersEntity) session.createQuery("FROM UsersEntity WHERE id = :id")
                                                .setParameter("id", id)
                                                .uniqueResult();
        return user;
    }
    
    public int updateUserById(Long id, UsersEntity updatedUser) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        int result = 0;
        try {
            String hql = "UPDATE UsersEntity " +
                         "SET fullname = :fullname, phone = :phone, avatar = :avatar, updated_at = :updatedAt WHERE id = :id";
            result = session.createQuery(hql)
                            .setParameter("fullname", updatedUser.getFullname())
                            .setParameter("phone", updatedUser.getPhone())
                            .setParameter("avatar", updatedUser.getAvatar())
                            .setParameter("updatedAt", new Date())
                            .setParameter("id", id)
                            .executeUpdate();
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
            result = session.createQuery(hql)
                            .setParameter("password", newPassword)
                            .setParameter("updatedAt", new Date())
                            .setParameter("id", id)
                            .executeUpdate();
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
}

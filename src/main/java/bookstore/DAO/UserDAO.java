package bookstore.DAO;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.UsersEntity;
@Transactional
@Repository
public class UserDAO {
    @Autowired
    private SessionFactory factory;
    
    public UsersEntity getUserById(Long id) {
        Session session = factory.getCurrentSession();
        UsersEntity user = (UsersEntity) session.createQuery("FROM UsersEntity WHERE id = :id")
                                                .setParameter("id", id)
                                                .uniqueResult();
        return user;
    }
    
    public int updateUserById(Long id, UsersEntity updatedUser) {
        Session session = factory.getCurrentSession();
        String hql = "UPDATE UsersEntity " +
                     "SET fullname = :fullname, phone = :phone, avatar = :avatar, updated_at = :updated_at WHERE id = :id";
        int result = session.createQuery(hql).setParameter("fullname", updatedUser.getFullname())
                            .setParameter("phone", updatedUser.getPhone())
                            .setParameter("avatar", updatedUser.getAvatar())
                            .setParameter("updated_at", new Date())
                            .setParameter("id", id)
                            .executeUpdate();
        return result;
    }
    
    public int updatePasswordUserById(Long id, String newPassword) {
        Session session = factory.getCurrentSession();
        String hql = "UPDATE UsersEntity SET password = :password, updated_at = :updated_at WHERE id = :id";
        int result = session.createQuery(hql).setParameter("password", newPassword)
        					.setParameter("updated_at", new Date())
                            .setParameter("id", id)
                            .executeUpdate();
        return result;
    }

}

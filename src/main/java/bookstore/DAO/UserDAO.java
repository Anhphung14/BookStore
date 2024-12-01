package bookstore.DAO;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
}

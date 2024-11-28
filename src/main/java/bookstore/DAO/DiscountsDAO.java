package bookstore.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.BooksEntity;

@Transactional
@Repository
public class DiscountsDAO {
	@Autowired
	private SessionFactory factory;

	public Double getDiscountValueByBookId(Long bookId) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT bd.discount.discountValue " + "FROM Book_DiscountsEntity bd "
				+ "WHERE bd.book.id = :bookId " + "AND bd.discount.discountType = 0 "
				+ "AND CURRENT_TIMESTAMP BETWEEN bd.discount.startDate AND bd.discount.endDate";

		// Retrieve the discount value as BigDecimal
		Object result = session.createQuery(hql).setParameter("bookId", bookId).uniqueResult();

		// Kiểm tra nếu giá trị null thì trả về 0.0
		if (result == null) {
			return 0.0;
		}

		// Chuyển đổi từ Object sang BigDecimal rồi sang Double
		BigDecimal discountValueBD = (BigDecimal) result;
		return discountValueBD.doubleValue();
	}

	public List<Double> getDiscountsValueByBookId(List<BooksEntity> books) {
		Session session = factory.getCurrentSession();
		List<Double> discountValues = new ArrayList<>();

		for (BooksEntity book : books) {
			String hql = "SELECT bd.discount.discountValue " + "FROM Book_DiscountsEntity bd "
					+ "WHERE bd.book.id = :bookId " + "AND bd.discount.discountType = 0 "
					+ "AND CURRENT_TIMESTAMP BETWEEN bd.discount.startDate AND bd.discount.endDate";

			Object result = session.createQuery(hql).setParameter("bookId", book.getId()).uniqueResult();

			if (result == null) {
				discountValues.add(0.0);
			} else {
				BigDecimal discountValue = (BigDecimal) result;
				discountValues.add(discountValue.doubleValue());
			}
		}

		return discountValues;
	}
}

package bookstore.DAO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookstore.Entity.*;

@Repository
@Transactional
public class DiscountsDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private BooksDAO booksDAO;

	public List<DiscountsEntity> getAllDiscounts() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM DiscountsEntity";
		Query query = session.createQuery(hql);
		List<DiscountsEntity> allDiscounts = query.list();
		return allDiscounts;
	}

	public DiscountsEntity getDiscountByCode(String discountCode) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM DiscountsEntity d where d.code = :discountCode";
		Query query = session.createQuery(hql);
		query = query.setParameter("discountCode", discountCode);
		DiscountsEntity discount = (DiscountsEntity) query.uniqueResult();
		return discount;
	}

	public List<DiscountsEntity> searchDiscount(String discountCode, String discountType, String minValue,
			String maxValue, String fromDate, String toDate, String discountStatus) throws ParseException {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM DiscountsEntity d WHERE 1=1";

		if (discountCode != null && !discountCode.isEmpty()) {
			hql += " AND d.code LIKE :discountCode";
		}
		if (discountType != null && !discountType.isEmpty()) {
			hql += " AND d.discountType = :discountType";
		}
		if (minValue != null && !minValue.isEmpty()) {
			hql += " AND d.discountValue >= :minValue";
		}
		if (maxValue != null && !maxValue.isEmpty()) {
			hql += " AND d.discountValue <= :maxValue";
		}
		if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
			hql += " AND CONVERT(date, d.createdAt) BETWEEN :fromDate AND :toDate";
		}
		if (discountStatus != null && !discountStatus.isEmpty()) {
			hql += " AND d.status = :discountStatus";
		}

		Query query = session.createQuery(hql);
		if (discountCode != null && !discountCode.isEmpty()) {
			query.setParameter("discountCode", "%" + discountCode + "%");
		}
		if (discountType != null && !discountType.isEmpty()) {
			query.setParameter("discountType", discountType);
		}
		if (minValue != null && !minValue.isEmpty()) {
			// Kiểm tra nếu minValue có dấu '%'
			if (minValue.endsWith("%")) {
				// Loại bỏ dấu '%' và chuyển thành Long
				minValue = minValue.substring(0, minValue.length() - 1);
				// Chuyển minValue thành kiểu Long (số)
				query.setParameter("minValue", (long) (Long.parseLong(minValue))); // Chuyển giá trị phần trăm thành giá
																					// trị số
			} else {
				// Chuyển minValue thành kiểu Long (số)
				query.setParameter("minValue", Long.parseLong(minValue));
			}
		}

		if (maxValue != null && !maxValue.isEmpty()) {
			// Kiểm tra nếu maxValue có dấu '%'
			if (maxValue.endsWith("%")) {
				// Loại bỏ dấu '%' và chuyển thành Long
				maxValue = maxValue.substring(0, maxValue.length() - 1);
				// Chuyển maxValue thành kiểu Long (số)
				query.setParameter("maxValue", (long) (Long.parseLong(maxValue))); // Chuyển giá trị phần trăm thành giá
																					// trị số
			} else {
				// Chuyển maxValue thành kiểu Long (số)
				query.setParameter("maxValue", Long.parseLong(maxValue));
			}
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
		if (discountStatus != null && !discountStatus.isEmpty()) {
			query.setParameter("discountStatus", discountStatus);
		}
		// System.out.println("hql: " + hql);
		List<DiscountsEntity> orders = query.list();
		return orders;
	}

	public int getUsedCountByDiscountId(Long discountId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT COUNT(od) FROM Order_DiscountsEntity od WHERE od.discount_id.id = :discountId";
		Query query = session.createQuery(hql);
		query.setParameter("discountId", discountId);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	public void updateStatusDiscounts() {
		Session session = sessionFactory.getCurrentSession();
		// Khởi tạo đối tượng Timestamp cho ngày hiện tại
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());

		List<DiscountsEntity> allDiscounts = getAllDiscounts();

		for (DiscountsEntity discount : allDiscounts) {
			int usedCount = getUsedCountByDiscountId(discount.getId());
			// System.out.println("Used: " + usedCount);
			// Kiểm tra nếu ngày kết thúc của discount nhỏ hơn currentDate
			if (!discount.getStatus().equals("inactive")) {
				if (discount.getEndDate() != null && discount.getEndDate().before(currentDate)) {
					// Cập nhật trạng thái thành 'expired'
					discount.setStatus("expired");

					// Lưu lại thay đổi trong session
					session.update(discount);
				}
				if (discount.getMaxUses() != null) {
					if (usedCount >= discount.getMaxUses()) {
						// Cập nhật trạng thái thành 'expired'
						// System.out.println("Vô đây à");
						discount.setStatus("expired");

						// Lưu lại thay đổi trong session
						session.update(discount);
					}
				}

			} else {
				if (discount.getEndDate() != null && discount.getEndDate().before(currentDate)) {
					// Cập nhật trạng thái thành 'expired'
					discount.setStatus("active");

					// Lưu lại thay đổi trong session
					session.update(discount);
				}
			}

		}
		session.flush();
	}

	public DiscountsEntity findDiscountById(Long discount_id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM DiscountsEntity where id = :discount_id";
		Query query = session.createQuery(hql);
		query.setParameter("discount_id", discount_id);
		DiscountsEntity discount = (DiscountsEntity) query.uniqueResult();
		return discount;
	}

	public boolean checkDiscountCodeExist(String code) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From DiscountsEntity where code = :code";
		Query query = session.createQuery(hql);
		query.setParameter("code", code);
		DiscountsEntity discount = (DiscountsEntity) query.uniqueResult();
		System.out.println("discount: " + discount);
		if (discount == null) {
			// System.out.println("Mã giảm giá này chưa tồn tại");
			return false;
		} else {
			// System.out.println("Đã tồn tại");
			return true;
		}
	}

	public String createNewDiscount(DiscountsEntity newDiscount, List<Long> subcategories_id) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(newDiscount);
			// Kiểm tra nếu subcategory đã có discount active
			if ("categories".equals(newDiscount.getApplyTo())) {
				for (Long subcategoryId : subcategories_id) {
					boolean hasActiveDiscount = checkIfSubcategoryHasActiveDiscount(subcategoryId,
							newDiscount.getStartDate(), newDiscount.getEndDate());

					if (hasActiveDiscount) {
						// Nếu subcategory đã có discount active trong thời gian này, throw exception
						// hoặc return lỗi
						return "This subcategory already has an active discount during this period";
					}

					// Lấy danh sách sách của mỗi subcategory
					List<BooksEntity> books = booksDAO.getBooksBySubcategory(subcategoryId);

					// Thêm các sách vào bảng Book_Discount
					for (BooksEntity book : books) {
						Book_DiscountsEntity bookDiscount = new Book_DiscountsEntity();
						bookDiscount.setDiscount_id(newDiscount); // Thiết lập discount_id
						bookDiscount.setBook_id(book); // Thiết lập book_id
						session.save(bookDiscount); // Lưu vào bảng Book_Discount
					}
				}
			}

			return "success"; // Nếu mọi thứ đều ok, trả về thành công
		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nếu có lỗi, trả về thông báo lỗi
		}
	}

	// Phương thức kiểm tra nếu subcategory đã có discount active trong thời gian
	// này
	public boolean checkIfSubcategoryHasActiveDiscount(Long subcategoryId, Date startDate, Date endDate) {
		String hql = "SELECT COUNT(d) FROM Book_DiscountsEntity bde " + "JOIN bde.discount_id d "
				+ "WHERE bde.book_id IN (SELECT b.id FROM BooksEntity b WHERE b.subcategoriesEntity.id = :subcategoryId) "
				+ "AND d.startDate <= :endDate " + "AND d.endDate >= :startDate " + "AND d.status = 'active'";

		Long count = (Long) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("subcategoryId", subcategoryId).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).uniqueResult();

		return count > 0;
	}

	public String updateDiscount(Long discountId, DiscountsEntity updatedDiscount, List<Long> subcategories_id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			// Lấy discount hiện tại từ database theo discountId
			DiscountsEntity existingDiscount = (DiscountsEntity) session.get(DiscountsEntity.class, discountId);

			if (existingDiscount == null) {
				return "Discount not found"; // Nếu không tìm thấy discount, trả về thông báo lỗi
			}

			// Cập nhật thông tin discount mới
			existingDiscount.setCode(updatedDiscount.getCode());
			existingDiscount.setDiscountType(updatedDiscount.getDiscountType());
			existingDiscount.setDiscountValue(updatedDiscount.getDiscountValue());
			existingDiscount.setApplyTo(updatedDiscount.getApplyTo());
			existingDiscount.setStartDate(updatedDiscount.getStartDate());
			existingDiscount.setEndDate(updatedDiscount.getEndDate());
			existingDiscount.setMinOrderValue(updatedDiscount.getMinOrderValue());
			existingDiscount.setMaxUses(updatedDiscount.getMaxUses());
			existingDiscount.setStatus(updatedDiscount.getStatus());
			// System.out.println("updatedDiscount.getStatus(): " +
			// updatedDiscount.getStatus());
			// System.out.println("updatedDiscount.getDiscountValue(): "
			// +updatedDiscount.getDiscountValue());
			// Cập nhật thời gian sửa đổi
			Date currentTimestamp = new Date();
			existingDiscount.setUpdatedAt(currentTimestamp);

			// Cập nhật discount trong database
			// System.out.println("existingDiscount: " +
			// existingDiscount.getDiscountValue());
			session.update(existingDiscount);

			// Kiểm tra xem subcategory có thay đổi không và chỉ khi discount áp dụng cho
			// categories
			if ("categories".equals(updatedDiscount.getApplyTo()) && subcategories_id != null
					&& !subcategories_id.isEmpty()) {
				// Trước tiên, xóa tất cả các Book_Discount cũ liên quan đến discount này
				String hqlDelete = "DELETE FROM Book_DiscountsEntity WHERE discount_id = :discountId";
				session.createQuery(hqlDelete).setParameter("discountId", existingDiscount).executeUpdate();

				// Thêm các sách mới vào bảng Book_Discount cho các subcategory đã chọn
				for (Long subcategoryId : subcategories_id) {
					// Lấy danh sách sách của mỗi subcategory
					List<BooksEntity> books = booksDAO.getBooksBySubcategory(subcategoryId);

					// Thêm các sách vào bảng Book_Discount
					for (BooksEntity book : books) {
						Book_DiscountsEntity bookDiscount = new Book_DiscountsEntity();
						bookDiscount.setDiscount_id(existingDiscount); // Thiết lập discount_id
						bookDiscount.setBook_id(book); // Thiết lập book_id
						session.save(bookDiscount); // Lưu vào bảng Book_Discount
					}
				}
			}

			// Trả về thành công nếu không có lỗi
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nếu có lỗi, trả về thông báo lỗi
		}
	}

	public List<Long> foundCategoryOfDiscount(Long discount_id) {
		Session session = sessionFactory.getCurrentSession();

		// Viết HQL để lấy id của Category và Subcategory
		String hql = "SELECT sc.id, c.id, sc.name, c.name " + "FROM Book_DiscountsEntity bd " + "JOIN bd.book_id b "
				+ "JOIN b.subcategoriesEntity sc " + "JOIN sc.categoriesEntity c "
				+ "WHERE bd.discount_id.id = :discount_id";

		// Tạo query từ HQL
		Query query = session.createQuery(hql);
		query.setParameter("discount_id", discount_id); // Thiết lập tham số discount_id vào query

		// Thực thi truy vấn và trả về kết quả
		List<Object[]> result = query.list();

		// Tạo danh sách để chứa các id của Category và Subcategory
		List<Long> ids = new ArrayList<>();
		for (Object[] row : result) {
			Long subcategoryId = (Long) row[0]; // id của Subcategory
			Long categoryId = (Long) row[1]; // id của Category
			ids.add(categoryId);
			ids.add(subcategoryId);

		}

		return ids;
	}

	public Map<String, List<String>> getNameCategoryOfDiscount(Long discount_id) {
		Session session = sessionFactory.getCurrentSession();

		// Viết HQL để lấy tên của Category và Subcategory
		String hql = "SELECT c.name, sc.name " + "FROM Book_DiscountsEntity bd " + "JOIN bd.book_id b "
				+ "JOIN b.subcategoriesEntity sc " + "JOIN sc.categoriesEntity c "
				+ "WHERE bd.discount_id.id = :discount_id";

		// Tạo query từ HQL
		Query query = session.createQuery(hql);
		query.setParameter("discount_id", discount_id);

		// Thực thi truy vấn và trả về kết quả
		List<Object[]> result = query.list();

		// Tạo Map để chứa các cặp Category và danh sách Subcategory
		Map<String, List<String>> categorySubcategoryMap = new HashMap<>();
		for (Object[] row : result) {
			String categoryName = (String) row[0]; // Tên Category
			String subcategoryName = (String) row[1]; // Tên Subcategory

			// Kiểm tra xem Category đã tồn tại trong map chưa, nếu chưa thì thêm mới
			if (!categorySubcategoryMap.containsKey(categoryName)) {
				categorySubcategoryMap.put(categoryName, new ArrayList<>());
			}

			// Thêm Subcategory vào danh sách Subcategory của Category
			categorySubcategoryMap.get(categoryName).add(subcategoryName);
		}

		return categorySubcategoryMap;
	}

	public String deleteBookDiscountsByDiscountId(Long discountId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			// Xóa các bản ghi Book_DiscountsEntity liên quan đến discountId
			String hqlDelete = "DELETE FROM Book_DiscountsEntity WHERE discount_id.id = :discountId";
			int result = session.createQuery(hqlDelete).setParameter("discountId", discountId).executeUpdate();

			if (result > 0) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
			// Xử lý lỗi nếu cần
		}
		return null;
	}

	public boolean deleteDiscount(DiscountsEntity discountDelete) {
		Session session = sessionFactory.getCurrentSession();
		try {
			if (discountDelete != null) {
				if (discountDelete.getApplyTo().equals("categories")) {
					String deleteBookDiscountsByDiscountId = deleteBookDiscountsByDiscountId(discountDelete.getId());
					if (deleteBookDiscountsByDiscountId.equals("success")) {
						session.delete(discountDelete);
						return true;
					} else {
						return false;
					}
				} else {
					session.delete(discountDelete);
					return true;
				}

			} else {
				// Nếu discount không tồn tại
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public Double getDiscountValueByBookId(Long bookId) {
		Session session = sessionFactory.getCurrentSession();

		// Câu lệnh HQL để lấy giá trị giảm giá cho cuốn sách, kiểm tra trạng thái và
		// thời gian hợp lệ của discount
		String hql = "SELECT bd.discount_id.discountValue " + "FROM Book_DiscountsEntity bd "
				+ "WHERE bd.book_id.id = :bookId " + "AND bd.discount_id.status = 'Active' "
				+ "AND CURRENT_TIMESTAMP BETWEEN bd.discount_id.startDate AND bd.discount_id.endDate";

		// Thực thi truy vấn và lấy kết quả
		Object result = session.createQuery(hql).setParameter("bookId", bookId).uniqueResult();

		// Nếu không tìm thấy giảm giá hợp lệ, trả về 0
		if (result == null) {
			return 0.0;
		}

		// Chuyển đổi giá trị trả về từ Object sang Long và sau đó sang Double
		Long discountValueLong = (Long) result;
		return discountValueLong.doubleValue();
	}

	public List<Double> getDiscountsValueByBookId(List<BooksEntity> books) {
		Session session = sessionFactory.getCurrentSession();
		List<Double> discountValues = new ArrayList<>();

		for (BooksEntity book : books) {
			String hql = "SELECT bd.discount_id.discountValue " + "FROM Book_DiscountsEntity bd "
					+ "WHERE bd.book_id.id = :bookId " + "AND bd.discount_id.applyTo = 'categories' "
					+ "AND bd.discount_id.status = 'active' "
					+ "AND CURRENT_TIMESTAMP BETWEEN bd.discount_id.startDate AND bd.discount_id.endDate";

			Object result = session.createQuery(hql).setParameter("bookId", book.getId()).uniqueResult();

			if (result == null) {
				discountValues.add(0.0);
			} else {
				Long discountValue = (Long) result;
				discountValues.add(discountValue.doubleValue());
			}
		}

		return discountValues;
	}

	public boolean createOrderDiscount(Order_DiscountsEntity orderDiscount) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(orderDiscount);
			return true;
		} catch (Exception e) {
			// Spring sẽ tự động rollback nếu exception xảy ra
			e.printStackTrace();
			return false;
		}
	}
}

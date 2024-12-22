package bookstore.Controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.DAO.BooksDAO;
import bookstore.DAO.CartDAO;
import bookstore.DAO.CategoriesDAO;
import bookstore.DAO.DiscountsDAO;
import bookstore.DAO.InventoryDAO;
import bookstore.DAO.OrderDetailDAO;
import bookstore.DAO.RatingsDAO;
import bookstore.DAO.SubcategoriesDAO;
import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Entity.RatingsEntity;
import bookstore.Entity.SubcategoriesEntity;
import bookstore.Entity.UsersEntity;

@Controller
public class ClientController {
	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private SubcategoriesDAO subcategoriesDAO;
	@Autowired
	private BooksDAO booksDAO;
	@Autowired
	private DiscountsDAO discountsDAO;
	@Autowired
	private RatingsDAO ratingsDAO;
	@Autowired
	private InventoryDAO inventoryDAO;
	@Autowired
	private OrderDetailDAO orderDetailDAO;
	@Autowired
	private CartDAO cartDAO;

	@RequestMapping(value = "/index")
	public String index(ModelMap model, HttpSession session) {
		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
		List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
		UsersEntity userSession = (UsersEntity) session.getAttribute("user");

		List<BooksEntity> bookList = booksDAO.listBooks();

		Map<Long, Double> bookDiscounts = new HashMap<>();

		for (BooksEntity book : bookList) {
			Double discountValue = discountsDAO.getDiscountValueByBookId(book.getId());

			if (discountValue == null) {
				discountValue = 0.0;
			}
			bookDiscounts.put(book.getId(), discountValue);
			System.out.println(bookDiscounts);
		}

		if (userSession != null) {
			Long countBooksInCart = cartDAO.countItemsInCart(userSession.getCart().getId());
			session.setAttribute("countBooksInCart", countBooksInCart);
		} else {
			session.setAttribute("countBooksInCart", 0);
		}

		model.addAttribute("user", userSession);

		discountsDAO.updateStatusDiscounts();

	    Map<String, Object> bestSellingData = orderDetailDAO.getBestSellingBook();
	    if (bestSellingData != null) {
	        BooksEntity bestSellingBook = (BooksEntity) bestSellingData.get("bestSellingBook");

	        model.addAttribute("bestSellingBook", bestSellingBook);
	    }

		model.addAttribute("Categories", listCategories);
		model.addAttribute("SubCategories", listSubCategories);

		model.addAttribute("user", userSession);
		model.addAttribute("bookList", bookList);
		model.addAttribute("bookDiscounts", bookDiscounts);
		return "client/index";
	}

	@RequestMapping("/allProduct")
	public String allProduct(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "16") int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "newest", required = false) String sortBy, ModelMap model) {

		List<BooksEntity> listAllBook = booksDAO.getAllBook(page, pageSize, sortBy);
		int totalPages = booksDAO.getTotalPagesOfAllBooks(pageSize);

		List<Object[]> countBookEachCategory = booksDAO.countBookEachCategory();

		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
		List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();

		Map<Long, Double> bookDiscounts = new HashMap<>();

		for (BooksEntity book : listAllBook) {
			Double discountValue = discountsDAO.getDiscountValueByBookId(book.getId());

			if (discountValue == null) {
				discountValue = 0.0;
			}
			bookDiscounts.put(book.getId(), discountValue);
		}

		long countAllBooks = booksDAO.countAllBook();
		model.addAttribute("countAllBooks", countAllBooks);

		model.addAttribute("Categories", listCategories);
		model.addAttribute("SubCategories", listSubCategories);
		model.addAttribute("countBookEachCategory", countBookEachCategory);
		model.addAttribute("bookDiscounts", bookDiscounts);
		model.addAttribute("bookList", listAllBook);
		model.addAttribute("currentPage", page); // Trang hiện tại
		model.addAttribute("totalPages", totalPages); // Tổng số trang
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("sortBy", sortBy);
		return "client/showAllBooks";
	}

	@RequestMapping("/search")
	public String searchBooks(@RequestParam(value = "q", required = false) String searchQuery,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "16") int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "newest", required = false) String sortBy, ModelMap model) {
		// System.out.println("pageSize: " + pageSize);
		// System.out.println("Search Query: " + searchQuery);
		List<BooksEntity> bookList = booksDAO.search(searchQuery, page, pageSize, sortBy);
		// System.out.println(bookList.size());
		int totalPages = booksDAO.getTotalPagesOfSearch(searchQuery, pageSize);
		// System.out.println(totalPages);
		List<Object[]> countBookEachCategory = booksDAO.countBookEachCategory();

		long countAllBooks = booksDAO.countAllBook();
		model.addAttribute("countAllBooks", countAllBooks);

		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
		List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();

		model.addAttribute("Categories", listCategories);
		model.addAttribute("SubCategories", listSubCategories);

		model.addAttribute("countBookEachCategory", countBookEachCategory);
		model.addAttribute("bookList", bookList);
		model.addAttribute("currentPage", page); // Trang hiện tại
		model.addAttribute("totalPages", totalPages); // Tổng số trang
		model.addAttribute("pageSize", pageSize); // Số lượng sách 1 trang
		model.addAttribute("searchQuery", searchQuery);
		model.addAttribute("sortBy", sortBy);
		return "client/search";
	}

	@RequestMapping(value = "productdetail/{productId}", method = RequestMethod.GET)
	public String productdetail(ModelMap model, @PathVariable("productId") Long id) {
		BooksEntity book = booksDAO.getBookByIdHQL(id);
		model.addAttribute("book", book);
		Double discount = discountsDAO.getDiscountValueByBookId(id);
		model.addAttribute("discount", discount);

		// System.out.println(discount);
		List<BooksEntity> books_category = booksDAO
				.listBookOfCategoryRan(book.getSubcategoriesEntity().getCategoriesEntity().getId());
		System.out.println(books_category);
		model.addAttribute("books_category", books_category);

		List<Double> discounts = discountsDAO.getDiscountsValueByBookId(books_category);
		model.addAttribute("discounts", discounts);

		Double ratingAVR = ratingsDAO.getAverageRatingByBookId(id);
		int averageRating = (int) Math.floor(ratingAVR);
		model.addAttribute("ratingAVR", averageRating);

//		InventoryEntity inventory = inventoryDAO.getInventoryByBookId(id);
//		model.addAttribute("stock_quantity", inventory.getStock_quantity());
		List<Object[]> countBookEachCategory = booksDAO.countBookEachCategory();
		model.addAttribute("countBookEachCategory", countBookEachCategory);

		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
		List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();

		model.addAttribute("Categories", listCategories);
		model.addAttribute("SubCategories", listSubCategories);
		return "client/productdetail";
	}

	@RequestMapping(value = "productdetail/{productId}/reviews", method = RequestMethod.GET)
	public String getReviewsByProductId(ModelMap model, @PathVariable("productId") Long productId) {
		// Gọi DAO để lấy danh sách đánh giá
		List<RatingsEntity> reviews = ratingsDAO.getRatingsByBookIdApp(productId);

		// Thêm danh sách reviews vào model
		model.addAttribute("reviews", reviews);
		model.addAttribute("bookId", productId);

		// Trả về view reviews.jsp
		return "client/reviews";
	}
}

package bookstore.Controller.client;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.DAO.BooksDAO;
import bookstore.DAO.CategoriesDAO;
import bookstore.DAO.DiscountsDAO;
import bookstore.DAO.InventoryDAO;
import bookstore.DAO.RatingsDAO;
import bookstore.DAO.SubcategoriesDAO;
import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.InventoryEntity;
import bookstore.Entity.RatingsEntity;
import bookstore.Entity.SubcategoriesEntity;


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
    
    @RequestMapping(value = "/index")
	public String index(ModelMap model) {
		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();

        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
        return "client/index";
	}
	
	
    @RequestMapping("/search")
    public String searchBooks(@RequestParam(value = "q", required = false) String searchQuery, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize, ModelMap model) {
    	//System.out.println("pageSize: " + pageSize);
        //System.out.println("Search Query: " + searchQuery);
        List<BooksEntity> bookList = booksDAO.search(searchQuery, page, pageSize);
        //System.out.println(bookList.size());
        int totalPages = booksDAO.getTotalPagesOfSearch(searchQuery, pageSize);
        //System.out.println(totalPages);
        List<Object[]> countBookEachCategory = booksDAO.countBookEachCategory();
        
        
        
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
        
        return "client/search";
    }

    @RequestMapping(value = "productdetail/{productId}", method = RequestMethod.GET)
	public String productdetail(ModelMap model, @PathVariable("productId") Long id) {
		BooksEntity book = booksDAO.getBookByIdHQL(id);
        model.addAttribute("book", book);
		System.out.println(book.getDescription());
        Double discount = discountsDAO.getDiscountValueByBookId(id);
		model.addAttribute("discount",discount);
		 
		//System.out.println(discount); 
		List<BooksEntity> books_category = booksDAO.getRandBooksBySubcategory(book.getSubcategoriesEntity().getId());
		System.out.println(books_category); 
		model.addAttribute("books_category", books_category); 
		
		List<Double> discounts = discountsDAO.getDiscountsValueByBookId(books_category);
		model.addAttribute("discounts",discounts);
		
		Double ratingAVR = ratingsDAO.getAverageRatingByBookId(id);
		int averageRating = (int) Math.floor(ratingAVR);
		model.addAttribute("ratingAVR", averageRating);
		
		InventoryEntity inventory = inventoryDAO.getInventoryByBookId(id);
		model.addAttribute("stock_quantity", inventory.getStock_quantity());
		
		return "client/productdetail";
	}
    
    @RequestMapping(value = "productdetail/{productId}/reviews", method = RequestMethod.GET)
	public String getReviewsByProductId(ModelMap model, @PathVariable("productId") Long productId) {
	    // Gọi DAO để lấy danh sách đánh giá
	    List<RatingsEntity> reviews = ratingsDAO.getRatingsByBookId(productId);

	    // Thêm danh sách reviews vào model
	    model.addAttribute("reviews", reviews);
	    model.addAttribute("bookId", productId);

	    // Trả về view reviews.jsp
	    return "client/reviews";
	}
}

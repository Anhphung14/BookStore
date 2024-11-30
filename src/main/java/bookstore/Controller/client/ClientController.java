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
import bookstore.DAO.SubcategoriesDAO;
import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;
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
        Double discount = discountsDAO.getDiscountValueByBookId(id);
        model.addAttribute("discount",discount);
		System.out.println(discount); 
		List<BooksEntity> books_category = booksDAO.getBooksBySubcategory(book.getSubcategoriesEntity().getId());
		System.out.println(books_category); 
		model.addAttribute("books_category", books_category); 
		List<Double> discounts = discountsDAO.getDiscountsValueByBookId(books_category);
		model.addAttribute("discounts",discounts);
		 
		return "client/productdetail";
	}
}

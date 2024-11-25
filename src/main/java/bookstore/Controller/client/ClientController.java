package bookstore.Controller.client;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.DAO.BookDAO;
import bookstore.DAO.CategoryDAO;
import bookstore.DAO.SubcategoryDAO;
import bookstore.Entity.Book;
import bookstore.Entity.Category;
import bookstore.Entity.SubcategoriesEntity;


@Controller
public class ClientController {
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private SubcategoryDAO subcategoryDAO;
    @Autowired
    private BookDAO bookDAO;
    
    
    
    
    @RequestMapping(value = "/index")
	public String index(ModelMap model) {
		List<Category> listCategories = categoryDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoryDAO.findAll();

        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
        return "client/index";
	}
	
	
    @RequestMapping("/search")
    public String searchBooks(@RequestParam(value = "q", required = false) String searchQuery, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize, ModelMap model) {
    	//System.out.println("pageSize: " + pageSize);
        //System.out.println("Search Query: " + searchQuery);
        List<Book> bookList = bookDAO.search(searchQuery, page, pageSize);
        //System.out.println(bookList.size());
        int totalPages = bookDAO.getTotalPagesOfSearch(searchQuery, pageSize);
        //System.out.println(totalPages);
        List<Object[]> countBookEachCategory = bookDAO.countBookEachCategory();
        
        
        
        List<Category> listCategories = categoryDAO.findAllCategories();
        List<SubcategoriesEntity> listSubCategories = subcategoryDAO.findAll();

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

}

package bookstore.Controller.client;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.DAO.BookDAO;
import bookstore.DAO.CategoryDAO;
import bookstore.DAO.SubcategoryDAO;
import bookstore.Entity.Book;
import bookstore.Entity.Category;
import bookstore.Entity.SubcategoriesEntity;

@Controller
@RequestMapping("/categories/")
public class CategoriesController {
	@Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private SubcategoryDAO subcategoryDAO;
	@Autowired
    private BookDAO bookDAO;
    
    @RequestMapping("/{slug}/{slugSub}.htm")
    public String getSubCategoryPage(@PathVariable("slug") String slug, @PathVariable("slugSub") String slugSub,  @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize, ModelMap model) {
        List<Category> listCategories = categoryDAO.findAllCategories();
        //List<Object[]> danhMuc = new ArrayList<>();
        Object[] danhMuc = new Object[4]; 
        for (Category cg : listCategories) {
            for (SubcategoriesEntity scg : cg.getSubcategories()) {
                if (scg.getSlug().equals(slugSub)) {
                	danhMuc[0] = cg.getName();       
                	danhMuc[1] = scg.getName();      
                	danhMuc[2] = cg.getSlug(); 
                	danhMuc[3] = scg.getSlug();  
                    //danhMuc.add(item);            
                    break; // Dừng vòng lặp khi tìm thấy danh mục con phù hợp
                }
            }
        }

        List<Object[]> countBookEachCategory = bookDAO.countBookEachCategory();
        //Phân trang
        //int pageSize = 1;
        List<Book> bookList = bookDAO.listBookOfSubCategory((String) danhMuc[0], (String) danhMuc[1], page, pageSize);
        int totalPages = bookDAO.getTotalPagesOfSubBook((String) danhMuc[1], pageSize);
        
        
     
        List<SubcategoriesEntity> listSubCategories = subcategoryDAO.findAll();

        model.addAttribute("Categories", listCategories);
        model.addAttribute("SubCategories", listSubCategories);
        
        model.addAttribute("danhMuc", danhMuc);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("countBookEachCategory", countBookEachCategory);
        model.addAttribute("bookList", bookList);
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("totalPages", totalPages); // Tổng số trang
        model.addAttribute("pageSize", pageSize);
        // Trả về trang tương ứng
        return "client/Categories/SubCategoryBooks";
    }
    
    @RequestMapping("{idCategory}.htm")
    public String getCategoryPage(@PathVariable("idCategory") Long idCategory,  @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize, ModelMap model) {
		List<Book> bookList = bookDAO.listBookOfCategory(idCategory, page, pageSize);
		List<Category> listCategories = categoryDAO.findAllCategories();
		List<Object[]> countBookEachCategory = bookDAO.countBookEachCategory();
		Category danhMuc = categoryDAO.findCategoryById(idCategory);
		//Phân trang
		//int pageSize = 1;
		int totalPages = bookDAO.getTotalPagesOfCateBook( idCategory , pageSize);
         
         
      
	     List<SubcategoriesEntity> listSubCategories = subcategoryDAO.findAll();
	
	     model.addAttribute("danhMuc", danhMuc);
	     model.addAttribute("Categories", listCategories);
	     model.addAttribute("SubCategories", listSubCategories);
	     
	     //model.addAttribute("danhMuc", danhMuc);
	     model.addAttribute("listCategories", listCategories);
	     model.addAttribute("countBookEachCategory", countBookEachCategory);
	     model.addAttribute("bookList", bookList);
	     model.addAttribute("currentPage", page); // Trang hiện tại
	     model.addAttribute("totalPages", totalPages); // Tổng số trang
	     model.addAttribute("pageSize", pageSize);
	     // Trả về trang tương ứng
	     return "client/Categories/CategoryBooks";
    }
}

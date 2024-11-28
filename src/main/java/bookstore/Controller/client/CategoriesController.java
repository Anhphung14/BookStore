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

import bookstore.DAO.BooksDAO;
import bookstore.DAO.CategoriesDAO;
import bookstore.DAO.SubcategoriesDAO;
import bookstore.Entity.BooksEntity;
import bookstore.Entity.CategoriesEntity;
import bookstore.Entity.SubcategoriesEntity;

@Controller
@RequestMapping("/categories/")
public class CategoriesController {
	@Autowired
    private CategoriesDAO categoriesDAO;
    @Autowired
    private SubcategoriesDAO subcategoriesDAO;
	@Autowired
    private BooksDAO booksDAO;
    
    @RequestMapping("/{slug}/{slugSub}.htm")
    public String getSubCategoryPage(@PathVariable("slug") String slug, @PathVariable("slugSub") String slugSub,  @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize, ModelMap model) {
        List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
        //List<Object[]> danhMuc = new ArrayList<>();
        Object[] danhMuc = new Object[4]; 
        for (CategoriesEntity cg : listCategories) {
            for (SubcategoriesEntity scg : cg.getSubcategoriesEntity()) {
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

        List<Object[]> countBookEachCategory = booksDAO.countBookEachCategory();
        //Phân trang
        //int pageSize = 1;
        List<BooksEntity> bookList = booksDAO.listBookOfSubCategory((String) danhMuc[0], (String) danhMuc[1], page, pageSize);
        int totalPages = booksDAO.getTotalPagesOfSubBook((String) danhMuc[1], pageSize);
        
        
     
        List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();

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
		List<BooksEntity> bookList = booksDAO.listBookOfCategory(idCategory, page, pageSize);
		List<CategoriesEntity> listCategories = categoriesDAO.findAllCategories();
		List<Object[]> countBookEachCategory = booksDAO.countBookEachCategory();
		CategoriesEntity danhMuc = categoriesDAO.findCategoryById(idCategory);
		//Phân trang
		//int pageSize = 1;
		int totalPages = booksDAO.getTotalPagesOfCateBook( idCategory , pageSize);
         
         
      
	     List<SubcategoriesEntity> listSubCategories = subcategoriesDAO.findAll();
	
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

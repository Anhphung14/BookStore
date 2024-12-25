package bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.DAO.RatingsDAO;
import bookstore.Entity.RatingsEntity;
@Transactional
@Controller
@RequestMapping("/admin1337")
public class RatingsController {
	@Autowired
	private RatingsDAO ratingsDAO;
	
	@PreAuthorize("hasAuthority('VIEW_RATING')")
	@RequestMapping(value = "/ratings", method = RequestMethod.GET)
	public String ratings(ModelMap model) {
//		List<RatingsEntity> listUn = ratingsDAO.getRatingsByStatus0();
		List<RatingsEntity> listAp = ratingsDAO.getRatingsByStatus1();
//		List<RatingsEntity> listRe = ratingsDAO.getRatingsByStatus2();
		model.addAttribute("listRatings", listAp);
//		model.addAttribute("listAp", listAp);
//		model.addAttribute("listRe", listRe);
		return "ratings/index";
	}
	
	@PreAuthorize("hasAuthority('VIEW_RATING')")
	@RequestMapping(value = "/ratings", method = RequestMethod.POST)
	public String ratingsSearch(ModelMap model, @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "searchOption", required = false) String searchOption) {
		//List<RatingsEntity> listUn = ratingsDAO.getRatingsByStatus0();
		List<RatingsEntity> listAp = ratingsDAO.getRatingsByStatus1();
		//List<RatingsEntity> listRe = ratingsDAO.getRatingsByStatus2();
		
		if (searchOption.equals("bookName") && !search.isEmpty()) {
			//listUn = ratingsDAO.searchRatingsByBook(search, 0);
			listAp = ratingsDAO.searchRatingsByBook(search, 1);
			//listRe = ratingsDAO.searchRatingsByBook(search, 2);
		}
		
		if (searchOption.equals("reviewerName") && !search.isEmpty()) {
			//listUn = ratingsDAO.searchRatingsByUser(search, 0);
			listAp = ratingsDAO.searchRatingsByUser(search, 1);
			//listRe = ratingsDAO.searchRatingsByUser(search, 2);
		}
		
		if (search.isEmpty()) {
			//listUn = ratingsDAO.getRatingsByStatus0();
			listAp = ratingsDAO.getRatingsByStatus1();
			//listRe = ratingsDAO.getRatingsByStatus2();
		}
		
		//model.addAttribute("listUn", listUn);
		model.addAttribute("listRatings", listAp);
		//model.addAttribute("listRe", listRe);
		return "ratings/index";
	}
	
	@RequestMapping(value = "/rating/update/{ratingId}", method = RequestMethod.GET)
	public String update_Rating(@PathVariable("ratingId") String idd, ModelMap model) {
		if (!idd.matches("\\d+")) {
	    	return "redirect:/admin1337/ratings";
	    }
	    
		Long id = Long.parseLong(idd);
		RatingsEntity rating = ratingsDAO.getRatingById(id);
		if(rating == null) {
			return "redirect:/admin1337/ratings";
		}
		
		model.addAttribute("rating", rating);
		return "ratings/update";
	}
	
	@RequestMapping(value = "/rating/update", method = RequestMethod.POST)
	public String submit_Update(@RequestParam("id") Long id, @RequestParam("status") int status) {
		int rs = ratingsDAO.updateStatus(id, status);
		return "redirect:/admin1337/ratings";
	}
	
	
	
}

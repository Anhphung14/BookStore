package bookstore.Controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import bookstore.Entity.OrdersEntity;
import bookstore.Entity.UsersEntity;

@Controller
@Transactional
@RequestMapping("/admin1337")
public class HomeController {
	@Autowired
	SessionFactory factory;
	

	@RequestMapping(value = "/home")
	public String home(ModelMap model, HttpSession session) {
	    UsersEntity user_session = (UsersEntity) session.getAttribute("user");
	    
	    if (user_session == null) {
	        return "redirect:/signin.htm";
	    }
	    
	    long userCount = getUserCount();
	    model.addAttribute("userCount", userCount);
	    
	    long orderCount = getOrderCount();
	    model.addAttribute("orderCount", orderCount);
	    
	    long totalAmountForStatus3 = getTotalAmountForStatus3();
	    String formattedAmount = formatCurrency(totalAmountForStatus3);
	    model.addAttribute("totalAmountForStatus3", formattedAmount);
	    
	    List<String> months = getLastFiveMonths();
	    List<Long> revenues = getRevenuesForLastFiveMonths();

	    model.addAttribute("months", new Gson().toJson(months));
	    model.addAttribute("revenues", new Gson().toJson(revenues)); 
	    
	    List<OrdersEntity> orders = getOrdersWithUserInfo(); 
	    model.addAttribute("orders", orders);
	    

	    return "statistics/index";
	}
	
	private List<OrdersEntity> getOrdersWithUserInfo() {
	    Session session = factory.getCurrentSession();
	    // Sắp xếp theo ngày tạo giảm dần và giới hạn số lượng đơn hàng là 7
	    String hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.user ORDER BY o.createdAt DESC";
	    Query query = session.createQuery(hql);
	    query.setMaxResults(7);  // Giới hạn kết quả trả về là 7
	    return query.list();
	}



    private long getUserCount() {
        Session session = factory.getCurrentSession();
        String hql = "SELECT count(*) FROM UsersEntity";
        Long count = (Long) session.createQuery(hql).uniqueResult();
        return count != null ? count : 0;
    }
    
    private long getOrderCount() {
        Session session = factory.getCurrentSession();
        String hql = "SELECT count(*) FROM OrdersEntity WHERE orderStatus = 'Hoàn thành'";
        Long count = (Long) session.createQuery(hql).uniqueResult();
        return count != null ? count : 0;
    }
    
    public String formatCurrency(long amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return format.format(amount);
    }
    
    public long getTotalAmountForStatus3() {
        Session session = factory.getCurrentSession();
        String hql = "SELECT SUM(o.totalPrice) FROM OrdersEntity o WHERE LOWER(o.orderStatus) = LOWER('Hoàn thành')";
        Double totalAmount = (Double) session.createQuery(hql).uniqueResult();  
        return totalAmount != null ? totalAmount.longValue() : 0;  
    }

    private List<String> getLastFiveMonths() {
        List<String> months = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 5; i++) {
            LocalDate date = currentDate.minusMonths(i);
            String monthName = date.getMonth().getDisplayName(TextStyle.FULL, new Locale("vi", "VN"));
            months.add(monthName + " " + date.getYear()); 
        }

        Collections.reverse(months);
        return months;
    }

    private List<Long> getRevenuesForLastFiveMonths() {
        Session session = factory.getCurrentSession();
        List<Long> revenues = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 5; i++) {
            LocalDate date = currentDate.minusMonths(i);
            int month = date.getMonthValue();
            int year = date.getYear();

            String hql = "SELECT SUM(o.totalPrice) FROM OrdersEntity o " +
                    "WHERE EXTRACT(MONTH FROM o.createdAt) = :month " +
                    "AND EXTRACT(YEAR FROM o.createdAt) = :year " +
                    "AND o.orderStatus = 'Hoàn thành'";

            Object result = session.createQuery(hql)
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .uniqueResult();

            Long revenue = (result != null) ? 
                (result instanceof Double ? ((Double) result).longValue() : (Long) result) : 
                0L;
            revenues.add(revenue);
        }
        
        Collections.reverse(revenues);
        return revenues;
    }


}

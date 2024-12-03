package bookstore.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import bookstore.Entity.Address.*;

@Repository
@Transactional
public class ShippingAddressDAO {
	@Autowired
    SessionFactory sessionFactory;
	
	public List<ProvincesEntity> getListProvinces(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "From ProvincesEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	public Map<String, Map<String, List<String>>> getProvincesWithDistrictsAndWards() {
        Map<String, Map<String, List<String>>> result = new HashMap<>();

        // Lấy session Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Truy vấn tất cả provinces
        String hql = "FROM ProvincesEntity p " +
                "JOIN FETCH p.districtsEntity d " + 
                "ORDER BY p.name, d.name";

        Query query = session.createQuery(hql);
        List<ProvincesEntity> provinces = query.list();

        for (ProvincesEntity province : provinces) {
            Map<String, List<String>> districtsMap = new HashMap<>();

            // Lấy tất cả districts của province
            for (DistrictsEntity district : province.getDistrictsEntity()) {
                List<String> wardsNames = new ArrayList<>();
                
                // Lấy tất cả wards của district
                for (WardsEntity ward : district.getWardsEntity()) {
                    wardsNames.add(ward.getFullName());  // Lưu tên phường xã
                }
                
                // Thêm district và các wards vào map
                districtsMap.put(district.getFullName(), wardsNames);
            }

            // Thêm province và các districts vào result map
            result.put(province.getFullName(), districtsMap);
        }

        return result;
    }
}

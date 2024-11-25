package bookstore.Entity;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Categories")
public class CategoriesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "name", columnDefinition = "nvarchar")
	private String name;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date created_at;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date updated_at;
	
	@Transient
    private String slug;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	public String getSlug() {
        if (slug == null) {
            // Tính toán slug nếu chưa có
            slug = toSlug(name);
        }
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    @Override
    public String toString() {
    	return "id: " + id + " name: " + name ;
    }
    
    private String toSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        // Loại bỏ dấu
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String noDiacritics = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        // Tách chuỗi thành các từ, xử lý từng từ và nối lại bằng dấu gạch ngang
        return Arrays.stream(noDiacritics.split("[\\s/]+")) // Split theo khoảng trắng
                     .map(word -> word.replaceAll("[^a-zA-Z0-9]", "")) // Loại bỏ ký tự đặc biệt
                     .filter(word -> !word.isEmpty()) // Loại bỏ từ rỗng
                     .map(String::toLowerCase) // Chuyển thành chữ thường
                     .collect(Collectors.joining("-")); // Ghép lại bằng dấu gạch ngang
    }
	
}
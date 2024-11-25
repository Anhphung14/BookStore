package bookstore.Entity;


import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Categories")
public class Category{

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Quan hệ 1-N với Subcategories
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<SubcategoriesEntity> subcategoriesEntities;

    @Transient
    private String slug;
    
    
    // Constructors
    public Category() {
    }

    public Category(String name) {
        this.name = name;
        this.createdAt =  new Date();
        this.updatedAt =  new Date();
    }

    // Getters and Setters

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


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setSubcategories(Collection<SubcategoriesEntity> subcategoriesEntities) {
		this.subcategoriesEntities = subcategoriesEntities;
	}

	public Collection<SubcategoriesEntity> getSubcategories() {
		return subcategoriesEntities;
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

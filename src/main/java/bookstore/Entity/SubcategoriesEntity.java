package bookstore.Entity;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.*;


@Entity
@Table(name = "Subcategories")
public class SubcategoriesEntity{

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "subcategoriesEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<BooksEntity> books;

    // Getter and Setter
    public Collection<BooksEntity> getBooks() {
        return books;
    }

    public void setBooks(Collection<BooksEntity> books) {
        this.books = books;
    }
    
    @Transient
    private String slug;

    // Constructors
    public SubcategoriesEntity() {
    }

    public SubcategoriesEntity(String name, Category category, String slug) {
        this.name = name;
        this.category = category;
        this.createdAt =  new Date();
        this.updatedAt =  new Date();
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

	@Override
    public String toString() {
    	return "id: " + id + " name: " + name + " category: " + category;
    }
	
	public String toSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        // Chuẩn hóa chuỗi và loại bỏ dấu
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String noDiacritics = normalized.replaceAll("\\p{M}", ""); // Loại bỏ dấu chính xác

        // Giữ lại chữ cái, số, thay Đ/đ thành d, rồi loại bỏ ký tự đặc biệt
        noDiacritics = noDiacritics.replaceAll("[Đđ]", "d"); // Xử lý chữ Đ và đ
        return Arrays.stream(noDiacritics.split("\\s+")) // Split theo khoảng trắng
                     .map(word -> word.replaceAll("[^a-zA-Z0-9]", "")) // Loại bỏ ký tự đặc biệt
                     .filter(word -> !word.isEmpty()) // Loại bỏ từ rỗng
                     .map(String::toLowerCase) // Chuyển thành chữ thường
                     .collect(Collectors.joining("-")); // Ghép lại bằng dấu gạch ngang
    }
}


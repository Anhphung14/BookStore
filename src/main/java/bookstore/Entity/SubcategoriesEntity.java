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
    private CategoriesEntity categoriesEntity;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

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

    public SubcategoriesEntity(String name, CategoriesEntity categoriesEntity, String slug) {
        this.name = name;
        this.categoriesEntity = categoriesEntity;
        this.created_at =  new Date();
        this.updated_at =  new Date();
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
    
	public CategoriesEntity getCategoriesEntity() {
		return categoriesEntity;
	}

	public void setCategoriesEntity(CategoriesEntity categoriesEntity) {
		this.categoriesEntity = categoriesEntity;
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

	@Override
    public String toString() {
    	return "id: " + id + " name: " + name + " categoryEntity: " + categoriesEntity;
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


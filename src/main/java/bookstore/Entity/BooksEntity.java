package bookstore.Entity;


import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Books")
public class BooksEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "title", columnDefinition = "nvarchar")
    private String title;
	
	@Column(name = "author", columnDefinition = "nvarchar")
    private String author;
	
	@Column(name = "price", precision = 10, scale = 2, nullable = false)
    private Double price;
	
	@Column(name = "description", columnDefinition = "nvarchar")
    private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subcategory_id", nullable = false)
	private SubcategoriesEntity subcategoriesEntity;
	
	@ManyToOne
    @JoinColumn(name = "supplier_id")
    private SuppliersEntity supplier;
	
	@Column(name = "stock_quantity", nullable = false)
    private Integer stock_quantity;
	
	@Column(name = "thumbnail", columnDefinition = "nvarchar")
    private String thumbnail;

    @Column(name = "images", columnDefinition = "nvarchar")
    private String images;
    
    @Column(name = "publication_year")
    private Integer publication_year;

    @Column(name = "language", columnDefinition = "varchar")
    private String language;

    @Column(name = "page_count")
    private Integer page_count;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT GETDATE()", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    
    @Column(name = "status", columnDefinition = "bit")
    private int status;

    @OneToMany(mappedBy = "book_id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Book_DiscountsEntity> bookDiscounts;
    
    
    public BooksEntity() {
    	
    }
    
    
	public BooksEntity(Long id, String title, String author, Double price, String description,
			SubcategoriesEntity subcategoriesEntity, SuppliersEntity supplier, Integer stock_quantity, String thumbnail,
			String images, Integer publication_year, String language, Integer page_count, Date createdAt,
			Date updatedAt, int status, Collection<Book_DiscountsEntity> bookDiscounts) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.description = description;
		this.subcategoriesEntity = subcategoriesEntity;
		this.supplier = supplier;
		this.stock_quantity = stock_quantity;
		this.thumbnail = thumbnail;
		this.images = images;
		this.publication_year = publication_year;
		this.language = language;
		this.page_count = page_count;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
		this.bookDiscounts = bookDiscounts;
	}

	public SubcategoriesEntity getSubcategoriesEntity() {
		return subcategoriesEntity;
	}

	public void setSubcategoriesEntity(SubcategoriesEntity subcategoriesEntity) {
		this.subcategoriesEntity = subcategoriesEntity;
	}

	public SuppliersEntity getSupplier() {
		return supplier;
	}

	public void setSupplier(SuppliersEntity supplier) {
		this.supplier = supplier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getStock_quantity() {
		return stock_quantity;
	}

	public void setStock_quantity(Integer stock_quantity) {
		this.stock_quantity = stock_quantity;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getPublication_year() {
		return publication_year;
	}

	public void setPublication_year(Integer publication_year) {
		this.publication_year = publication_year;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getPage_count() {
		return page_count;
	}

	public void setPage_count(Integer page_count) {
		this.page_count = page_count;
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

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Collection<Book_DiscountsEntity> getBookDiscounts() {
		return bookDiscounts;
	}

	public void setBookDiscounts(Collection<Book_DiscountsEntity> bookDiscounts) {
		this.bookDiscounts = bookDiscounts;
	}

	@Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", subcategory=" + (subcategoriesEntity != null ? subcategoriesEntity.getName() : "null") +
                ", supplier=" + (supplier != null ? supplier.getName() : "null") +
                ", stockQuantity=" + stock_quantity +
                ", thumbnail='" + thumbnail + '\'' +
                ", images='" + images + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

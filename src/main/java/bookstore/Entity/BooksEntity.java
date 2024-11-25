package bookstore.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;
    
    @Column(name = "status", columnDefinition = "bit")
    private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
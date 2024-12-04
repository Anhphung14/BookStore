package bookstore.Entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "Cart_Items")
public class CartItemsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartsEntity cart;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "book_id", nullable = false)
    private BooksEntity book;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public CartItemsEntity() {
    	
    }

    public CartItemsEntity(Long id, CartsEntity cart, BooksEntity booksEntity, int quantity, Double price, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.cart = cart;
		this.book = booksEntity;
		this.quantity = quantity;
		this.price = price;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CartsEntity getCart() {
		return cart;
	}

	public void setCart(CartsEntity cart) {
		this.cart = cart;
	}

	public BooksEntity getBook() {
		return book;
	}

	public void setBook(BooksEntity booksEntity) {
		this.book = booksEntity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	@PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PrePersist
    protected void onUpdate() {
        updatedAt = new Date();
    }
    
 // Tính và cập nhật tổng giá cho CartItem
    public void setTotalPrice() {
        if (this.quantity > 0 && this.book != null && this.book.getPrice() != null) {
            // Tính tổng giá bằng cách nhân trực tiếp với kiểu double
            this.price = this.book.getPrice().doubleValue() * this.quantity;
        } else {
            this.price = 0.0; // Giá trị mặc định nếu không hợp lệ
        }
    }
}

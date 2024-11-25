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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "Carts")
public class CartsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemsEntity> cartItems;

    // Constructors
    
    public CartsEntity() {
    	
    }
    
    public CartsEntity(Long id, UsersEntity user, Date createdAt, Date updatedAt, int status,
    		List<CartItemsEntity> cartItems) {
    	super();
    	this.id = id;
    	this.user = user;
    	this.createdAt = createdAt;
    	this.updatedAt = updatedAt;
    	this.status = status;
    	this.cartItems = cartItems;
    }

    
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
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

	public List<CartItemsEntity> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemsEntity> cartItems) {
		this.cartItems = cartItems;
	}
	
	@PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }


	@PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

	/*
	 * @Override public String toString() { return "Cart{" + "id=" + id + ", user="
	 * + (user != null ? user.getUsername() : "null") + ", createdAt=" + createdAt +
	 * ", updatedAt=" + updatedAt + ", status=" + status + '}'; }
	 */
}

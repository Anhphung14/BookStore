package bookstore.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user; // Liên kết tới bảng Users

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private Double totalPrice;

    @Column(name = "shipping_address", nullable = false, length = 255)
    private String shippingAddress;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersDetailEntity> orderDetails; // Liên kết tới bảng OrderDetails

    @OneToMany(mappedBy = "order_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order_DiscountsEntity> orderDiscounts; // Liên kết tới bảng Order_Discounts

    
    public OrdersEntity() {
    	
    }
    public OrdersEntity(Long id, UsersEntity user, Double totalPrice, String shippingAddress, Integer status,
			Date createdAt, Date updatedAt, List<OrdersDetailEntity> orderDetails,
			List<Order_DiscountsEntity> orderDiscounts) {
		super();
		this.id = id;
		this.user = user;
		this.totalPrice = totalPrice;
		this.shippingAddress = shippingAddress;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.orderDetails = orderDetails;
		this.orderDiscounts = orderDiscounts;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<OrdersDetailEntity> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrdersDetailEntity> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public List<Order_DiscountsEntity> getOrderDiscounts() {
		return orderDiscounts;
	}
	public void setOrderDiscounts(List<Order_DiscountsEntity> orderDiscounts) {
		this.orderDiscounts = orderDiscounts;
	}

    
}

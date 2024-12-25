package bookstore.Entity;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user; // Liên kết tới bảng Users
    
    @Column(name = "customerName", nullable = true)
    private String customerName;
    
    @Column(name = "customerPhone", nullable = true)
    private String customerPhone;
    
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private Double totalPrice;

    @Column(name = "shipping_address", nullable = false, length = 255)
    private String shippingAddress;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;
    
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;
    
    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrdersDetailEntity> orderDetails; // Liên kết tới bảng OrderDetails

    @OneToMany(mappedBy = "order_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order_DiscountsEntity> orderDiscounts; // Liên kết tới bảng Order_Discounts

    @Transient
    private Double discountValue;
    
    public OrdersEntity() {
    	super();
    }
    
	public OrdersEntity(Long id, UsersEntity user, Double totalPrice, String shippingAddress, String paymentMethod,
			String paymentStatus, String orderStatus, Date createdAt, Date updatedAt,
			List<OrdersDetailEntity> orderDetails, List<Order_DiscountsEntity> orderDiscounts) {
		super();
		this.id = id;
		this.user = user;
		this.totalPrice = totalPrice;
		this.shippingAddress = shippingAddress;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.orderStatus = orderStatus;
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
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public Double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	@Autowired
	public String toString() {
		return "OrderId: " + id + " totalPrice: " + totalPrice + " paymentStatus: " + paymentStatus + " orderStatus: " + orderStatus;
	}
    
}

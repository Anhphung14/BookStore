package bookstore.Entity;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "discounts")
public class DiscountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "discount_type" , nullable = false)
    private String discountType;  // Ví dụ: 'percentage', 'amount', 'product-based', 'fixed'

    @Column(name = "discount_value" , nullable = false)
    private Double discountValue;

    @Column(name = "apply_to" , nullable = true)
    private String applyTo;  // Enum để xác định áp dụng cho user, sản phẩm, category, hoặc order

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "start_date" , nullable = false)
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "end_date" , nullable = false)
    private Date endDate;
    
    @Column(name = "min_order_value" , nullable = true)
    private Double minOrderValue;
    
    @Column(name = "max_uses" , nullable = true)
    private Integer maxUses;
    
    @Column (name = "status" , nullable = false)
    private String status;
   
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT GETDATE()", nullable = false)
    private Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    
    
    @OneToMany(mappedBy = "discount_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<Order_DiscountsEntity> orderDiscountsEntity;

    @OneToMany(mappedBy = "discount_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<Book_DiscountsEntity> bookDiscountsEntity;
    
    public DiscountsEntity() {
    	super();
    }
    
    public DiscountsEntity(Long id, String code, String discountType, Double discountValue, String applyTo,
    		Date startDate, Date endDate, Double minOrderValue, Integer maxUses, String status, Date createdAt,
			Date updatedAt, Collection<Order_DiscountsEntity> orderDiscountsEntity,
			Collection<Book_DiscountsEntity> bookDiscountsEntity) {
		super();
		this.id = id;
		this.code = code;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.applyTo = applyTo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.minOrderValue = minOrderValue;
		this.maxUses = maxUses;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.orderDiscountsEntity = orderDiscountsEntity;
		this.bookDiscountsEntity = bookDiscountsEntity;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}

	public String getApplyTo() {
		return applyTo;
	}

	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getMinOrderValue() {
		return minOrderValue;
	}

	public void setMinOrderValue(Double minOrderValue) {
		this.minOrderValue = minOrderValue;
	}

	public Integer getMaxUses() {
		return maxUses;
	}

	public void setMaxUses(Integer maxUses) {
		this.maxUses = maxUses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Collection<Order_DiscountsEntity> getOrderDiscountsEntity() {
		return orderDiscountsEntity;
	}

	public void setOrderDiscountsEntity(Collection<Order_DiscountsEntity> orderDiscountsEntity) {
		this.orderDiscountsEntity = orderDiscountsEntity;
	}

	public Collection<Book_DiscountsEntity> getBookDiscountsEntity() {
		return bookDiscountsEntity;
	}

	public void setBookDiscountsEntity(Collection<Book_DiscountsEntity> bookDiscountsEntity) {
		this.bookDiscountsEntity = bookDiscountsEntity;
	}


	@Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", discountType='" + discountType + '\'' +
                ", discountValue=" + discountValue +
                ", applyTo=" + applyTo +
                ", bookDiscountsEntity=" + bookDiscountsEntity +
                '}';
    }
}

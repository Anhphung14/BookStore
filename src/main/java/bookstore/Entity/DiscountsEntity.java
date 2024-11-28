package bookstore.Entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Discounts")
public class DiscountsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "discount_type")
    private int discountType;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date endDate;

    @Column(name = "min_order_value")
    private BigDecimal minOrderValue;

    @Column(name = "max_uses")
    private Integer maxUses;
    
    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP) 
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP) 
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date updatedAt;
    
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
	public int getDiscountType() {
		return discountType;
	}
	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}
	public BigDecimal getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
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
	public BigDecimal getMinOrderValue() {
		return minOrderValue;
	}
	public void setMinOrderValue(BigDecimal minOrderValue) {
		this.minOrderValue = minOrderValue;
	}
	public Integer getMaxUses() {
		return maxUses;
	}
	public void setMaxUses(Integer maxUses) {
		this.maxUses = maxUses;
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
	public DiscountsEntity(Long id, String code, int discountType, BigDecimal discountValue, Date startDate,
			Date endDate, BigDecimal minOrderValue, Integer maxUses, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.code = code;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.startDate = startDate;
		this.endDate = endDate;
		this.minOrderValue = minOrderValue;
		this.maxUses = maxUses;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
    
    public DiscountsEntity() {
    	super();
    }
}

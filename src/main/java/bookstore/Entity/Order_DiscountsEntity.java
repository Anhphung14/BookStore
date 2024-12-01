package bookstore.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Order_Discounts")
public class Order_DiscountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private DiscountsEntity discount_id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrdersEntity order_id;
    
    public Order_DiscountsEntity() {
    	
    }
    
	public Order_DiscountsEntity(Long id, DiscountsEntity discount_id, OrdersEntity order_id) {
		super();
		this.id = id;
		this.discount_id = discount_id;
		this.order_id = order_id;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DiscountsEntity getDiscount_id() {
		return discount_id;
	}

	public void setDiscount_id(DiscountsEntity discount_id) {
		this.discount_id = discount_id;
	}

	public OrdersEntity getOrder_id() {
		return order_id;
	}

	public void setOrder_id(OrdersEntity order_id) {
		this.order_id = order_id;
	}

	@Override
	public String toString() {
	    return "OrderDiscount{" +
	            "id=" + id +
	            ", discount=" + discount_id + // Chỉ lấy code của discount để hiển thị
	            ", order=" + order_id + // Chỉ lấy ID của order để hiển thị
	            '}';
	}

	
}

package bookstore.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Book_Discounts")
public class Book_DiscountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private BooksEntity book_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id", nullable = false)
    private DiscountsEntity discount_id;

    public Book_DiscountsEntity() {
    	
    }
    
    
	public Book_DiscountsEntity(Long id, BooksEntity book_id, DiscountsEntity discount_id) {
		super();
		this.id = id;
		this.book_id = book_id;
		this.discount_id = discount_id;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public BooksEntity getBook_id() {
		return book_id;
	}


	public void setBook_id(BooksEntity book_id) {
		this.book_id = book_id;
	}


	public DiscountsEntity getDiscount_id() {
		return discount_id;
	}


	public void setDiscount_id(DiscountsEntity discount_id) {
		this.discount_id = discount_id;
	}


	@Override
    public String toString() {
        return "Book_DiscountsEntity{" +
                "id=" + id +
                ", book=" + (book_id != null ? book_id.getTitle() : "null") +  // Hiển thị tên sách trong toString
                ", discount=" + (discount_id != null ? discount_id.getCode() : "null") +  // Hiển thị mã giảm giá trong toString
                '}';
    }
}

package bookstore.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Book_Discounts")
public class Book_DiscountsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BooksEntity book;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private DiscountsEntity discount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BooksEntity getBook() {
		return book;
	}

	public void setBook(BooksEntity book) {
		this.book = book;
	}

	public DiscountsEntity getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountsEntity discount) {
		this.discount = discount;
	}

	public Book_DiscountsEntity(Long id, BooksEntity book, DiscountsEntity discount) {
		super();
		this.id = id;
		this.book = book;
		this.discount = discount;
	}
    
    public Book_DiscountsEntity() {
    	super();
    }
}

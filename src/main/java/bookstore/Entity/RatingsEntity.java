package bookstore.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Ratings")
public class RatingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user; // Tham chiếu đến bảng Users

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BooksEntity book; // Tham chiếu đến bảng Books

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrdersEntity order; // Tham chiếu đến bảng Orders

    @Column(name = "number", nullable = false)
    private int number; // Số sao (1-5)

    @Column(name = "status", nullable = false)
    private int status; // Trạng thái đánh giá

    @Column(name = "content")
    private String content; // Nội dung đánh giá

    @Column(name = "response")
    private String response; // Phản hồi từ admin (có thể null)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt; // Ngày tạo

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt; // Ngày cập nhật

    // Constructors
    public RatingsEntity() {
    }

    public RatingsEntity(UsersEntity user, BooksEntity book, OrdersEntity order, int number, int status, String content, String response, Date createdAt, Date updatedAt) {
        this.user = user;
        this.book = book;
        this.order = order;
        this.number = number;
        this.status = status;
        this.content = content;
        this.response = response;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
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

    public BooksEntity getBook() {
        return book;
    }

    public void setBook(BooksEntity book) {
        this.book = book;
    }

    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
}

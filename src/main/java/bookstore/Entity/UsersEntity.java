package bookstore.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Users")
public class UsersEntity {
   
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "uuid", columnDefinition = "nvarchar(255)")
    private String uuid;

    @Column(name = "avatar", columnDefinition = "nvarchar")
    private String avatar;

    @Column(name = "email", columnDefinition = "nvarchar")
    private String email;

    @Column(name = "password", columnDefinition = "nvarchar")
    private String password;

    @Column(name = "fullname", columnDefinition = "nvarchar")
    private String fullname;

    @Column(name = "gender", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer gender;

    @Column(name = "phone", columnDefinition = "nvarchar")
    private String phone;

    @Column(name = "reset_token", columnDefinition = "nvarchar")
    private String reset_token;

    @Column(name = "verify_token", columnDefinition = "nvarchar")
    private String verify_token;
    
    @Column(name = "enabled", columnDefinition = "bit")
    private Integer enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;
    
    @OneToOne(mappedBy = "user")
    private CartsEntity cart;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<OrdersEntity> orders;
    
    @Transient
    private Set<Long> roleIds;

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
    

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name = "user_roles",
    		joinColumns = @JoinColumn(name = "user_id"),
    		inverseJoinColumns = @JoinColumn(name = "role_id")
    		)
    
    private Set<RolesEntity> roles = new HashSet<>();
    
    public Set<RolesEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesEntity> roles) {
        this.roles = roles;
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
    
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public String getVerify_token() {
        return verify_token;
    }

    public void setVerify_token(String verify_token) {
        this.verify_token = verify_token;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

	public CartsEntity getCart() {
		return cart;
	}

	public void setCart(CartsEntity cart) {
		this.cart = cart;
	}
	
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public List<OrdersEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrdersEntity> orders) {
		this.orders = orders;
	}
	
	
    
    
}

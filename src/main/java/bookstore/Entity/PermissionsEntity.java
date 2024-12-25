package bookstore.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jmx.export.annotation.ManagedAttribute;

@Entity
@Table(name = "Permissions")
public class PermissionsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "name", columnDefinition = "nvarchar")
    private String name;
	
	@Column(name = "description", columnDefinition = "nvarchar")
    private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT GETDATE()", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<RolesEntity> roles = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Integer getIs_active() {
//		return is_active;
//	}
//
//	public void setIs_active(Integer is_active) {
//		this.is_active = is_active;
//	}

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

	public Set<RolesEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolesEntity> roles) {
		this.roles = roles;
	}
}

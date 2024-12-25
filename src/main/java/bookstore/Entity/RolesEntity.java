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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Roles")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(50)", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<UsersEntity> users = new HashSet<>();

    // Getters and setters
    public Set<UsersEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersEntity> users) {
        this.users = users;
    }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Role_Permission",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    
    private Set<PermissionsEntity> permissions = new HashSet<>();

    @Transient
    private long userCount;

    // Getters and setters
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

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

	public Set<PermissionsEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<PermissionsEntity> permissions) {
		this.permissions = permissions;
	}
}

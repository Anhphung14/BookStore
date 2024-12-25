package bookstore.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(RolePermissionId.class)
@Table(name = "Role_Permission")
public class RolePermission {
	@Id
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RolesEntity role;
	
	@Id
    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false)
    private PermissionsEntity permission;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

	public RolesEntity getRole() {
		return role;
	}

	public void setRole(RolesEntity role) {
		this.role = role;
	}

	public PermissionsEntity getPermission() {
		return permission;
	}

	public void setPermission(PermissionsEntity permission) {
		this.permission = permission;
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

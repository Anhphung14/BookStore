package bookstore.Entity;

import java.io.Serializable;

public class RolePermissionId implements Serializable {
    private Long role; // Phải khớp với trường "role" trong RolePermission
    private Long permission; // Phải khớp với trường "permission" trong RolePermission

    // Getters và Setters
    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getPermission() {
        return permission;
    }

    public void setPermission(Long permission) {
        this.permission = permission;
    }

    // Override equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePermissionId that = (RolePermissionId) o;

        if (!role.equals(that.role)) return false;
        return permission.equals(that.permission);
    }

    @Override
    public int hashCode() {
        int result = role.hashCode();
        result = 31 * result + permission.hashCode();
        return result;
    }
}

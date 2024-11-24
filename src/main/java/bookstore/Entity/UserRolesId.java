package bookstore.Entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class UserRolesId implements Serializable {

    private Long user;
    private Long role;

    public UserRolesId() {}

    public UserRolesId(Long user, Long role) {
        this.user = user;
        this.role = role;
    }

    // Getter và Setter
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRolesId that = (UserRolesId) o;

        if (!user.equals(that.user)) return false;
        return role.equals(that.role);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}

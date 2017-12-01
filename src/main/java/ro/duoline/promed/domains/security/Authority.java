package ro.duoline.promed.domains.security;

import ro.duoline.promed.domains.User;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author I.T.W764
 */
@Entity
@Table(name = "Roles")
public class Authority
        //        extends AbstractDomainClass
        implements Serializable {

    private static final long serialVersionUID = 111L;

    @Id
    private String authority;

    @Version
    private Integer version;

    @JoinTable(name = "Users_x_Roles",
            joinColumns = @JoinColumn(name = "RoleId"),
            inverseJoinColumns = @JoinColumn(name = "UserId"))
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> users =
//            new ArrayList<>();
new HashSet<>();

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String role) {
        this.authority = role;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
        }
        if (!user.getAuthorities().contains(this)) {
            user.getAuthorities().add(this);
        }
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getAuthorities().remove(this);
    }

    @Override
    public String toString() {
        return "Authority{" + authority + '}';
    }

}

package de.aquariumshow.model;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    private ASUser user;

    public UserRole() {
    }

    public UserRole(String roleName, ASUser user) {
        this.roleName = roleName;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ASUser getUser() {
        return user;
    }

    public void setUser(ASUser user) {
        this.user = user;
    }
}
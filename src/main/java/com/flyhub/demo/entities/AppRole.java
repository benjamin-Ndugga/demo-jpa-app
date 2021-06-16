package com.flyhub.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Benjamin E Ndugga
 */
@Entity(name = "AppRole")
@Table(name = "app_roles")
@JsonIgnoreProperties(allowSetters = true, allowGetters = false, value = {"users"})
public class AppRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id")
    private Long roleId;
    private String roleName;
    private boolean status;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "role_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_id_fk")))
    private Set<AppUser> users;

    public AppRole() {
    }

    public AppRole(String roleName, boolean status) {
        this.roleName = roleName;
        this.status = status;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    @Override
    public String toString() {
        return "AppRole{" + "roleId=" + roleId + ", roleName=" + roleName + ", status=" + status + '}';
    }

}

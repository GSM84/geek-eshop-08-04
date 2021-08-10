package ru.geekbrains.persist;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Integer age;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<ru.geekbrains.persist.Role> role;

    public User() {
    }

    public User(Long id, String name, Integer age, String password, Set<ru.geekbrains.persist.Role> roles) {
        this.id = id;
        this.username = name;
        this.age = age;
        this.password = password;
        this.role = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ru.geekbrains.persist.Role> getRoles() {
        return role;
    }

    public void setRoles(Set<ru.geekbrains.persist.Role> roles) {
        this.role = roles;
    }
}

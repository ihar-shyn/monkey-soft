package com.shyn.monkeysoft.user;

import com.shyn.monkeysoft.department.Department;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table
@Getter
@Setter
@ToString
public class MonkeyUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "monkey_user_sequence",
            sequenceName = "monkey_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "monkey_user_sequence"
    )
    private Long id;
    @Column(unique = true, nullable = false)
    @Size(min = 1, max = 50)
    private String login;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public MonkeyUser() {}

    public MonkeyUser(String login, String password, String firstName, String lastName, Boolean isEnabled, Department department) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnabled = isEnabled;
        this.department = department;
    }

    public String getDepartmentName() {
        return department == null
                ? "Free Monkey"
                : department.getDepartmentId();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}

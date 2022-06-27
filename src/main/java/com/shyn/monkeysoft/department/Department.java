package com.shyn.monkeysoft.department;

import com.shyn.monkeysoft.user.MonkeyUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Department {

    @Id
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "department_sequence"
    )
    private Long id;
    @Column(unique = true, nullable = false)

    private String departmentId;
    private String description;

    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL
    )
    private List<MonkeyUser> monkeyUsers = new ArrayList<>();

    public Department() {
    }

    public Department(String departmentId, String description) {
        this.departmentId = departmentId;
        this.description = description;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long key) {
        this.id = key;
    }

    public List<MonkeyUser> getMonkeyUsers() {
        return monkeyUsers;
    }

    public void setMonkeyUsers(List<MonkeyUser> monkeyUsers) {
        this.monkeyUsers = monkeyUsers;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

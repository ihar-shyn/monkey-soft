package com.shyn.monkeysoft.department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shyn.monkeysoft.user.MonkeyUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString(exclude = "monkeyUsers")
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
    @JsonIgnore
    @Getter
    private List<MonkeyUser> monkeyUsers = new ArrayList<>();

    public Department() {
    }

    public Department(String departmentId, String description) {
        this.departmentId = departmentId;
        this.description = description;
    }
}

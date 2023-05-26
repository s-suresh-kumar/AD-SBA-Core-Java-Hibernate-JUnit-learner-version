package sba.sms.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="student")
public class Student {
    @Id
    @Column(name="email", nullable=false, length=50)
    private String email;

    @Column(name="name", nullable=false, length=50)
    private String name;

    @Column(name="password", nullable=false, length=50)
    private String password;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH},fetch=FetchType.EAGER)
    @JoinTable(name="student_courses",
            joinColumns = @JoinColumn(name="student_email"),
            inverseJoinColumns = @JoinColumn(name="course_id"))
    private List<Course> courses = new ArrayList<>();

}

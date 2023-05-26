package sba.sms.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private int id;

    @Column(name="name", nullable=false, length=50)
    private String name;

    @Column(name="instructor", nullable=false, length=50)
    private String instructor;

    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},fetch
            =FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

}

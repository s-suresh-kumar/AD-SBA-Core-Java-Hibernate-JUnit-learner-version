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

    public Student() {  
    }

    public Student(String email, String name, String password, List<Course> courses) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.courses = courses;
    }

    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getEmail().equals(student.getEmail()) && getName().equals(student.getName()) && getPassword().equals(student.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getName(), getPassword());
    }
}

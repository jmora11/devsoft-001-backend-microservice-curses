package com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity;

import com.P001SpringBoot.back.models.entity.Examen;
import com.P001SpringBoot.back.models.entity.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonIgnoreProperties(value = {"curso"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoStudent> cursoStudents;


    //@OneToMany(fetch = FetchType.LAZY)
    @Transient
    private List<Student> students;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Examen> examenes;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    public Curso() {
        this.students = new ArrayList<>();
        this.examenes = new ArrayList<>();
        this.cursoStudents = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudents(Student student) {
        this.students.add(student);
    }

    public void removeStudents(Student student) {
        this.students.remove(student);
    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public void addExamen(Examen examen) {
        this.examenes.add(examen);
    }

    public List<CursoStudent> getCursoStudents() {
        return cursoStudents;
    }

    public void setCursoStudents(List<CursoStudent> cursoStudents) {
        this.cursoStudents = cursoStudents;
    }

    public void removeExamen(Examen examen) {
        this.examenes.remove(examen);
    }

    public void addCursoStudents(CursoStudent cursoStudents) {
        this.cursoStudents.add(cursoStudents);
    }

    public void removeCursoStudents(CursoStudent cursoStudents) {
        this.cursoStudents.remove(cursoStudents);
    }

}

package com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "cursos_alumnos")
public class CursoStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alumno_id", unique = true)
    private Long alumnoId;

    @JsonIgnoreProperties(value = {"cursoStudents"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof CursoStudent)) {
            return false;
        }

        CursoStudent student = (CursoStudent) obj;

        return this.alumnoId != null && this.alumnoId.equals(student.getAlumnoId());
    }
}

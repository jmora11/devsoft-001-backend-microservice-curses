package com.P001SptringBoot.back.cursos.cursesmicroservices.controllers;

import com.P001SpringBoot.back.controllers.CommonController;
import com.P001SpringBoot.back.models.entity.Examen;
import com.P001SpringBoot.back.models.entity.Student;
import com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity.Curso;
import com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity.CursoStudent;
import com.P001SptringBoot.back.cursos.cursesmicroservices.services.CursoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

    @Value("{config.balanceador.test}")
    private String balanceadorTest;

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<?> eliminarCursoIdPorAlumno(@PathVariable Long id) {
        service.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<?> listAllStudents() {
        List<Curso> cursos = ((List<Curso>) service.findAll())
                .stream()
                .map(curso -> {
                    curso.getCursoStudents().forEach(cursoStudent -> {
                        Student student = new Student();
                        student.setId(cursoStudent.getAlumnoId());
                        curso.addStudents(student);
                    });
                    return curso;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cursos);
    }

    @Override
    @GetMapping({"/find/{id}"})
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<Curso> optionalStudent = this.service.findById(id);
        Curso curso = optionalStudent.get();
        if(curso.getCursoStudents().isEmpty() == false) {

            List<Long> ids = curso.getCursoStudents()
                    .stream()
                    .map(cursoStudent -> cursoStudent.getAlumnoId())
                    .collect(Collectors.toList());

            List<Student> students = (List<Student>) service.getStudentsByCurse(ids);

            curso.setStudents(students);
        }
        return optionalStudent.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(curso);
    }

    @Override
    @GetMapping({"/page"})
    public ResponseEntity<?> listPage(Pageable pageable) {
        Page<Curso> cursos = service.findAll(pageable)
                .map(curso -> {
                    curso.getCursoStudents().forEach(cursoStudent -> {
                        Student student = new Student();
                        student.setId(cursoStudent.getAlumnoId());
                        curso.addStudents(student);
                    });
                    return curso;
                });

        return ResponseEntity.ok().body(cursos);
    }

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balancerTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("Balanceadorr", "balanceadorTest");
        response.put("Cursos", service.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validar(result);
        }


        Optional<Curso> optionalCurso = this.service.findById(id);

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoToUpdate = optionalCurso.get();
        cursoToUpdate.setName(curso.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoToUpdate));
    }

    @PutMapping("/asign-student/{id}")
    public ResponseEntity<?> asignarStudent(@RequestBody List<Student> studentList, @PathVariable Long id) {
        Optional<Curso> optionalCurso = this.service.findById(id);

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = optionalCurso.get();
        studentList.forEach(student -> {

            CursoStudent cursoStudent = new CursoStudent();
            cursoStudent.setAlumnoId(student.getId());
            cursoStudent.setCurso(curso);
            curso.addCursoStudents(cursoStudent);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));

    }

    @PutMapping("/delete-student/{id}")
    public ResponseEntity<?> deleteStudent(@RequestBody Student student, @PathVariable Long id) {
        Optional<Curso> optionalCurso = this.service.findById(id);

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = optionalCurso.get();
        CursoStudent cursoStudent = new CursoStudent();
        cursoStudent.setAlumnoId(student.getId());
        curso.removeCursoStudents(cursoStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));

    }

    @GetMapping("/student-course/{id}")
    public ResponseEntity<?> findCourseById(@PathVariable Long id) {
        Curso curso = service.findCursoByStudentId(id);

        if(curso != null) {
            List<Long> examenesId = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
            if(examenesId != null && examenesId.size() > 0) {
                List<Examen> examenes = curso
                        .getExamenes()
                        .stream()
                        .map(examen -> {
                            if (examenesId.contains(examen.getId())) {
                                examen.setRespondido(true);
                            }
                            return examen;
                        })
                        .collect(Collectors.toList());

                curso.setExamenes(examenes);
            }
        }

        return ResponseEntity.ok(curso);
    }

    @PutMapping("/asign-exam/{id}")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenList, @PathVariable Long id) {
        Optional<Curso> optionalCurso = this.service.findById(id);

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = optionalCurso.get();
        examenList.forEach(examen -> {
            curso.addExamen(examen);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));

    }

    @PutMapping("/delete-exam/{id}")
    public ResponseEntity<?> deleteExam(@RequestBody Examen examen, @PathVariable Long id) {
        Optional<Curso> optionalCurso = this.service.findById(id);

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = optionalCurso.get();

        curso.removeExamen(examen);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));

    }
}

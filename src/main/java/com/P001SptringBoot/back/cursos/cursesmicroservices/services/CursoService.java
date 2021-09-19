package com.P001SptringBoot.back.cursos.cursesmicroservices.services;

import com.P001SpringBoot.back.models.entity.Student;
import com.P001SpringBoot.back.service.ICommonService;
import com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity.Curso;

import java.util.List;

public interface CursoService extends ICommonService<Curso> {
    public Curso findCursoByStudentId(Long id);

    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long studentId);

    public Iterable<Student> getStudentsByCurse(List<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);
}

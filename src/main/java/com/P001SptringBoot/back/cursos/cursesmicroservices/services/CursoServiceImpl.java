package com.P001SptringBoot.back.cursos.cursesmicroservices.services;

import com.P001SpringBoot.back.models.entity.Student;
import com.P001SpringBoot.back.service.CommonService;
import com.P001SptringBoot.back.cursos.cursesmicroservices.clients.RespuestaFeingClient;
import com.P001SptringBoot.back.cursos.cursesmicroservices.clients.StudentFeingClient;
import com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity.Curso;
import com.P001SptringBoot.back.cursos.cursesmicroservices.models.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoServiceImpl extends CommonService<Curso, CursoRepository> implements CursoService {

    @Autowired
    private StudentFeingClient student;

    @Autowired
    private RespuestaFeingClient client;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByStudentId(Long id) {
        return repository.findCursoByStudentId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long studentId) {
        return client.obtenerExamenesIdsConRespuestasAlumno(studentId);
    }

    @Override
    public Iterable<Student> getStudentsByCurse(List<Long> ids) {
        return student.getStudentsByCurse(ids);
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        repository.eliminarCursoAlumnoPorId(id);
    }
}

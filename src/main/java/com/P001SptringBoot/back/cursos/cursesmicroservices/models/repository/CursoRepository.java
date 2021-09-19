package com.P001SptringBoot.back.cursos.cursesmicroservices.models.repository;

import com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {

    @Query("select c from Curso c join fetch c.cursoStudents s where s.alumnoId=?1")
    public Curso findCursoByStudentId(Long id);

    @Modifying
    @Query("delete from CursoStudent ca where ca.alumnoId=?1")
    public void eliminarCursoAlumnoPorId(Long id);
}

package com.P001SptringBoot.back.cursos.cursesmicroservices.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-respuestas")
public interface RespuestaFeingClient {

    @GetMapping("/student/{studentId}/resolved-exams")
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long studentId);

}

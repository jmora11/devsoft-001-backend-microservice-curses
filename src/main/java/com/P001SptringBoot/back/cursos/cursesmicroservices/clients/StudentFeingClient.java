package com.P001SptringBoot.back.cursos.cursesmicroservices.clients;

import com.P001SpringBoot.back.models.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-usuarios")
public interface StudentFeingClient {

    @GetMapping("/students-by-curse")
    public Iterable<Student> getStudentsByCurse(@RequestParam List<Long> ids);

}

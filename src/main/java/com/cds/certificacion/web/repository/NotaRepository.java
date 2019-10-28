package com.cds.certificacion.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cds.certificacion.domain.Tarea;

public interface NotaRepository extends JpaRepository<Tarea, String>{

}

package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.fechaNacimiento <= :year AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :year)")
    List<Autor> buscarAutoresVivos(@Param("year") Integer year);

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> buscarAutores();

    Optional<Autor> findByNombreIgnoreCase(String nombre);
}

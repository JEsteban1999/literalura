package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Idioma;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autores")
    List<Libro> findAll();

    @Query("SELECT DISTINCT l.idiomas FROM Libro l")
    List<String> findDistinctIdiomas();

    @Query("SELECT l FROM Libro l WHERE l.idiomas = :idioma")
    List<Libro> findByIdioma(@Param("idioma") Idioma idioma);
}

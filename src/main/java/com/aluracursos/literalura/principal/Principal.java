package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.services.ConsumoAPI;
import com.aluracursos.literalura.services.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<Libro> libros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    public void muestraElMenu() {
        var opcion = 1;
        System.out.println("\n-------------------- BIENVENIDO --------------------\n");
        while (opcion != 0) {
            var menu = """
                        1. Buscar libro por titulo
                        2. Listar libros registrados
                        3. Listar autores registrados
                        4. Listar autores vivos en un determinado año
                        5. Listar libros por idioma
                        0. Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    guardarLibro();
                    break;
                case 2:
                    obtenerTodosLosLibros();
                    break;
                case 3:
                    mostrarTodosLosAutores();
                    break;
                case 4:
                    mostrarAutoresVivos();
                    break;
                case 5:
                    obtenerLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }
    }

    private DatosLibros getDatos() {
        System.out.println("Introduce el nombre del libro: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datos.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado");
        } else {
            System.out.println("Libro no encontrado");
        }
        return libroBuscado.orElse(null);
    }

    private Libro guardarLibro() {
        DatosLibros datos = getDatos();
        if (datos == null) {
            System.out.println("No se puede guardar el libro porque no se encontraron datos.");
            return null;
        }
        Optional<Libro> libroExistente = libroRepository.findByTituloContainsIgnoreCase(datos.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro '" + datos.titulo() + "' ya existe en la base de datos." +
                    " Debe buscarlo con la opción 2 del menú.");
            return libroExistente.get();
        } else {
            List<Autor> autores = datos.autores().stream()
                    .map(datosAutor -> {
                        return autorRepository.findByNombreIgnoreCase(datosAutor.nombre())
                                .orElseGet(() -> {
                                    Autor nuevoAutor = new Autor(datosAutor);
                                    return autorRepository.save(nuevoAutor);
                                });
                    }).collect(Collectors.toList());
            Libro libro = new Libro(datos);
            libro.setAutores(autores);
            libroRepository.save(libro);
            mostrarLibro(libro);
            System.out.println("Libro agregado correctamente");
            return libro;
        }
    }

    private void mostrarLibro(Libro libro) {
        System.out.println("\n-------------------- LIBRO --------------------\n");
        System.out.println("Titulo: " + libro.getTitulo());
        if (!libro.getAutores().isEmpty()) {
            System.out.println("Autor: " + libro.getAutores().get(0).getNombre());
        } else {
            System.out.println("Autor: No disponible");
        }
        System.out.println("Idioma: " + libro.getIdiomas());
        System.out.println("Numero de descargas: " + libro.getNumeroDescargas() + "\n");
    }

    private void obtenerTodosLosLibros() {
        libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdiomas))
                .forEach(this::mostrarLibro);
    }

    private void obtenerLibrosPorIdioma() {
        List<String> idiomas = libroRepository.findDistinctIdiomas();
        System.out.println("Ingresa el idioma que deseas buscar: ");
        idiomas.forEach(System.out::println);
        String idiomaIngresado = teclado.nextLine();
        try {
            Idioma idiomaSeleccionado = Idioma.fromString(idiomaIngresado);
            List<Libro> librosFiltradosPorIdioma = libroRepository.findByIdioma(idiomaSeleccionado);
            if (librosFiltradosPorIdioma.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma seleccionado");
            } else {
                librosFiltradosPorIdioma.forEach(this::mostrarLibro);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("El idioma ingresado no es valido: " + e.getMessage());
        }
    }

    private void mostrarAutor(Autor autor) {
        System.out.println("\n-------------------- AUTOR --------------------\n");
        System.out.println("Nombre: " + autor.getNombre());
        System.out.println("Año de nacimiento: " + autor.getFechaNacimiento());
        System.out.println("Año de fallecimiento: " + autor.getFechaFallecimiento());
        System.out.println("Libros: ");
        if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
            autor.getLibros().forEach(l -> System.out.println("- " + l.getTitulo()));
        } else {
            System.out.println("No hay libros disponibles");
        }
        System.out.println("\n");
    }

    private void mostrarTodosLosAutores() {
        autores = autorRepository.buscarAutores();
        autores.forEach(a -> {
            mostrarAutor(a);
        });
    }

    private void mostrarAutoresVivos() {
        System.out.println("Escribe el año que quieres consultar: ");
        Integer fecha = teclado.nextInt();
        if (fecha == null || fecha <= 0) {
            System.out.println("Por favor, introduce un año valido");
            return;
        }
        autores = autorRepository.buscarAutoresVivos(fecha);
        autores.forEach(this::mostrarAutor);
    }
}

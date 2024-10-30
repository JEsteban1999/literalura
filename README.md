# Biblioteca Virtual

## Descripción
Biblioteca Virtual es una aplicación en Java que permite gestionar y consultar libros y autores a través de una base de datos. La aplicación utiliza una API externa para buscar libros y autores, almacenándolos en una base de datos local para futuras consultas. Proporciona un menú interactivo para realizar diversas operaciones, como buscar libros por título, listar autores y libros, y filtrar autores vivos en un año específico.

## Características
1. **Buscar libro por título**: Permite al usuario buscar un libro por su título a través de una API externa. Si el libro es encontrado, se guarda en la base de datos.
2. **Listar libros registrados**: Muestra todos los libros almacenados en la base de datos, ordenados por idioma.
3. **Listar autores registrados**: Muestra todos los autores registrados en la base de datos y sus detalles.
4. **Listar autores vivos en un año específico**: Filtra y muestra los autores que estaban vivos en el año especificado.
5. **Listar libros por idioma**: Permite al usuario seleccionar un idioma y muestra todos los libros registrados en ese idioma.

## Requisitos
- Java 17 o superior
- Dependencias de Spring Framework (para la inyección de dependencias)
- Conexión a una base de datos compatible (configurar `LibroRepository` y `AutorRepository`).
- Dependencia para consumir la API de datos (ConsumoAPI).

## Instalación y Ejecución
1. Clona este repositorio en tu máquina local.
2. Asegúrate de configurar correctamente la conexión a la base de datos en las clases `LibroRepository` y `AutorRepository`.
3. Ejecuta la aplicación principal con `Principal.muestraElMenu()`.
4. Sigue las instrucciones del menú interactivo en la consola.

## Uso
Al iniciar la aplicación, verás el siguiente menú interactivo:

-------------------- BIENVENIDO --------------------

1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un determinado año
5. Listar libros por idioma
0. Salir

Selecciona la opción que deseas utilizar e ingresa los datos requeridos. Cada opción realizará una consulta o acción sobre la base de datos.

## Estructura de Clases
- **Principal**: Clase principal que gestiona el flujo de la aplicación y el menú de opciones.
- **LibroRepository**: Interfaz de acceso a datos para gestionar operaciones CRUD en la base de datos de libros.
- **AutorRepository**: Interfaz de acceso a datos para gestionar operaciones CRUD en la base de datos de autores.
- **ConsumoAPI**: Clase que consume la API externa de libros para obtener los detalles de un libro específico.
- **ConvierteDatos**: Clase encargada de transformar los datos JSON recibidos de la API a objetos de tipo `DatosLibros`.

## Ejemplos de Uso
- Para **buscar un libro por título**, selecciona la opción `1`, ingresa el nombre del libro y espera a que se complete la búsqueda.
- Para **listar libros por idioma**, selecciona la opción `5`, elige un idioma de la lista y visualiza los libros disponibles en dicho idioma.

## Notas
- La aplicación utiliza la URL base `https://gutendex.com/books/` para consumir datos de libros de la API de Gutendex.
- Asegúrate de que los datos de la base de datos se configuren correctamente antes de ejecutar la aplicación.

## Autor
Desarrollado por Juan Esteban Narvaez.

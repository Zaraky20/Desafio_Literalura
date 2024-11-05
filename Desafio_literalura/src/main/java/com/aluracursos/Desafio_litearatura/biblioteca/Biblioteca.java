package com.aluracursos.Desafio_litearatura.biblioteca;

import com.aluracursos.Desafio_litearatura.config.ConsumoAPI;
import com.aluracursos.Desafio_litearatura.config.ConvertirDatos;
import com.aluracursos.Desafio_litearatura.modelos.Autor;
import com.aluracursos.Desafio_litearatura.modelos.Libro;
import com.aluracursos.Desafio_litearatura.modelos.LibroResponse;
import com.aluracursos.Desafio_litearatura.modelos.records.DatosLibro;
import com.aluracursos.Desafio_litearatura.repository.IAutorRepository;
import com.aluracursos.Desafio_litearatura.repository.ILibroRepository;
import org.slf4j.Marker;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;

public class Biblioteca {
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvertirDatos convierte = new ConvertirDatos();
    private static final String API_BASE = "https://gutendex.com/books/?search=";
    private final List<Libro> datosDelLibro = new ArrayList<>();
    private final ILibroRepository libroRepositorio;
    private final IAutorRepository autorRepositorio;


    public Biblioteca(ILibroRepository libroRepositorio, IAutorRepository autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void muestraMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                ++++BIENVENIDO A LA BIBLIOTECA++++
                1 - Agregar libro por nombre
                2 - Mostrar libros buscados
                3 - Buscar libro por nombre
                4 - Buscar autores por año
                5 - Buscar autor por nombre
                0 - Salir
                
                ++++Ingrese una opción++++
                """);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroEnLaWeb();
                        break;
                    case 2:
                        MostrarLibrosBuscados();
                        break;
                    case 3:
                        buscarLibroPorNombre();
                        break;
                    case 4:
                        buscarAutoresPorAno();
                        break;
                    case 5:
                        buscarAutorPorNombre();
                        break;
                    case 6:
                        //MostrarTop10LibrosMasDescargados();
                        break;
                    case 0:
                        System.out.println("Cerrando Aplicación...!");
                        break;
                    default:
                        System.out.println("Opción incorrecta. Ingresa una nueva opción.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor ingrese una opción válida.");
                teclado.nextLine();
            }
        }
    }

    // Método para buscar un libro en la API y guardarlo en la base de datos
    private Libro getDatosLibro() {
        System.out.print("Ingrese el nombre del libro: ");
        var nombreLibro = teclado.nextLine().toLowerCase();
        var json = consumoAPI.obtenerDatos(API_BASE + nombreLibro.replace(" ", "+"));

        if (json == null || json.trim().isEmpty()) {
            System.out.println("No se recibieron datos válidos de la API.");
            return null;
        }

        LibroResponse datos = convierte.convierteDatosJsonAJava(json, LibroResponse.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosLibro primerLibro = datos.getResultadoLibros().get(0);
            return new Libro(primerLibro);
        } else {
            System.out.println("No se encontraron resultados.");
            return null;
        }
    }


    private void buscarLibroEnLaWeb() {
        Libro libro = getDatosLibro();
        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }


        try {
            boolean libroExists = libroRepositorio.existsByTitulo(libro.getTitulo());
            if (libroExists) {
                System.out.println("Este libro ya existe en la base de datos");
            } else {
                libroRepositorio.save(libro);
                System.out.println(libro.toString());
            }
        } catch (InvalidDataAccessApiUsageException e) {
            System.out.println("No se puede persisitir el libro buscado!");
        }
    }

    // Método para mostrar los libros que han sido buscados y guardados
    @Transactional(readOnly = true)
    private void MostrarLibrosBuscados() {
        List<Libro> libros = libroRepositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se enconraron libros en la base de datos");
        } else {
            System.out.println("Libros encontrados en la base de datos:");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }


    // Método para buscar un libro por nombre en la base de datos
    private void buscarLibroPorNombre() {
        System.out.print("Ingrese el nombre del libro a buscar: ");
        var tituloLibro = teclado.nextLine();
        Libro libroBuscado = libroRepositorio.findByTituloContainsIgnoreCase(tituloLibro);
        if (libroBuscado != null) {
            System.out.println("El libro buscado fue: " + libroBuscado);
        } else {
            System.out.println("El libro con el titulo '" + tituloLibro + "' no se encontró");
        }
    }


    // Método para buscar autores por año
    private void buscarAutoresPorAno() {
        System.out.println("Ingresa el año para verificar si los autores que estan vivos:\n");

        try {
            var anoBuscado = teclado.nextInt();
            teclado.nextLine();

            // Validación básica para años razonables
            if (anoBuscado <= 0) {
                System.out.println("Por favor, ingresa un año válido mayor a 0.");
                return;
            }

            // Buscar autores con las fechas correspondientes
            List<Autor> autoresVivos = autorRepositorio.findByCumpleanosLessThanOrFechaFallecimientoGreaterThanEqual(anoBuscado, anoBuscado);
            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores que estuvieran vivos en el año " + anoBuscado + ".");
            } else {
                System.out.println("Los autores que estaban vivos en el año " + anoBuscado + " son:");
                Set<String> autoresUnicos = new HashSet<>();

                for (Autor autor : autoresVivos) {
                    Integer cumpleanios = autor.getCumpleanos();
                    Integer fechaFallecimiento = autor.getFechaFallecimiento();

                    // Comprobar si el autor estaba vivo en el año especificado
                    if (cumpleanios != null && fechaFallecimiento != null) {
                        if (cumpleanios <= anoBuscado && fechaFallecimiento >= anoBuscado) {
                            // Verificar si el autor es único antes de mostrar
                            if (autoresUnicos.add(autor.getNombre())) {
                                System.out.println("Autor: " + autor.getNombre());
                            }
                        }
                    } else {
                        System.out.println("Datos incompletos para el autor: " + autor.getNombre());
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingresa un número entero");
            teclado.nextLine();
        }
    }


    // Método para buscar un autor por nombre
    private void buscarAutorPorNombre() {
        System.out.print("Ingrese el nombre del autor a buscar: ");
        var nombreAutor = teclado.nextLine();
        Optional<Autor> escritorBuscado = autorRepositorio.findFirstByNombreContainsIgnoreCase(nombreAutor);
        if (escritorBuscado != null) {
            System.out.println("\nEl escritor buscado fue: " + escritorBuscado.get().getNombre());

        } else {
            System.out.println("\nEl escritor con el titulo '" + nombreAutor + "' no fue encontrado");
        }
    }

    //Método para mostrar el top 10 de libros más descargados
   /* private void MostrarTop10LibrosMasDescargados() {
        List<Libro> top10Libros = libroRepositorio.findTop10ByTituloByCantidadDeDescargas();
        if (!top10Libros.isEmpty()) {
            int index = 1;
            for (Libro libro : top10Libros) {
                System.out.printf("Libro %d: %s Autor: %s Descargas: %d\n",
                        index, libro.getTitulo(), libro.getAutores().getNombre(), libro.getCantidadDeDescargas());
                index++;
            }

        }

    }*/
}






/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.gestores;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inventario.excepciones.ProductoNoEncontradoException;
import com.inventario.interfaces.FiltroProducto;
import com.inventario.productos.*;
import com.inventario.utils.LocalDateAdapter;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author joaxx
 *
 * Clase que gestiona el inventario de productos.
 *
 * @param <T> Tipo de producto que extiende de Producto.
 */
public class GestorInventario {
    // Lista que almacena los productos en el inventario
    private List<Producto> inventario;
    // Mapa para acceder rápidamente a los productos por código
    private Map<Integer, Producto> mapaProductos;

    /**
     * Constructor que inicializa la lista de inventario y el mapa de productos.
     */
    public GestorInventario() {
        this.inventario = new ArrayList<>();
        this.mapaProductos = new HashMap<>();
    }

    /**
     * Agrega un producto al inventario.
     *
     * @param producto Producto a agregar.
     */
    public void agregarProducto(Producto producto) {
        inventario.add(producto);
        mapaProductos.put(producto.getCodigo(), producto);
        System.out.println("Producto agregado exitosamente.");
    }

    /**
     * Muestra todos los productos del inventario.
     */
    public void mostrarInventario() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (Producto producto : inventario) {
                producto.mostrarDetalles();
                System.out.println("----------------------------");
            }
        }
    }

    /**
     * Muestra todos los productos usando un Iterator.
     */
    public void mostrarInventarioConIterator() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            Iterator<Producto> iterator = inventario.iterator();
            while (iterator.hasNext()) {
                Producto producto = iterator.next();
                producto.mostrarDetalles();
                System.out.println("----------------------------");
            }
        }
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto a buscar.
     * @return El producto encontrado.
     * @throws ProductoNoEncontradoException Si no se encuentra el producto.
     */
    public Producto buscarProductoPorCodigo(int codigo) throws ProductoNoEncontradoException {
        Producto producto = mapaProductos.get(codigo);
        if (producto != null) {
            return producto;
        } else {
            throw new ProductoNoEncontradoException("Producto no encontrado con código: " + codigo);
        }
    }

    /**
     * Modifica los datos de un producto existente.
     *
     * @param codigo  Código del producto a modificar.
     * @param scanner Objeto Scanner para leer datos de la consola.
     */
    public void modificarProducto(int codigo, Scanner scanner) {
        try {
            Producto producto = buscarProductoPorCodigo(codigo);
            System.out.println("Producto encontrado. Ingrese los nuevos datos:");

            System.out.print("Nombre: ");
            scanner.nextLine(); // Consumir el salto de línea
            String nuevoNombre = scanner.nextLine();
            producto.setNombre(nuevoNombre);

            System.out.print("Precio: ");
            double nuevoPrecio = scanner.nextDouble();
            producto.setPrecio(nuevoPrecio);

            System.out.print("Stock: ");
            int nuevoStock = scanner.nextInt();
            producto.setStock(nuevoStock);

            if (producto instanceof Electrodomestico) {
                Electrodomestico electrodomestico = (Electrodomestico) producto;

                System.out.print("Garantía en meses: ");
                int nuevaGarantia = scanner.nextInt();
                electrodomestico.setGarantiaMeses(nuevaGarantia);

                System.out.print("Categoría Energética (A, B, C, D, E, F, G): ");
                scanner.nextLine(); // Consumir el salto de línea
                String categoria = scanner.nextLine();
                electrodomestico.setCategoriaEnergetica(CategoriaEnergetica.valueOf(categoria.toUpperCase()));

            } else if (producto instanceof Alimento) {
                Alimento alimento = (Alimento) producto;

                System.out.print("Fecha de Vencimiento (YYYY-MM-DD): ");
                LocalDate fechaVencimiento = null;
                boolean fechaValida = false;
                scanner.nextLine(); // Consumir el salto de línea
                while (!fechaValida) {
                    try {
                        String fechaStr = scanner.nextLine();
                        fechaVencimiento = LocalDate.parse(fechaStr);
                        fechaValida = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida. Por favor, ingrese la fecha en formato YYYY-MM-DD:");
                    }
                }
                alimento.setFechaVencimiento(fechaVencimiento);

                System.out.print("Refrigeración Requerida (true/false): ");
                boolean refrigeracion = scanner.nextBoolean();
                alimento.setRefrigeracionRequerida(refrigeracion);
            }

            System.out.println("Producto modificado exitosamente.");

        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error en los datos ingresados: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            System.out.println("Operación de modificación finalizada.");
        }
    }

    /**
     * Elimina un producto del inventario.
     *
     * @param codigo Código del producto a eliminar.
     */
    public void eliminarProducto(int codigo) {
        try {
            // Buscar el producto por código
            Producto producto = buscarProductoPorCodigo(codigo);
            if (producto != null) {
                // Eliminar de la lista y del mapa
                inventario.remove(producto);
                mapaProductos.remove(codigo);
                System.out.println("Producto eliminado exitosamente: " + producto);
            } else {
                System.out.println("Producto no encontrado con código: " + codigo);
            }
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al eliminar el producto: " + e.getMessage());
        }
    }


    /**
     * Guarda el inventario en un archivo utilizando serialización.
     *
     * @param rutaArchivo Ruta del archivo donde se guardará el inventario.
     */
    public void guardarInventario(String rutaArchivo) {
         if (!rutaArchivo.endsWith(".dat")) {
             rutaArchivo += ".dat"; // Asegurarse de que sea .dat
         }

         try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
             oos.writeObject(inventario);
             System.out.println("Inventario guardado exitosamente en " + rutaArchivo);
         } catch (IOException e) {
             System.out.println("Error al guardar el inventario: " + e.getMessage());
         }
    }


    /**
     * Carga el inventario desde un archivo.
     *
     * @param rutaArchivo Ruta del archivo desde donde se cargará el inventario.
     */
    @SuppressWarnings("unchecked")
    public void cargarInventario(String rutaArchivo) {
        if (!rutaArchivo.endsWith(".dat")) {
            System.out.println("Error: el archivo debe tener la extensión .dat");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            inventario = (List<Producto>) ois.readObject();
            mapaProductos.clear();
            for (Producto producto : inventario) {
                mapaProductos.put(producto.getCodigo(), producto);
            }
            System.out.println("Inventario cargado exitosamente desde " + rutaArchivo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el inventario: " + e.getMessage());
        }
    }


    /**
     * Guarda el inventario en un archivo JSON utilizando Gson.
     *
     * @param rutaArchivo Ruta del archivo donde se guardará el inventario en JSON.
     */
    public void guardarInventarioJSON(String rutaArchivo) {
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();
        
        JsonArray jsonArray = new JsonArray();

        for (Producto producto : inventario) {
            JsonObject jsonObject = gson.toJsonTree(producto).getAsJsonObject();

            // Agregar el campo "tipo" según el tipo de producto
            if (producto instanceof Electrodomestico) {
                jsonObject.addProperty("tipo", "Electrodomestico");
            } else if (producto instanceof Alimento) {
                jsonObject.addProperty("tipo", "Alimento");
            }

            jsonArray.add(jsonObject);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            gson.toJson(jsonArray, writer);
            System.out.println("Inventario guardado en formato JSON en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario en JSON: " + e.getMessage());
        }
    }

    /**
     * Carga el inventario desde un archivo JSON utilizando Gson.
     *
     * @param rutaArchivo Ruta del archivo desde donde se cargará el inventario en JSON.
     */
public void cargarInventarioJSON(String rutaArchivo) {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
        JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
        inventario.clear();
        mapaProductos.clear();

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String tipo = jsonObject.has("tipo") ? jsonObject.get("tipo").getAsString() : null;

            if (tipo == null) {
                System.out.println("Advertencia: Producto sin tipo encontrado en el JSON, ignorando...");
                continue;
            }

            Producto producto = null;
            switch (tipo) {
                case "Electrodomestico":
                    producto = gson.fromJson(jsonObject, Electrodomestico.class);
                    break;
                case "Alimento":
                    producto = gson.fromJson(jsonObject, Alimento.class);
                    break;
                default:
                    System.out.println("Advertencia: Tipo desconocido \"" + tipo + "\", ignorando...");
                    continue;
            }

            if (producto != null) {
                inventario.add(producto);
                mapaProductos.put(producto.getCodigo(), producto);
            }
        }

        System.out.println("Inventario cargado desde JSON desde " + rutaArchivo);
    } catch (IOException e) {
        System.out.println("Error al cargar el inventario desde JSON: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Error inesperado al cargar el inventario: " + e.getMessage());
        e.printStackTrace();
    }
}


    /**
     * Exporta el inventario a un archivo de texto.
     *
     * @param rutaArchivo Ruta del archivo donde se exportará el inventario.
     */
    public void exportarInventarioATexto(String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto producto : inventario) {
                if (producto instanceof Electrodomestico) {
                    writer.write("Electrodomestico, " + producto.toString());
                } else if (producto instanceof Alimento) {
                    writer.write("Alimento, " + producto.toString());
                }
                writer.newLine();
            }
            System.out.println("Inventario exportado a " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al exportar el inventario: " + e.getMessage());
        }
    }


    /**
     * Importa datos desde un archivo de texto.
     *
     * @param rutaArchivo Ruta del archivo desde donde se importarán los datos.
     */
    public void importarInventarioDesdeTexto(String rutaArchivo) {
    try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println("Leído desde archivo: " + linea);
            Producto producto = parsearProductoDesdeTexto(linea);
            if (producto != null) {
                agregarProducto(producto);
                System.out.println("Producto agregado al inventario: " + producto);
            }
        }
        System.out.println("Importación completada.");
    } catch (IOException e) {
        System.out.println("Error al importar el inventario: " + e.getMessage());
    }
}

    
    private Producto parsearProductoDesdeTexto(String linea) {
    try {
        // Dividir el tipo y los detalles del producto
        String[] partesIniciales = linea.split(", Producto");
        if (partesIniciales.length < 2) {
            System.out.println("Línea con formato incorrecto: " + linea);
            return null;
        }

        String tipo = partesIniciales[0].trim();
        String detalles = "Producto" + partesIniciales[1].trim();

        // Procesar según el tipo de producto
        if (tipo.equals("Electrodomestico")) {
            String[] partes = detalles.replace("Producto{", "").replace("}", "").split(", ");
            int codigo = Integer.parseInt(obtenerValor(partes[0]));
            String nombre = obtenerValor(partes[1]);
            double precio = Double.parseDouble(obtenerValor(partes[2]));
            int stock = Integer.parseInt(obtenerValor(partes[3]));
            int garantiaMeses = Integer.parseInt(obtenerValor(partes[4]));
            CategoriaEnergetica categoriaEnergetica = CategoriaEnergetica.valueOf(obtenerValor(partes[5]));

            Electrodomestico electrodomestico = new Electrodomestico(nombre, precio, stock, garantiaMeses, categoriaEnergetica);
            electrodomestico.setTipo(tipo); // Establecer el tipo
            return electrodomestico;
        } else if (tipo.equals("Alimento")) {
            String[] partes = detalles.replace("Producto{", "").replace("}", "").split(", ");
            int codigo = Integer.parseInt(obtenerValor(partes[0]));
            String nombre = obtenerValor(partes[1]);
            double precio = Double.parseDouble(obtenerValor(partes[2]));
            int stock = Integer.parseInt(obtenerValor(partes[3]));
            LocalDate fechaVencimiento = LocalDate.parse(obtenerValor(partes[4]));
            boolean refrigeracionRequerida = Boolean.parseBoolean(obtenerValor(partes[5]));

            Alimento alimento = new Alimento(nombre, precio, stock, fechaVencimiento, refrigeracionRequerida);
            alimento.setTipo(tipo); // Establecer el tipo
            return alimento;
        } else {
            System.out.println("Tipo desconocido: " + tipo);
        }
    } catch (Exception e) {
        System.out.println("Error al parsear la línea: " + linea + ". Detalle del error: " + e.getMessage());
    }
    return null;
}


private String obtenerValor(String texto) {
    if (texto.contains("=")) {
        return texto.split("=")[1].replace("'", "").trim(); 
    }
    return texto.trim();
}

    // Métodos adicionales para interacción con la consola

    /**
     * Solicita al usuario los datos para agregar un nuevo producto.
     *
     * @param scanner Objeto Scanner para leer datos de la consola.
     */
    public void agregarProductoDesdeConsola(Scanner scanner) {
        int tipo = 0;
        do {
            System.out.println("Seleccione el tipo de producto:");
            System.out.println("1. Electrodoméstico");
            System.out.println("2. Alimento");
            System.out.print("Opción: ");
            try {
                tipo = scanner.nextInt();
                if (tipo != 1 && tipo != 2) {
                    System.out.println("Opción inválida. Por favor, ingrese 1 o 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número (1 o 2).");
                scanner.nextLine(); 
            }
        } while (tipo != 1 && tipo != 2);
        scanner.nextLine();

        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Precio: ");
            double precio = scanner.nextDouble();

            System.out.print("Stock: ");
            int stock = scanner.nextInt();

            if (tipo == 1) {
                System.out.print("Garantía en meses: ");
                int garantia = scanner.nextInt();

                System.out.print("Categoría Energética (A, B, C, D, E, F, G): ");
                scanner.nextLine(); 
                String categoria = scanner.nextLine();

                Electrodomestico electrodomestico = new Electrodomestico(
                        nombre, precio, stock, garantia,
                        CategoriaEnergetica.valueOf(categoria.toUpperCase())
                );
                agregarProducto(electrodomestico);

            } else if (tipo == 2) {
                System.out.print("Fecha de Vencimiento (YYYY-MM-DD): ");
                LocalDate fechaVencimiento = null;
                boolean fechaValida = false;
                scanner.nextLine(); 
                while (!fechaValida) {
                    try {
                        String fechaStr = scanner.nextLine();
                        fechaVencimiento = LocalDate.parse(fechaStr);
                        fechaValida = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida. Por favor, ingrese la fecha en formato YYYY-MM-DD:");
                    }
                }

                System.out.print("Refrigeración Requerida (true/false): ");
                boolean refrigeracion = scanner.nextBoolean();

                Alimento alimento = new Alimento(nombre, precio, stock, fechaVencimiento, refrigeracion);
                agregarProducto(alimento);

            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error en los datos ingresados: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Solicita al usuario el código del producto a buscar y muestra sus detalles.
     *
     * @param scanner Objeto Scanner para leer datos de la consola.
     */
    public void buscarProductoPorCodigoDesdeConsola(Scanner scanner) {
        System.out.print("Ingrese el código del producto a buscar: ");
        int codigo = scanner.nextInt();
        try {
            Producto producto = buscarProductoPorCodigo(codigo);
            producto.mostrarDetalles();
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Solicita al usuario el código del producto a modificar y actualiza sus datos.
     *
     * @param scanner Objeto Scanner para leer datos de la consola.
     */
    public void modificarProductoDesdeConsola(Scanner scanner) {
        System.out.print("Ingrese el código del producto a modificar: ");
        int codigo = scanner.nextInt();
        modificarProducto(codigo, scanner);
    }

    /**
     * Solicita al usuario el código del producto a eliminar.
     *
     * @param scanner Objeto Scanner para leer datos de la consola.
     */
    public void eliminarProductoDesdeConsola(Scanner scanner) {
        System.out.print("Ingrese el código del producto a eliminar: ");
        int codigo = scanner.nextInt();
        eliminarProducto(codigo);
    }

    /**
     * Filtra los productos del inventario según un criterio dado.
     *
     * @param filtro Criterio de filtrado.
     * @return Lista de productos que cumplen con el criterio.
     */
    public List<Producto> filtrarProductos(FiltroProducto filtro) {
        return inventario.stream()
                .filter(filtro::filtrar)
                .collect(Collectors.toList());
    }

    // Getter para el inventario
    public List<Producto> getInventario() {
        return inventario;
    }
    
    public List<Producto> filtrarStockBajo(int cantidadMinima) {
        return inventario.stream()
                .filter(producto -> producto.getStock() < cantidadMinima)
                .collect(Collectors.toList());
    }

}


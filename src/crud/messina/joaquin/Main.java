/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crud.messina.joaquin;
import com.inventario.gestores.GestorInventario;
import com.inventario.productos.Producto;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author joaxx
 *
 * Clase principal que inicia la aplicación de gestión de inventario.
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error al configurar la codificación UTF-8: " + e.getMessage());
        }

        GestorInventario gestor = new GestorInventario();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Gestión de Inventario ===");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Mostrar Todos los Productos");
            System.out.println("3. Buscar Producto por Código");
            System.out.println("4. Modificar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("6. Guardar Inventario en Archivo");
            System.out.println("7. Cargar Inventario desde Archivo");
            System.out.println("8. Mostrar Productos (usando Iterator)");
            System.out.println("9. Guardar Inventario en JSON");
            System.out.println("10. Cargar Inventario desde JSON");
            System.out.println("11. Exportar Inventario a Texto");
            System.out.println("12. Importar Inventario desde Texto");
            System.out.println("13. Filtrar Productos por Stock Bajo");
            System.out.println("14. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    gestor.agregarProductoDesdeConsola(scanner);
                    break;
                case 2:
                    gestor.mostrarInventario();
                    break;
                case 3:
                    gestor.buscarProductoPorCodigoDesdeConsola(scanner);
                    break;
                case 4:
                    gestor.modificarProductoDesdeConsola(scanner);
                    break;
                case 5:
                    gestor.eliminarProductoDesdeConsola(scanner);
                    break;
                case 6:
                    gestor.guardarInventario("inventario.dat");
                    break;
                case 7:
                    gestor.cargarInventario("inventario.dat");
                    break;
                case 8:
                    gestor.mostrarInventarioConIterator();
                    break;
                case 9:
                    gestor.guardarInventarioJSON("inventario.json");
                    break;
                case 10:
                    gestor.cargarInventarioJSON("inventario.json");
                    break;
                case 11:
                    gestor.exportarInventarioATexto("inventario.txt");
                    break;
                case 12:
                    gestor.importarInventarioDesdeTexto("inventario.txt");
                    break;
                case 13:
                    System.out.print("Ingrese el stock máximo para filtrar: ");
                    int stockMaximo = scanner.nextInt();
                    List<Producto> productosFiltrados = gestor.filtrarProductos(
                            producto -> producto.getStock() <= stockMaximo
                    );
                    System.out.println("Productos con stock menor o igual a " + stockMaximo + ":");
                    for (Producto p : productosFiltrados) {
                        System.out.println(p);
                    }
                    break;
                case 14:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 14);

        scanner.close();
    }
}

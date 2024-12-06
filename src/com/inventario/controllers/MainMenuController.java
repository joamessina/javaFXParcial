/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

/**
 * Controlador del menú principal de la aplicación de gestión de inventario.
 * Maneja las acciones asociadas a cada botón del menú principal.
 * @author joaxx
 */
import com.inventario.gestores.GestorInventario;
import com.inventario.productos.Producto;
import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

public class MainMenuController {

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnMostrar;
    @FXML
    private Button btnCargarJson;
    @FXML
    private Button btnGuardarJson;
    @FXML
    private Button btnExportarInventarioTexto;
    @FXML
    private Button btnImportarInventarioTexto;
    @FXML
    private Button btnGuardarInventarioArchivo;
    @FXML
    private Button btnCargarInventarioArchivo;
    @FXML
    private Button btnFiltrarProductosStockBajo;
    @FXML
    private Button btnSalir;

    private Stage primaryStage;
    private GestorInventario gestorInventario;

    /**
     * Establece el Stage principal.
     *
     * @param primaryStage el Stage principal de la aplicación.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    /**
     * Establece el GestorInventario que maneja los datos.
     *
     * @param gestorInventario el gestor de inventario.
     */
    public void setGestorInventario(GestorInventario gestorInventario) {
        this.gestorInventario = gestorInventario;
    }
    
    /**
     * Maneja la acción de agregar un nuevo producto.
     * Carga la vista "Agregar Producto".
     */
    
    @FXML
    private void handleAgregarProducto() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/inventario/views/AgregarProductoView.fxml"));
            AnchorPane agregarProductoPane = loader.load();

            Scene scene = new Scene(agregarProductoPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Agregar Producto");

            AgregarProductoController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja la acción de editar un producto existente.
     * Carga la vista "Seleccionar Producto".
     */
    @FXML
    private void handleEditarProducto() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/inventario/views/SeleccionarProductoView.fxml"));
            AnchorPane seleccionarProductoPane = loader.load();

            Scene scene = new Scene(seleccionarProductoPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Seleccionar Producto");

            SeleccionarProductoController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja la acción de eliminar un producto existente.
     * Carga la vista "Eliminar Producto".
     */
    @FXML
    private void handleEliminarProducto() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/inventario/views/EliminarProductoView.fxml"));
            AnchorPane seleccionarProductoPane = loader.load();

            Scene scene = new Scene(seleccionarProductoPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Eliminar Producto");

            EliminarProductoController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Maneja la acción de mostrar todos los productos.
     * Carga la vista "Mostrar Productos".
     */
    @FXML
    private void handleMostrarProductos() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/inventario/views/MostrarProductosView.fxml"));
            AnchorPane mostrarProductosPane = loader.load();

            Scene scene = new Scene(mostrarProductosPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Mostrar Productos");

            MostrarProductosController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Maneja la acción de cargar el inventario desde un archivo JSON.
     */
    @FXML
    private void handleCargarJson() {
        try {
            String rutaArchivo = "inventario.json";
            gestorInventario.cargarInventarioJSON(rutaArchivo);
            mostrarMensaje("Inventario cargado exitosamente desde el archivo JSON.");
        } catch (Exception e) {
            mostrarAlerta("Error al cargar el inventario desde JSON: " + e.getMessage());
        }
    }

    /**
     * Maneja la acción de guardar el inventario en un archivo JSON.
     */
    @FXML
    private void handleGuardarJson() {
        try {
            String rutaArchivo = "inventario.json";
            gestorInventario.guardarInventarioJSON(rutaArchivo);
            mostrarMensaje("Inventario guardado exitosamente en formato JSON en: " + rutaArchivo);
        } catch (Exception e) {
            mostrarAlerta("Error al guardar el inventario en JSON: " + e.getMessage());
        }
    }
    
    /**
     * Maneja la acción de exportar el inventario a un archivo de texto.
     */
    
    @FXML
    private void handleExportarInventarioTexto() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Inventario a Texto");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                gestorInventario.exportarInventarioATexto(file.getAbsolutePath());
                mostrarMensaje("Inventario exportado exitosamente.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error al exportar el inventario: " + e.getMessage());
        }
    }
    
    /**
     * Maneja la acción de importar el inventario desde un archivo de texto.
     */
    @FXML
    private void handleImportarInventarioTexto() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar Inventario desde Texto");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));
            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                gestorInventario.importarInventarioDesdeTexto(file.getAbsolutePath());
                mostrarMensaje("Inventario importado exitosamente.");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/inventario/views/MostrarProductosView.fxml"));
                AnchorPane mostrarProductosPane = loader.load();

                MostrarProductosController controller = loader.getController();
                controller.setGestorInventario(gestorInventario); 
            }
        } catch (Exception e) {
            mostrarAlerta("Error al importar el inventario: " + e.getMessage());
        }
    }

    /**
     * Maneja la acción de guardar el inventario en un archivo binario o .dat.
     */
    @FXML
    private void handleGuardarInventarioArchivo() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Inventario en Archivo");

            FileChooser.ExtensionFilter filterBin = new FileChooser.ExtensionFilter("Archivos Binarios (*.bin)", "*.bin");
            FileChooser.ExtensionFilter filterDat = new FileChooser.ExtensionFilter("Archivos DAT (*.dat)", "*.dat");
            fileChooser.getExtensionFilters().addAll(filterBin, filterDat);

            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".bin") && !filePath.endsWith(".dat")) {
                    String selectedExtension = fileChooser.getSelectedExtensionFilter().getExtensions().get(0);
                    filePath += selectedExtension.substring(1); 
                }

                gestorInventario.guardarInventario(filePath);
                mostrarMensaje("Inventario guardado exitosamente en " + filePath);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al guardar el inventario: " + e.getMessage());
        }
    }

     /**
     * Maneja la acción de cargar el inventario desde un archivo binario o .dat.
     */
    @FXML
    private void handleCargarInventarioArchivo() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cargar Inventario desde Archivo");

            FileChooser.ExtensionFilter filterBin = new FileChooser.ExtensionFilter("Archivos Binarios (*.bin)", "*.bin");
            FileChooser.ExtensionFilter filterDat = new FileChooser.ExtensionFilter("Archivos DAT (*.dat)", "*.dat");
            fileChooser.getExtensionFilters().addAll(filterBin, filterDat);

            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                gestorInventario.cargarInventario(file.getAbsolutePath());
                mostrarMensaje("Inventario cargado exitosamente desde " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            mostrarAlerta("Error al cargar el inventario: " + e.getMessage());
        }
    }

    /**
     * Maneja la acción de filtrar productos con stock bajo.
     */
    
    @FXML
    private void handleFiltrarProductosStockBajo() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Filtro de Stock Bajo");
            dialog.setHeaderText("Ingrese el límite de stock:");
            dialog.setContentText("Límite:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int limiteStock = Integer.parseInt(result.get());

                    List<Producto> productosStockBajo = gestorInventario.filtrarProductos(
                            producto -> producto.getStock() < limiteStock
                    );

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/inventario/views/MostrarProductosView.fxml"));
                    AnchorPane mostrarProductosPane = loader.load();

                    MostrarProductosController controller = loader.getController();
                    controller.setPrimaryStage(primaryStage);
                    controller.setGestorInventario(gestorInventario);

                    controller.cargarProductosEnTabla(productosStockBajo);

                    Scene scene = new Scene(mostrarProductosPane);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Productos con Stock Bajo");
                } catch (NumberFormatException e) {
                    mostrarAlerta("Por favor, ingrese un número válido para el límite.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Maneja la acción de ver las estadisticas de los productos
     */
    @FXML
    private void handleVerEstadisticas() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/inventario/views/EstadisticasView.fxml"));
            AnchorPane estadisticasPane = loader.load();

            EstadisticasController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);

            Scene scene = new Scene(estadisticasPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Estadísticas del Inventario");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja la acción de cerrar la aplicación.
     */
    
    @FXML
    private void handleSalir() {
        primaryStage.close();
    }
    
    /**
     * Muestra una alerta de advertencia con el mensaje proporcionado.
     *
     * @param mensaje el mensaje a mostrar en la alerta.
     */
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(primaryStage);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    /**
     * Muestra un mensaje informativo con el texto proporcionado.
     *
     * @param mensaje el mensaje a mostrar en la alerta.
     */
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

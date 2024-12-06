/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.inventario.gestores.GestorInventario;
import com.inventario.productos.Producto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author joaxx
 * Controlador para la vista de eliminación de productos.
 */
public class EliminarProductoController {

    @FXML
    private ListView<Producto> listViewProductos;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnVolver;

    private Stage primaryStage;
    private GestorInventario gestorInventario;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setGestorInventario(GestorInventario gestorInventario) {
        this.gestorInventario = gestorInventario;
        cargarProductos();
    }

    @FXML
    private void initialize() {
        // Inicialización si es necesaria
    }

    private void cargarProductos() {
        listViewProductos.setItems(FXCollections.observableArrayList(gestorInventario.getInventario()));
    }

    @FXML
    private void handleEliminar() {
        Producto productoSeleccionado = listViewProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            gestorInventario.eliminarProducto(productoSeleccionado.getCodigo());
            mostrarAlerta("Producto eliminado exitosamente.", Alert.AlertType.INFORMATION);
            cargarProductos(); // Refresca la lista
        } else {
            mostrarAlerta("Por favor, selecciona un producto para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inventario/views/MainMenuView.fxml"));
            AnchorPane mainMenuPane = loader.load();

            Scene scene = new Scene(mainMenuPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Inventario");

            MainMenuController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.initOwner(primaryStage);
        alert.setTitle(tipoAlerta == Alert.AlertType.INFORMATION ? "Información" : "Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
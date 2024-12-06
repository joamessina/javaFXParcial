/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.inventario.gestores.GestorInventario;
import com.inventario.productos.Producto;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author joaxx
 */
public class SeleccionarProductoController {

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private TableColumn<Producto, Integer> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colTipo;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private Button btnSeleccionar;

    @FXML
    private Button btnCancelar;

    private Stage primaryStage;
    private GestorInventario gestorInventario;
    private Producto productoSeleccionado;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setGestorInventario(GestorInventario gestorInventario) {
        this.gestorInventario = gestorInventario;
        cargarProductosEnTabla();
    }

    private void cargarProductosEnTabla() {
        ObservableList<Producto> productos = FXCollections.observableArrayList(gestorInventario.getInventario());
        tableProductos.setItems(productos);

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    @FXML
    private void handleSeleccionar() {
        productoSeleccionado = tableProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/inventario/views/EditarProductoView.fxml"));
                AnchorPane editarProductoPane = loader.load();

                Scene scene = new Scene(editarProductoPane);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Editar Producto");

                EditarProductoController controller = loader.getController();
                controller.setPrimaryStage(primaryStage);
                controller.setGestorInventario(gestorInventario);
                controller.cargarProducto(productoSeleccionado);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Por favor, selecciona un producto.");
        }
    }

    @FXML
    private void handleCancelar() {
        primaryStage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(primaryStage);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.inventario.gestores.GestorInventario;
import com.inventario.productos.Producto;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
public class MostrarProductosController {

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private TableColumn<Producto, Integer> colCodigo;

    @FXML
    private TableColumn<Producto, String> colTipo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private Button btnVolver;

    private Stage primaryStage;
    private GestorInventario gestorInventario;

    // Métodos para establecer el Stage y GestorInventario
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setGestorInventario(GestorInventario gestorInventario) {
        this.gestorInventario = gestorInventario;
        cargarProductosEnTabla(); 
    }

    // Inicializar las columnas y cargar los datos en la tabla
    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    // Sobrecarga del método cargarProductosEnTabla
    public void cargarProductosEnTabla(List<Producto> productos) {
        if (productos != null) {
            ObservableList<Producto> productosObservable = FXCollections.observableArrayList(productos);
            tableProductos.setItems(productosObservable);
        } else {
            System.out.println("La lista de productos está vacía.");
        }
    }

    // Método sin parámetros que llama al gestorInventario para obtener todos los productos
    public void cargarProductosEnTabla() {
        cargarProductosEnTabla(gestorInventario.getInventario());
    }

    @FXML
    private void handleVolver() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/inventario/views/MainMenuView.fxml"));
            AnchorPane mainMenuPane = loader.load();

            Scene scene = new Scene(mainMenuPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Menú Principal");

            MainMenuController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

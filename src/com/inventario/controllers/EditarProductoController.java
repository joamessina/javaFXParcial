/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;


import com.inventario.gestores.GestorInventario;
import com.inventario.productos.Alimento;
import com.inventario.productos.Electrodomestico;
import com.inventario.productos.CategoriaEnergetica;
import com.inventario.productos.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author joaxx
 */
public class EditarProductoController {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtTipo;

    @FXML
    private TextField txtGarantia;

    @FXML
    private ComboBox<String> cmbCategoriaEnergetica;

    @FXML
    private TextField txtFechaVencimiento;

    @FXML
    private ComboBox<String> cmbRefrigeracion;

    private Stage primaryStage;
    private GestorInventario gestorInventario;
    private Producto productoSeleccionado;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setGestorInventario(GestorInventario gestorInventario) {
        this.gestorInventario = gestorInventario;
    }

    public void cargarProducto(Producto producto) {
        this.productoSeleccionado = producto;

        txtCodigo.setText(String.valueOf(producto.getCodigo()));
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));

        if (producto instanceof Electrodomestico) {
            Electrodomestico electrodomestico = (Electrodomestico) producto;
            txtTipo.setText("Electrodoméstico");
            txtGarantia.setText(String.valueOf(electrodomestico.getGarantiaMeses()));
            cmbCategoriaEnergetica.setValue(electrodomestico.getCategoriaEnergetica().name());
        } else if (producto instanceof Alimento) {
            Alimento alimento = (Alimento) producto;
            txtTipo.setText("Alimento");
            txtFechaVencimiento.setText(alimento.getFechaVencimiento().toString());
            cmbRefrigeracion.setValue(alimento.isRefrigeracionRequerida() ? "Sí" : "No");
        }
    }

    @FXML
    private void initialize() {
        ObservableList<String> categoriasEnergeticas = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F", "G");
        cmbCategoriaEnergetica.setItems(categoriasEnergeticas);

        ObservableList<String> opcionesRefrigeracion = FXCollections.observableArrayList("Sí", "No");
        cmbRefrigeracion.setItems(opcionesRefrigeracion);
    }

    @FXML
private void handleGuardar() {
    try {
        productoSeleccionado.setNombre(txtNombre.getText());
        productoSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
        productoSeleccionado.setStock(Integer.parseInt(txtStock.getText()));

        if (productoSeleccionado instanceof Electrodomestico) {
            Electrodomestico electrodomestico = (Electrodomestico) productoSeleccionado;
            electrodomestico.setGarantiaMeses(Integer.parseInt(txtGarantia.getText()));
            electrodomestico.setCategoriaEnergetica(Enum.valueOf(CategoriaEnergetica.class, cmbCategoriaEnergetica.getValue()));
        } else if (productoSeleccionado instanceof Alimento) {
            Alimento alimento = (Alimento) productoSeleccionado;
            alimento.setFechaVencimiento(LocalDate.parse(txtFechaVencimiento.getText()));
            alimento.setRefrigeracionRequerida(cmbRefrigeracion.getValue().equals("Sí"));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("Producto actualizado exitosamente.");
        alert.showAndWait();

        regresarAlMenuPrincipal();
    } catch (Exception e) {
        mostrarAlerta("Ocurrió un error al guardar los cambios: " + e.getMessage());
    }
}


    @FXML
    private void handleCancelar() {
        regresarAlMenuPrincipal();
    }

    private void regresarAlMenuPrincipal() {
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

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

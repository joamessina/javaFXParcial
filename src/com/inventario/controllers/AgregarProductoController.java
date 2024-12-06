/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;


import com.inventario.gestores.GestorInventario;
import com.inventario.productos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author joaxx
 */
public class AgregarProductoController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private ComboBox<String> cmbTipoProducto;

    @FXML
    private VBox vboxAlimento;

    @FXML
    private DatePicker dateFechaVencimiento;

    @FXML
    private CheckBox chkRefrigeracion;

    @FXML
    private VBox vboxElectrodomestico;

    @FXML
    private TextField txtGarantia;

    @FXML
    private ComboBox<String> cmbCategoriaEnergetica;

    private Stage primaryStage;
    private GestorInventario gestorInventario;

    /**
     * Establece el Stage principal para esta vista.
     *
     * @param primaryStage El Stage principal de la aplicación.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Establece el gestor de inventario utilizado por este controlador.
     *
     * @param gestorInventario El gestor de inventario de la aplicación.
     */
    public void setGestorInventario(GestorInventario gestorInventario) {
        this.gestorInventario = gestorInventario;
    }

    @FXML
    private void initialize() {
        ObservableList<String> tiposProducto = FXCollections.observableArrayList("Alimento", "Electrodoméstico");
        cmbTipoProducto.setItems(tiposProducto);
        cmbTipoProducto.setPromptText("Selecciona Tipo");

        ObservableList<String> categoriasEnergeticas = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F", "G");
        cmbCategoriaEnergetica.setItems(categoriasEnergeticas);
        cmbCategoriaEnergetica.setPromptText("Selecciona Categoría");

        vboxAlimento.setVisible(false);
        vboxElectrodomestico.setVisible(false);
    }

    /**
     * Maneja el cambio del tipo de producto seleccionado en la interfaz.
     *
     * @param event Evento generado por el cambio de selección en el ComboBox.
     */
    @FXML
    private void handleTipoProductoChange(ActionEvent event) {
        String tipo = cmbTipoProducto.getValue();
        if (tipo != null) {
            switch (tipo) {
                case "Alimento":
                    vboxAlimento.setVisible(true);
                    vboxElectrodomestico.setVisible(false);
                    break;
                case "Electrodoméstico":
                    vboxAlimento.setVisible(false);
                    vboxElectrodomestico.setVisible(true);
                    break;
                default:
                    vboxAlimento.setVisible(false);
                    vboxElectrodomestico.setVisible(false);
                    break;
            }
        } else {
            vboxAlimento.setVisible(false);
            vboxElectrodomestico.setVisible(false);
        }
    }

    /**
     * Maneja el evento de guardar un producto en el inventario.
     *
     * @param event Evento generado por el clic en el botón Guardar.
     */
    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            String nombre = txtNombre.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String stockStr = txtStock.getText().trim();
            String tipo = cmbTipoProducto.getValue();

            if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || tipo == null) {
                mostrarAlerta("Por favor, completa todos los campos obligatorios.");
                return;
            }

            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            Producto nuevoProducto = null;

            if (tipo.equals("Alimento")) {
                LocalDate fechaVencimiento = dateFechaVencimiento.getValue();
                boolean refrigeracionRequerida = chkRefrigeracion.isSelected();

                if (fechaVencimiento == null) {
                    mostrarAlerta("Por favor, selecciona una fecha de vencimiento.");
                    return;
                }

                Alimento alimento = new Alimento();
                alimento.setNombre(nombre);
                alimento.setTipo("Alimento");
                alimento.setPrecio(precio);
                alimento.setStock(stock);
                alimento.setFechaVencimiento(fechaVencimiento);
                alimento.setRefrigeracionRequerida(refrigeracionRequerida);

                nuevoProducto = alimento;

            } else if (tipo.equals("Electrodoméstico")) {
                String garantiaStr = txtGarantia.getText().trim();
                String categoriaEnergetica = cmbCategoriaEnergetica.getValue();

                if (garantiaStr.isEmpty() || categoriaEnergetica == null) {
                    mostrarAlerta("Por favor, completa todos los campos específicos del electrodoméstico.");
                    return;
                }

                int garantiaMeses = Integer.parseInt(garantiaStr);

                Electrodomestico electrodomestico = new Electrodomestico();
                electrodomestico.setNombre(nombre);
                electrodomestico.setTipo("Electrodomestico");
                electrodomestico.setPrecio(precio);
                electrodomestico.setStock(stock);
                electrodomestico.setGarantiaMeses(garantiaMeses);
                electrodomestico.setCategoriaEnergetica(CategoriaEnergetica.valueOf(categoriaEnergetica));

                nuevoProducto = electrodomestico;
            }

            if (nuevoProducto != null) {
                gestorInventario.agregarProducto(nuevoProducto);
                mostrarMensaje("Producto agregado exitosamente.");
                regresarAlMenuPrincipal();
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Por favor, ingresa valores numéricos válidos para precio, stock y garantía.");
        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error al agregar el producto: " + e.getMessage());
        }
    }

    /**
     * Maneja el evento de cancelar la acción actual y regresar al menú principal.
     *
     * @param event Evento generado por el clic en el botón Cancelar.
     */
    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAlMenuPrincipal();
    }

    /**
     * Regresa al menú principal.
     */
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

    /**
     * Muestra un mensaje de alerta con el texto especificado.
     *
     * @param mensaje El mensaje que se mostrará en la alerta.
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
     * Muestra un mensaje informativo con el texto especificado.
     *
     * @param mensaje El mensaje que se mostrará en la alerta informativa.
     */
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

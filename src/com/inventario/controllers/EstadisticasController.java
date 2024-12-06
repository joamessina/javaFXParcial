/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.inventario.gestores.GestorInventario;
import com.inventario.productos.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Controlador para la vista de estadísticas del inventario.
 * Muestra diferentes gráficos que representan datos del inventario.
 * 
 * @author joaxx
 */
public class EstadisticasController {

    @FXML
    private PieChart pieChartDistribucion;

    @FXML
    private BarChart<String, Number> barChartStock;

    @FXML
    private ScatterChart<Number, Number> scatterChartPrecioStock;

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
     * Establece el gestor del inventario y carga las estadísticas.
     *
     * @param gestorInventario el gestor de inventario.
     */
    public void setGestorInventario(GestorInventario gestorInventario) {
        this.gestorInventario = gestorInventario;
        cargarEstadisticas();
    }

    /**
     * Método principal para cargar todas las estadísticas.
     * Llama a los métodos individuales para cargar cada gráfico.
     */
    private void cargarEstadisticas() {
        cargarDistribucionProductos();
        cargarStockPorCategoria();
        cargarPrecioVsStock();
    }

    /**
     * Carga el gráfico de torta con la distribución de productos.
     * Cuenta cuántos productos son de tipo "Alimento" y cuántos son "Electrodoméstico".
     */
    private void cargarDistribucionProductos() {
        int alimentos = (int) gestorInventario.getInventario().stream()
                .filter(producto -> producto instanceof com.inventario.productos.Alimento)
                .count();

        int electrodomesticos = (int) gestorInventario.getInventario().stream()
                .filter(producto -> producto instanceof com.inventario.productos.Electrodomestico)
                .count();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Alimentos", alimentos),
                new PieChart.Data("Electrodomésticos", electrodomesticos)
        );

        pieChartDistribucion.setData(data);
    }

    /**
     * Carga el gráfico de barras con el stock total por categoría.
     * Suma el stock de los productos por categoría (Alimentos y Electrodomésticos).
     */
    private void cargarStockPorCategoria() {
        int stockAlimentos = gestorInventario.getInventario().stream()
                .filter(producto -> producto instanceof com.inventario.productos.Alimento)
                .mapToInt(Producto::getStock)
                .sum();

        int stockElectrodomesticos = gestorInventario.getInventario().stream()
                .filter(producto -> producto instanceof com.inventario.productos.Electrodomestico)
                .mapToInt(Producto::getStock)
                .sum();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Alimentos", stockAlimentos));
        series.getData().add(new XYChart.Data<>("Electrodomésticos", stockElectrodomesticos));

        barChartStock.getData().add(series);
    }

    /**
     * Carga el gráfico de dispersión con la relación entre precio y stock.
     * Cada punto representa un producto con su precio y stock.
     */
    private void cargarPrecioVsStock() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        gestorInventario.getInventario().forEach(producto -> {
            series.getData().add(new XYChart.Data<>(producto.getStock(), producto.getPrecio()));
        });

        scatterChartPrecioStock.getData().add(series);
    }

    /**
     * Maneja la acción de volver al menú principal.
     * Carga la vista del menú principal.
     */
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

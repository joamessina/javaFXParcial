package com.inventario.app;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import com.inventario.gestores.GestorInventario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author joaxx
 */
public class NewFXMain extends Application {

    private Stage primaryStage;
    private GestorInventario gestorInventario = new GestorInventario();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestión de Inventario");

        mostrarMenuPrincipal();
    }

    /**
     * Muestra el menú principal de la aplicación cargando la vista FXML.
     * @param primaryStage Stage principal donde se mostrará la escena.
     */
    public void mostrarMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlLocation = getClass().getResource("/com/inventario/views/MainMenuView.fxml");
            System.out.println("FXML Location: " + fxmlLocation);
            if (fxmlLocation == null) {
                System.out.println("El archivo FXML no se encontró en la ruta especificada.");
                return;
            }
            loader.setLocation(fxmlLocation);
            AnchorPane menuPrincipal = loader.load();

            Scene scene = new Scene(menuPrincipal);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Inventario");
            primaryStage.show();

            com.inventario.controllers.MainMenuController controller = loader.getController();

            controller.setPrimaryStage(primaryStage);
            controller.setGestorInventario(gestorInventario);

            System.out.println("GestorInventario pasado a MainMenuController: " + gestorInventario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método principal de la aplicación. Lanza la ejecución de la aplicación JavaFX.
     * @param args Argumentos pasados desde la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

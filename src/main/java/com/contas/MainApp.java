package com.contas;

import com.contas.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getRoot(), 400, 300);
        primaryStage.setTitle("Login - Controle de Contas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.contas.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LoginView {

    private final VBox root;
    private final TextField emailField;
    private final PasswordField senhaField;
    private final Label mensagemLabel;

    public LoginView() {
        root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Login");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label senhaLabel = new Label("Senha:");
        senhaField = new PasswordField();

        form.add(emailLabel, 0, 0);
        form.add(emailField, 1, 0);
        form.add(senhaLabel, 0, 1);
        form.add(senhaField, 1, 1);

        Button loginButton = new Button("Entrar");
        loginButton.setOnAction(e -> fazerLogin());

        mensagemLabel = new Label();

        root.getChildren().addAll(titulo, form, loginButton, mensagemLabel);
    }

    public VBox getRoot() {
        return root;
    }

    private void fazerLogin() {
        String email = emailField.getText();
        String senha = senhaField.getText();
        // Aqui futuramente vamos integrar com UsuarioDAO para verificar login
        if (email.equals("admin") && senha.equals("admin")) {
            mensagemLabel.setText("Login bem-sucedido!");
        } else {
            mensagemLabel.setText("Email ou senha incorretos.");
        }
    }
}

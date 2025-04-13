package com.contas.view;

import com.contas.MainApp;
import com.contas.dao.UsuarioDAO;
import com.contas.model.Usuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginView {

    private final VBox root;
    private final TextField emailField;
    private final PasswordField senhaField;
    private final Label mensagemLabel;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

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
        loginButton.setOnAction(e -> {
            try {
                fazerLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button cadastrarButton = new Button("Criar conta");
        cadastrarButton.setOnAction(e -> abrirCadastro());

        mensagemLabel = new Label();

        root.getChildren().addAll(titulo, form, loginButton, cadastrarButton, mensagemLabel);
    }

    public VBox getRoot() {
        return root;
    }

    private void fazerLogin() throws SQLException {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (email.isBlank() || senha.isBlank()) {
            mensagemLabel.setText("Não esqueça dos campos obrigatórios! :]");
            return;
        }

        Usuario usuario = usuarioDAO.login(email, senha);
        if (usuario != null) {
            mensagemLabel.setText("Login bem-sucedido. Bem-vindo, " + usuario.getName() + "!");
            DashboardView dashboard = new DashboardView(usuario);
            Scene scene = new Scene(dashboard.getRoot(), 500, 400);
            MainApp.primaryStage.setScene(scene);
        } else {
            mensagemLabel.setText("Email ou senha incorretos.");
        }
    }

    private void abrirCadastro() {
        CadastroView cadastroView = new CadastroView();
        Scene scene = new Scene(cadastroView.getRoot(), 400, 300);
        MainApp.primaryStage.setScene(scene);
    }
}
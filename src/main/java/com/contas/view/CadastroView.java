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

public class CadastroView {

    private final VBox root;
    private final TextField nomeField;
    private final TextField emailField;
    private final PasswordField senhaField;
    private final Label mensagemLabel;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public CadastroView() {
        root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Cadastro");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        nomeField = new TextField();
        emailField = new TextField();
        senhaField = new PasswordField();

        form.add(new Label("Nome:"), 0, 0);
        form.add(nomeField, 1, 0);
        form.add(new Label("Email:"), 0, 1);
        form.add(emailField, 1, 1);
        form.add(new Label("Senha:"), 0, 2);
        form.add(senhaField, 1, 2);

        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setOnAction(e -> cadastrarUsuario());

        Button voltarButton = new Button("Voltar");
        voltarButton.setOnAction(e -> voltarLogin());

        mensagemLabel = new Label();

        root.getChildren().addAll(titulo, form, cadastrarButton, voltarButton, mensagemLabel);
    }

    public VBox getRoot() {
        return root;
    }

    private void cadastrarUsuario() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            mensagemLabel.setText("Preencha todos os campos.");
            return;
        }

        Usuario novo = new Usuario(0, nome, email, senha);
        if (usuarioDAO.cadastrar(novo)) {
            mensagemLabel.setText("Usuário cadastrado com sucesso.");
        } else {
            mensagemLabel.setText("Erro: email já cadastrado.");
        }
    }

    private void voltarLogin() {
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getRoot(), 400, 300);
        MainApp.primaryStage.setScene(scene);
    }
}
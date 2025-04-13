package com.contas.view;

import com.contas.MainApp;
import com.contas.dao.DespesaDAO;
import com.contas.model.Despesas;
import com.contas.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardView {

    private final VBox root;
    private final Usuario usuario;
    private final DespesaDAO despesaDAO = new DespesaDAO();
    private final ObservableList<String> listaDespesas;

    public DashboardView(Usuario usuario) {
        this.usuario = usuario;
        this.root = new VBox(10);
        this.root.setPadding(new Insets(20));

        Label titulo = new Label("Olá, " + usuario.getName() + " - Suas Despesas");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> listView = new ListView<>();
        listaDespesas = FXCollections.observableArrayList();
        listView.setItems(listaDespesas);
        carregarDespesas();

        Button novaDespesa = new Button("Nova Despesa");
        novaDespesa.setOnAction(e -> abrirNovaDespesa());

        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            LoginView loginView = new LoginView();
            Scene scene = new Scene(loginView.getRoot(), 400, 300);
            MainApp.primaryStage.setScene(scene);
        });

        HBox botoes = new HBox(10, novaDespesa, logout);

        root.getChildren().addAll(titulo, listView, botoes);
    }

    public VBox getRoot() {
        return root;
    }

    private void carregarDespesas() {
        listaDespesas.clear();
        List<Despesas> despesas = despesaDAO.listarPorUsuario(usuario.getId());
        for (Despesas d : despesas) {
            String linha = String.format("%s - R$ %.2f | %s | %s",
                    d.getName(), d.getValor(), d.getVencimento(), d.getStatus());
            listaDespesas.add(linha);
        }
    }

    private void abrirNovaDespesa() {
        VBox novaDespesaForm = new VBox(10);
        novaDespesaForm.setPadding(new Insets(20));

        TextField nomeField = new TextField();
        TextField valorField = new TextField();
        DatePicker vencimentoPicker = new DatePicker();
        DatePicker mesField = new DatePicker();

        Button salvarButton = new Button("Salvar");
        Label msg = new Label();

        salvarButton.setOnAction(e -> {
            try {
                String nome = nomeField.getText();
                double valor = Double.parseDouble(valorField.getText());
                LocalDate vencimento = vencimentoPicker.getValue();
                LocalDate mes = mesField.getValue();

                if (vencimento == null) throw new IllegalArgumentException("Data inválida");
                String vencimentoStr = vencimento.format(DateTimeFormatter.ISO_LOCAL_DATE);
                String mesRef = mes.format(DateTimeFormatter.ISO_LOCAL_DATE);

                Despesas nova = new Despesas(usuario.getId(), 0, nome, valor, vencimentoStr, "nao paga", mesRef);
                if (despesaDAO.adicionar(nova)) {
                    msg.setText("Despesa salva com sucesso.");
                    carregarDespesas();
                    MainApp.primaryStage.setScene(new Scene(this.getRoot(), 500, 400));
                } else {
                    msg.setText("Erro ao salvar despesa.");
                }
            } catch (Exception ex) {
                msg.setText("Erro: Verifique os campos.");
            }
        });

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.add(new Label("Nome:"), 0, 0);
        form.add(nomeField, 1, 0);
        form.add(new Label("Valor:"), 0, 1);
        form.add(valorField, 1, 1);
        form.add(new Label("Vencimento:"), 0, 2);
        form.add(vencimentoPicker, 1, 2);
        form.add(new Label("Mês Referência (AAAA-MM):"), 0, 3);
        form.add(mesField, 1, 3);

        novaDespesaForm.getChildren().addAll(new Label("Nova Despesa"), form, salvarButton, msg);
        MainApp.primaryStage.setScene(new Scene(novaDespesaForm, 400, 300));
    }
}
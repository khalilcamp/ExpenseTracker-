// src/main/java/com/contas/Main.java
package com.contas;

import com.contas.dao.DespesaDAO;
import com.contas.dao.UsuarioDAO;
import com.contas.database.SQLiteConnection;
import com.contas.model.Despesas;
import com.contas.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Inicializando sistema de controle de contas...");
        SQLiteConnection.init();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario novoUsuario = new Usuario(2,"João da Silva", "joao2@email.com", "123456");

        if (usuarioDAO.cadastrar(novoUsuario)) {
            System.out.println("Usuário cadastrado com sucesso.");
        }

        Usuario usuarioLogado = usuarioDAO.login("joao@email.com", "123456");
        if (usuarioLogado != null) {
            System.out.println("Login realizado com sucesso: " + usuarioLogado.getName());

            DespesaDAO despesaDAO = new DespesaDAO();
            Despesas novaDespesa = new Despesas(
                    usuarioLogado.getId(),
                    1,
                    "Internet",
                    120.50,
                    "2025-04-20",
                    "nao paga",
                    "2025-04"
            );

            if (despesaDAO.adicionar(novaDespesa)) {
                System.out.println("Despesa adicionada com sucesso.");
            }

            List<Despesas> despesas = despesaDAO.listarPorUsuario(usuarioLogado.getId());
            System.out.println("Despesas do usuário:");
            for (Despesas d : despesas) {
                System.out.printf("- %s: R$%.2f | Vencimento: %s | Status: %s\n",
                        d.getName(), d.getValor(), d.getVencimento(), d.getStatus());
            }
        } else {
            System.out.println("Falha no login.");
        }

        System.out.println("Sistema pronto.");
    }
}
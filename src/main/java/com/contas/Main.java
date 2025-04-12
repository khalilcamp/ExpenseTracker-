package com.contas;

import com.contas.dao.DespesaDAO;
import com.contas.dao.UsuarioDAO;
import com.contas.database.SQLiteConnection;
import com.contas.model.Despesas;
import com.contas.model.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final DespesaDAO despesaDAO = new DespesaDAO();
    private static Usuario usuarioLogado = null;

    public static void main(String[] args) throws SQLException {
        SQLiteConnection.init();
        menuPrincipal();
    }

    private static void menuPrincipal() throws SQLException {
        while (true) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> login();
                case 0 -> {
                    System.out.println("Saindo do sistema.");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario novo = new Usuario(0, nome, email, senha);
        if (usuarioDAO.cadastrar(novo)) {
            System.out.println("Usuário cadastrado com sucesso.");
        } else {
            System.out.println("Erro ao cadastrar usuário. Email já está em uso.");
        }
    }

    private static void login() throws SQLException {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        usuarioLogado = usuarioDAO.login(email, senha);
        if (usuarioLogado != null) {
            System.out.println("Login bem-sucedido. Bem-vindo, " + usuarioLogado.getName() + "!");
            menuUsuario();
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    private static void menuUsuario() {
        while (true) {
            System.out.println("\n--- MENU DO USUÁRIO ---");
            System.out.println("1. Adicionar despesa");
            System.out.println("2. Listar despesas");
            System.out.println("0. Logout");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> adicionarDespesa();
                case 2 -> listarDespesas();
                case 0 -> {
                    usuarioLogado = null;
                    System.out.println("Logout realizado.");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void adicionarDespesa() {
        System.out.print("Nome da despesa: ");
        String nome = scanner.nextLine();
        System.out.print("Valor (R$): ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Data de vencimento (YYYY-MM-DD): ");
        String vencimento = scanner.nextLine();
        System.out.print("Mês referência (YYYY-MM): ");
        String mes = scanner.nextLine();

        Despesas despesa = new Despesas(usuarioLogado.getId(), 0, nome, valor, vencimento, "nao paga", mes);
        if (despesaDAO.adicionar(despesa)) {
            System.out.println("Despesa adicionada com sucesso.");
        } else {
            System.out.println("Erro ao adicionar despesa.");
        }
    }

    private static void listarDespesas() {
        List<Despesas> despesas = despesaDAO.listarPorUsuario(usuarioLogado.getId());
        System.out.println("\n--- SUAS DESPESAS ---");
        for (Despesas d : despesas) {
            System.out.printf("- %s: R$%.2f | Vencimento: %s | Status: %s\n",
                    d.getName(), d.getValor(), d.getVencimento(), d.getStatus());
        }
    }
}
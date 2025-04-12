package com.contas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {
    private static final String DB_URL = "jdbc:sqlite:contas.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    String createUsuarios = """
                        CREATE TABLE IF NOT EXISTS usuarios (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            email TEXT UNIQUE NOT NULL,
                            senha_hash TEXT NOT NULL
                        );
                    """;

                    String createDespesas = """
                        CREATE TABLE IF NOT EXISTS despesas (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            usuario_id INTEGER NOT NULL,
                            name TEXT NOT NULL,
                            valor REAL NOT NULL,
                            vencimento TEXT NOT NULL,
                            status TEXT NOT NULL CHECK(status IN ('paga', 'nao paga')),
                            mes TEXT NOT NULL,
                            FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
                        );
                    """;

                    stmt.execute(createUsuarios);
                    stmt.execute(createDespesas);
                    System.out.println("Tabelas criadas com sucesso.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
package com.contas.dao;

import com.contas.database.SQLiteConnection;
import com.contas.model.Despesas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    public boolean adicionar(Despesas despesa) {
        String sql = "INSERT INTO despesas (usuario_id, name, valor, vencimento, status, mes) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, despesa.getUsuarioId());
            pstmt.setString(2, despesa.getName());
            pstmt.setDouble(3, despesa.getValor());
            pstmt.setString(4, despesa.getVencimento());
            pstmt.setString(5, despesa.getStatus());
            pstmt.setString(6, despesa.getMes());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar despesa: " + e.getMessage());
            return false;
        }
    }

    public List<Despesas> listarPorUsuario(int usuarioId) {
        List<Despesas> despesas = new ArrayList<>();
        String sql = "SELECT * FROM despesas WHERE usuario_id = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Despesas d = new Despesas(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("name"),
                        rs.getDouble("valor"),
                        rs.getString("vencimento"),
                        rs.getString("status"),
                        rs.getString("mes")
                );
                despesas.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar despesas: " + e.getMessage());
        }
        return despesas;
    }

    public boolean atualizarStatus(int despesaId, String novoStatus) {
        String sql = "UPDATE despesas SET status = ? WHERE id = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, despesaId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da despesa: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(int despesaId) {
        String sql = "DELETE FROM despesas WHERE id = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, despesaId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao remover despesa: " + e.getMessage());
            return false;
        }
    }
}

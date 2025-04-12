package com.contas.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.contas.database.SQLiteConnection;
import com.contas.model.Usuario;

import java.sql.*;

public class UsuarioDAO {
    // Metodo cadastrar usando SQL :]. Odeio SQLite
    public boolean cadastrar(Usuario usuario){
        String sql = "INSERT INTO usuarios (name,email,senha_hash) VALUES (?, ?, ?)";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String senhaHash = BCrypt.withDefaults().hashToString(12,usuario.getSenhaHash().toCharArray());
            pstmt.setString(1, usuario.getName());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, senhaHash);

            // Executor SQL, usar para INSERT, UPDATE E DELETE.
            pstmt.executeUpdate();
            return true;
        } catch ( SQLException e){
            System.err.println("Erro ao cadastrar o usuário. \n Erro: " + e.getMessage());
            return false;
        }
    }
    // Loginzinho.
    public Usuario login( String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        
        try (Connection conn = SQLiteConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                String hash = rs.getString("senha_hash");
                BCrypt.Result result = BCrypt.verifyer().verify(senha.toCharArray(), hash);
                if (result.verified){
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            hash
                    );
                }
            }
        } catch (SQLException e){
            System.err.println("Erro ao logar! \n Verifique o erro:" + e.getMessage());
        }
        return null;
    }
}

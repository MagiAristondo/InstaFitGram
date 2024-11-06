/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.instafitgram3.DataAcces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import spdvi.instafitgram3.dto.User;

/**
 *
 * @author giari
 */
public class DataAccess {
    private Connection getConection() {
        Connection connection = null;
        String connectionString = "jdbc:sqlserver://localhost;database=simulapdb;trustServerCertificate=true;user=sa;password=1234;";

        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public User getUserByUsername(String username) {
        User usuario = null;
        String sql = "Select * FROM Usuaris WHERE Nom=?";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                usuario = new User();
                usuario.setId(resultSet.getInt("Id"));
                usuario.setNom(resultSet.getString("Nom"));
                usuario.setEmail(resultSet.getString("Email"));
                usuario.setPasswordHash(resultSet.getString("PasswordHash"));
                //user.setFoto(resultSet.getBytes("Foto"));
                if (resultSet.getInt("IsInstructor") == 1) {
                    usuario.setIsIntructor(true);
                } else {
                    usuario.setIsIntructor(false);
                }
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;
    }
}

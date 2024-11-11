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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import spdvi.instafitgram3.dto.Attempt;
import spdvi.instafitgram3.dto.Review;
import spdvi.instafitgram3.dto.User;

/**
 *
 * @author giari
 */
public class DataAccess {
    private Connection getConection() {
        Connection connection = null;
        String connectionString = "jdbc:sqlserver://localhost:1433;database=simulapdb;trustServerCertificate=true;user=sa;password=1234;";

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
                    usuario.setIsInstructor(true);
                } else {
                    usuario.setIsInstructor(false);
                }
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;
    }
    
    public String insertUser(User user) {
        String result = "";
        String sql = "SET IDENTITY_INSERT Usuaris ON;"
                + "INSERT INTO Usuaris (Id, Nom, Email, PasswordHash, IsInstructor) VALUES (?,?,?,?,?);";
        Connection connection = getConection();

        try {
            PreparedStatement insertStatement = connection.prepareCall(sql);
            insertStatement.setInt(1, getMaxUserId());
            insertStatement.setString(2, user.getNom());
            insertStatement.setString(3, user.getEmail());
            insertStatement.setString(4, user.getPasswordHash());
            insertStatement.setBoolean(5, user.isIsInstructor());
            int resultset = insertStatement.executeUpdate();
            result = "Insert successful";

            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    
    public int getMaxUserId() {
        int maxId = 0;
        String sql = "SELECT MAX(Id) FROM Usuaris";
        try (Connection connection = getConection();
             PreparedStatement selectStatement = connection.prepareStatement(sql);
             ResultSet resultSet = selectStatement.executeQuery()) {

            if (resultSet.next()) {
                maxId = resultSet.getInt(1); // Get the value from the first column
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return maxId + 1;
    }
    
    public List<User> getUsers() {
        List<User> usuaris = new ArrayList<User>();
        String sql = "Select * FROM Usuaris";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                User usuario = new User();
                usuario.setId(resultSet.getInt("Id"));
                usuario.setNom(resultSet.getString("Nom"));
                usuario.setEmail(resultSet.getString("Email"));
                usuario.setPasswordHash(resultSet.getString("PasswordHash"));
                //user.setFoto(resultSet.getBytes("Foto"));
                if (resultSet.getInt("IsInstructor") == 1) {
                    usuario.setIsInstructor(true);
                } else {
                    usuario.setIsInstructor(false);
                }
                usuaris.add(usuario);
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuaris;
    }
    
        public List<Attempt> getAttemptsByUserId(String userId, boolean nomesSenseReview) {
        List<Attempt> intents = new ArrayList<Attempt>();
        String sql;
        
        if (!nomesSenseReview)
            sql = "SELECT i.*, e.NomExercici FROM Intents i JOIN Exercicis e ON i.IdExercici = e.Id WHERE i.IdUsuari=?";
        else
            sql = "SELECT i.*, e.NomExercici FROM Intents i JOIN Exercicis e ON i.IdExercici = e.Id WHERE i.IdUsuari=? AND i.Id NOT IN (SELECT IdIntent FROM Review)";
        
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Attempt intent = new Attempt();
                intent.setId(resultSet.getInt("Id"));
                intent.setIdUser(resultSet.getInt("IdUsuari"));
                intent.setIdExercise(resultSet.getInt("IdExercici"));
                intent.setTimeStampStart(resultSet.getDate("Timestamp_Inici"));
                intent.setTimeStampEnd(resultSet.getDate("Timestamp_Fi"));
                intent.setVideoFile(resultSet.getString("Videofile"));
                intent.setExercise(resultSet.getString("NomExercici"));
                
                intents.add(intent);
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return intents;
    }
        
    public Attempt getAttemptByUserIdAndNomExercici(String userId, String nomExercici) {
        Attempt intent = null;
        String sql = "SELECT i.*, e.NomExercici FROM Intents i JOIN Exercicis e ON i.IdExercici = e.Id WHERE i.IdUsuari=? AND e.NomExercici=?";
        
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, userId);
            selectStatement.setString(2, nomExercici);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                intent = new Attempt();
                intent.setId(resultSet.getInt("Id"));
                intent.setIdUser(resultSet.getInt("IdUsuari"));
                intent.setIdExercise(resultSet.getInt("IdExercici"));
                intent.setTimeStampStart(resultSet.getDate("Timestamp_Inici"));
                intent.setTimeStampEnd(resultSet.getDate("Timestamp_Fi"));
                intent.setVideoFile(resultSet.getString("Videofile"));
                intent.setExercise(resultSet.getString("NomExercici"));
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return intent;
    }
    
    public List<Review> getReviewsByIdIntent(int IdIntent) {
        List<Review> reviews = new ArrayList<Review>();
        String sql = "SELECT * FROM Review WHERE IdIntent=?";
        
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1, IdIntent);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("Id"));
                review.setIdAttempt(resultSet.getInt("IdIntent"));
                review.setIdReviewer(resultSet.getInt("IdReviewer"));
                review.setReview(resultSet.getInt("Valoracio"));
                review.setComment(resultSet.getString("Comentari"));
                
                reviews.add(review);
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reviews;
    }
    
    public int getMaxReviewId() {
        int maxId = 0;
        String sql = "SELECT MAX(Id) FROM Review";
        try (Connection connection = getConection();
             PreparedStatement selectStatement = connection.prepareStatement(sql);
             ResultSet resultSet = selectStatement.executeQuery()) {

            if (resultSet.next()) {
                maxId = resultSet.getInt(1); // Get the value from the first column
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return maxId + 1;
    }
    
    public String insertReview(Review review) {
        String result = "";
        String sql = "SET IDENTITY_INSERT Review ON;"
                + "INSERT INTO Review (Id, IdIntent, IdReviewer, Valoracio, Comentari) VALUES (?,?,?,?,?);";
        Connection connection = getConection();

        try {
            PreparedStatement insertStatement = connection.prepareCall(sql);
            insertStatement.setInt(1, getMaxReviewId());
            insertStatement.setInt(2, review.getIdAttempt());
            insertStatement.setInt(3, review.getIdReviewer());
            insertStatement.setInt(4, review.getReview());
            insertStatement.setString(5, review.getComment());
            int resultset = insertStatement.executeUpdate();
            result = "Insert successful";

            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    
    public String updateReview(Review review) {
        String result = "";
        String sql = "UPDATE Review SET IdIntent = ?, IdReviewer = ?, Valoracio = ?, Comentari = ? WHERE Id = ?;";
        Connection connection = getConection();

        try {
            PreparedStatement insertStatement = connection.prepareCall(sql);
            insertStatement.setInt(1, review.getIdAttempt());
            insertStatement.setInt(2, review.getIdReviewer());
            insertStatement.setInt(3, review.getReview());
            insertStatement.setString(4, review.getComment());
            insertStatement.setInt(5, review.getId());
            int resultset = insertStatement.executeUpdate();
            result = "Update successful";

            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    
    public String deleteReview(int idReview) {
        String result = "";
        String sql = "DELETE FROM Review WHERE Id = ?;";
        Connection connection = getConection();

        try {
            PreparedStatement insertStatement = connection.prepareCall(sql);
            
            insertStatement.setInt(1, idReview);
            int resultset = insertStatement.executeUpdate();
            result = "Delete successful";

            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}

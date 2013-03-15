/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kexjobb;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Handles queries to the mysql database.
 * Url to the database, user and password to database are stored separately a file called Databasedetails.
 * @author Shaan
 */
public class Database {
    String url = "";
    String user = "";
    String password = "";
    String query = "";
    
    public Database() {
        try {
            File file = new File("Databasedetails");
            try {
                System.out.println(file.getCanonicalPath());
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scanner scan = new Scanner(file);
            url = scan.nextLine();
            user = scan.nextLine();
            password = scan.nextLine();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addRating(String id, String songOne, String songTwo, String Rating) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        query = "INSERT INTO reviews (Id, Songone, Songtwo, Time, Rating) VALUES (?,?,?,?,?)";
        
        try {
            Connection con = (Connection) DriverManager.getConnection(url, user, password);
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, songOne);
            pst.setString(3, songTwo);
            
            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
            
            pst.setTimestamp(4, sqlTimestamp);
            pst.setInt(5, Integer.parseInt(Rating));
            pst.executeUpdate();
            
            pst.close();
            con.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Finish'd addRating()");
    }
}

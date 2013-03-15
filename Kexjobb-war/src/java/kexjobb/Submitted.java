/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kexjobb;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Gets called when a user submits a rating. Instantiates a Database and adds the rating of the songs to it.
 * @author Shaan
 */
public class Submitted extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        HttpSession sess = request.getSession(true);
        
        if(request.getParameter("action").equals("rate")){
            
            //		if (sess != null) {
            //			sess.invalidate();
            //		}
            //
            //		sess = request.getSession(true);
            
            
            try {
                Database db = new Database();
                String id = sess.getId();
                String rating = request.getParameter("rating");
                String songOne = (String)sess.getAttribute("songOne");
                String songTwo = (String)sess.getAttribute("songTwo");
                db.addRating(id, songOne, songTwo, rating);
                
            } catch (Exception ex) {
                Logger.getLogger(LoadController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("LoadController?action=continue").forward(request, response);
        }
    }
}

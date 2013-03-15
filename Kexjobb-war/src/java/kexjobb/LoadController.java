package kexjobb;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

/**
 * Handles the sessions.
 * @author Shaan
 */

@WebServlet(name = "LoadController", urlPatterns = {"/LoadController"})
public class LoadController extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        HttpSession sess = request.getSession(true);
        
        //		if (sess != null) {
        //			sess.invalidate();
        //		}
        //
        //		sess = request.getSession(true);
        
        
        try {
            if(request.getParameter("action").equals("start")){
                request.setCharacterEncoding("UTF-8");
                InfoExtractor extractor = new InfoExtractor();
                String[] songs = extractor.getSong();
                String songOne = songs[0];
                String songTwo = songs[1];
                String songOneUrl = extractor.getUrl(songOne);
                String songTwoUrl = extractor.getUrl(songTwo);
                
                sess.setAttribute("songOne", songOne);
                sess.setAttribute("songTwo", songTwo);
                sess.setAttribute("songOneUrl", songOneUrl);
                sess.setAttribute("songTwoUrl", songTwoUrl);
                sess.setAttribute("extractor", extractor);
            }
            else if(request.getParameter("action").equals("continue")){
                InfoExtractor extractor = (InfoExtractor)sess.getAttribute("extractor");
                if(extractor == null){
                    extractor = new InfoExtractor();
                }
                String[] songs = extractor.getSong();
                String songOne = songs[0];
                String songTwo = songs[1];
                String songOneUrl = extractor.getUrl(songOne);
                String songTwoUrl = extractor.getUrl(songTwo);
                
                sess.setAttribute("songOne", songOne);
                sess.setAttribute("songTwo", songTwo);
                sess.setAttribute("songOneUrl", songOneUrl);
                sess.setAttribute("songTwoUrl", songTwoUrl);
                sess.setAttribute("extractor", extractor);
            }
            else if(request.getParameter("action").equals("debug")){
                sess.setAttribute("songOne", "");
                sess.setAttribute("songTwo", "");
                sess.setAttribute("songOneUrl", "");
                sess.setAttribute("songTwoUrl", "");
                sess.setAttribute("extractor", "");
            }
            else{
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("main.jsp").forward(request, response);
    }
}

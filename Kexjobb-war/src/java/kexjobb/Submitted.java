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
 */
public class Submitted extends HttpServlet {
	
	/**
	 * doGet is called every time a user submits a rating.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		HttpSession sess = request.getSession(false);
		
		if(request.getParameter("action").equals("rate") || request.getParameter("action").equals("rateexample")){
			try {
				Database db = new Database();
				String id = sess.getId();
				String rating = request.getParameter("rating");
				String songOne = (String)sess.getAttribute("songOne");
				String songTwo = (String)sess.getAttribute("songTwo");
				String removeSong = (String)sess.getAttribute("removeSong");
				if((id != null && rating != null && songOne != null && songTwo != null) && !(id.equals("") || rating.equals("") || songOne.equals("") || songTwo.equals(""))){
					db.addRating(id, songOne, songTwo, rating,request.getRemoteAddr(),request.getParameter("action"));
					if(request.getParameter("action").equals("rate")){
						if(removeSong != null && removeSong != ""){
							InfoExtractor extractor = (InfoExtractor)sess.getAttribute("extractor");
							if(extractor != null){
								extractor.removeSong(Integer.parseInt(removeSong));
							}
						}
					}
					//					sess.setAttribute("InfoExtractor", extractor);
				}
			} catch (Exception ex) {
				Logger.getLogger(LoadController.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("Invalidating session for ip: "+request.getRemoteAddr());
				sess.invalidate();
				response.sendRedirect("LoadController");
			}
		}
		response.sendRedirect("LoadController");
		//			request.getRequestDispatcher("LoadController").forward(request, response);
	}
}

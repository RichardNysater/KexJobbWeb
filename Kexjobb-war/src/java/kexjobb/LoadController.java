package kexjobb;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

/**
 * The main controller for the website. Communicates with the InfoExtractor and Database to make sure users complete the example rating
 * before proceeding to the real rating and adds the songs to be played to the user's session.
 * @author Shaan.
 */
@WebServlet(name = "LoadController", urlPatterns = {"/LoadController"})
public class LoadController extends HttpServlet{
	
	/**
	 * doGet is called every time a user is redirected to the LoadController.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String nextLocation = "WEB-INF/main.jsp";
		HttpSession sess = request.getSession(true);
		String[] songs;
		try {
			InfoExtractor extractor = (InfoExtractor)sess.getAttribute("extractor");
			if(extractor == null){
				extractor = new InfoExtractor(request.getRemoteAddr());
			}
			
			Database db = new Database();
			if(db.hasCompletedExample(request.getRemoteAddr())){
				songs = extractor.getSong();
				String completedSongs = songs[2];
				sess.setAttribute("completedSongs",completedSongs);
				String removeSong = songs[3];
				sess.setAttribute("removeSong",removeSong);
			}
			else{
				songs = extractor.getDemoSong();
				String rating = songs[2];
				sess.setAttribute("rating",rating);
				nextLocation = "WEB-INF/example.jsp";
			}
			String songOne = songs[0];
			String songTwo = songs[1];
			if(db.hasFinishedRating(request.getRemoteAddr())){
				nextLocation = "WEB-INF/finished.jsp";
				request.getRequestDispatcher(nextLocation).forward(request, response);
			}
			else if(songOne == null || songTwo == null || songOne.equals("") || songTwo.equals("")){
				System.out.println("Invalidating session for ip: "+request.getRemoteAddr());
				sess.invalidate();
				nextLocation = "WEB-INF/index.jsp";
				request.getRequestDispatcher(nextLocation).forward(request, response);
			}
			else{
				setAttributes(songOne, songTwo, extractor, request, sess);
				request.getRequestDispatcher(nextLocation).forward(request, response);
			}
		} catch (Exception ex) {
			Logger.getLogger(LoadController.class.getName()).log(Level.SEVERE, null, ex);
			response.sendRedirect(nextLocation);
		}
		
	}
	
	/**
	 * Sets the attributes for the current session.
	 * @param songOne First song to be played.
	 * @param songTwo Second song to be played.
	 * @param extractor The InfoExtractor for this session.
	 * @param request The HttpServlet.
	 * @param sess  The current session.
	 */
	private void setAttributes(String songOne, String songTwo, InfoExtractor extractor, HttpServletRequest request, HttpSession sess){
		sess.setAttribute("songOne", songOne);
		sess.setAttribute("songTwo", songTwo);
		System.out.println(request.getHeader("user-agent") + " - "+request.getRemoteAddr());
		sess.setAttribute("songOneUrl", extractor.getUrl(songOne));
		sess.setAttribute("songTwoUrl", extractor.getUrl(songTwo));
		sess.setAttribute("extractor", extractor);
	}
}

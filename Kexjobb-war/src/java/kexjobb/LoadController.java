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
 * @author Shaan
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
		String nextLocation = "main.jsp";
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
			}
			else{
				songs = extractor.getDemoSong();
				String rating = songs[2];
				sess.setAttribute("rating",rating);
				nextLocation = "example.jsp";
			}
			String songOne = songs[0];
			String songTwo = songs[1];
			if(songOne == null && songTwo == null){
				nextLocation = "finished.jsp";
			}
			else{
				setAttributes(songOne, songTwo, extractor, request, sess);
			}
		} catch (Exception ex) {
			Logger.getLogger(LoadController.class.getName()).log(Level.SEVERE, null, ex);
		}
		request.getRequestDispatcher(nextLocation).forward(request, response);
	}
	
	/**
	 * Sets the attributes for the current session
	 * @param songOne First song to be played
	 * @param songTwo Second song to be played
	 * @param extractor The InfoExtractor for this session
	 * @param request The HttpServlet
	 * @param sess  The current session
	 */
	private void setAttributes(String songOne, String songTwo, InfoExtractor extractor, HttpServletRequest request, HttpSession sess){
		sess.setAttribute("songOne", songOne);
		sess.setAttribute("songTwo", songTwo);
		System.out.println(request.getHeader("user-agent"));
		if(request.getHeader("user-agent").contains("Firefox")){
			sess.setAttribute("songOneUrl", extractor.getUrlFirefox(songOne));
			sess.setAttribute("songTwoUrl", extractor.getUrlFirefox(songTwo));
		}
		else{
			sess.setAttribute("songOneUrl", extractor.getUrl(songOne));
			sess.setAttribute("songTwoUrl", extractor.getUrl(songTwo));
		}
		sess.setAttribute("extractor", extractor);
	}
}

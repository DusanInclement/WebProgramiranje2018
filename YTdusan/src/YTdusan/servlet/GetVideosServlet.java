package YTdusan.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.User.UserType;
import YTdusan.tools.UserLogCheck;


public class GetVideosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		ArrayList<Video> videos= null;
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		System.out.println("ALOOO");
		if(loggedInUser!= null) {
			System.out.println(loggedInUser.getUserName()+" - loggedInUser");
			if(loggedInUser.getUserType() == UserType.ADMIN && loggedInUser.getBlocked()!=true) {
				videos=VideoDAO.publicVideos();
			}
			else {
				videos =VideoDAO.publicVideos();
			}
		}
		else {
			videos =VideoDAO.publicVideos();
		}
		ArrayList<User> topSixChannels = null;
		topSixChannels = UserDAO.getTopSixChannels();
		Map<String, Object> data = new HashMap<>();
		data.put("topSixChannels", topSixChannels);
		data.put("videos", videos);
		data.put("loggedInUser", loggedInUser);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}
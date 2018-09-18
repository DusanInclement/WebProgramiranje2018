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

import YTdusan.dao.CommentDAO;
import YTdusan.dao.SubDAO;
import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Comment;
import YTdusan.model.Sub;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.model.Video;
import YTdusan.tools.UserLogCheck;


public class SubServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Sub> subs = null;
		System.out.println("HELLLLLLLLLLOOOOOOOOOOOOO");
		
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		if (loggedInUser != null) {
			String userName = loggedInUser.getUserName();
			subs = SubDAO.get(userName);
			
			
			Map<String, Object> data = new HashMap<>();
			data.put("subs", subs);
			data.put("logged", userName);
			System.out.println(data);
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("Zavrseno ucitavanje video: " +jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
			
			
		}
		else {
			System.out.println("NEMMAAAAAAAA");
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mainUser = request.getParameter("mainUser");
		String subscriber = request.getParameter("subscriber");
		
		boolean status = SubDAO.subscribe(mainUser,subscriber);
		
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
		
		
	}
	
protected void doPost2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mainUser = request.getParameter("mainUser");
		String subscriber = request.getParameter("subscriber");
		
		System.out.println(mainUser + " "  + subscriber);
		
		/*boolean status = SubDAO.Unsubscribe(mainUser,subscriber);
		
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);*/
		
		
		
		
	}
}


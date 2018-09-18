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
import com.sun.org.apache.xpath.internal.operations.NotEquals;

import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.model.Video;
import YTdusan.tools.UserLogCheck;


public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		String parameter = request.getParameter("parameter");
		ArrayList<Video> videos = new ArrayList<>();
		ArrayList<User> users = new ArrayList<>();
		
		
		if(loggedInUser == null){
			System.out.println("paramUser");
			videos = VideoDAO.search(parameter);
			users = UserDAO.search(parameter);
		}else if(loggedInUser.getUserType().toString().equals("ADMIN")){
			
			System.out.println("paramUser");
			videos = VideoDAO.searchAll(parameter);
			users = UserDAO.search(parameter);			
			
		}
		else{
			
			System.out.println("paramUser");
			videos = VideoDAO.search(parameter);
			users = UserDAO.search(parameter);			
		}
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);
		data.put("users",users);
		
		System.out.println("USERIIIII iz serach " + users);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Search: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}


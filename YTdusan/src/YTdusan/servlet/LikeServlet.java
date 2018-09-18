package YTdusan.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import YTdusan.dao.LikeDAO;
import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Like;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.User.UserType;
import YTdusan.tools.DateConverter;
import YTdusan.tools.UserLogCheck;


public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		
		if (loggedInUser !=null) {
			String userName = loggedInUser.getUserName();
			System.out.println(userName +  "OVOVOVOVOVOVOVOOVOVOVOV");
			ArrayList<Like> likes = null;
			
			
			likes = LikeDAO.getLikesUser(userName);
			System.out.println(likes);
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("like", likes);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}
		
		
		
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String primer = request.getParameter("primer");
		String owner = request.getParameter("owner");
		String videoId = request.getParameter("videoId");
		Date newDate = new Date();
		String myNewDate = DateConverter.dateToStringForWrite(newDate);		
		System.out.println(primer+ " == "+ "1");
		
		
		if(primer.equals("1")){
			System.out.println("usao u if");
			boolean status = LikeDAO.addVideoLike(myNewDate,owner,videoId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}else if (primer.equals("2")) {
			
			
			boolean status = LikeDAO.removeVideoLike(owner,videoId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
			
			
			
		}else if(primer.equals("3")){
			
			
			boolean status = LikeDAO.updateVideoLikeToLike(owner,videoId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}else if(primer.equals("4")){
			
			boolean status = LikeDAO.addVideoDisLike(myNewDate,owner,videoId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
			
			
		}else if(primer.equals("5")){
			boolean status = LikeDAO.removeVideoDisLike(owner,videoId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
			
			
		}else if(primer.equals("6")){
			
			boolean status = LikeDAO.updateVideoLikeToDisLike(owner,videoId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
}
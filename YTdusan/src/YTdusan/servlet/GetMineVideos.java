package YTdusan.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Sub;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.User.UserType;
import YTdusan.tools.UserLogCheck;



public class GetMineVideos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		
		String userName = request.getParameter("userName");
		
		ArrayList<Video> videos= null;
		ArrayList<Sub> subs = null;
		ArrayList<User> usersSub = null;
		User users= null;
		
		
		System.out.println(loggedInUser);
		
		if(loggedInUser == null){
			videos=VideoDAO.searchMinePublic(userName);
		}else if (loggedInUser.getUserType().toString().equals("ADMIN")){
			videos=VideoDAO.searchMine(userName);
		}else if(!loggedInUser.getUserName().equals(userName)){
			videos=VideoDAO.searchMinePublic(userName);
		}else{
			videos=VideoDAO.searchMine(userName);
			
		}
		
			
		
		users=UserDAO.get(userName);
		subs = UserDAO.getSubs(userName);
		int numOfSubs = VideoDAO.numbeOfSubs(userName);
		
		System.out.println(numOfSubs + " eeeeeheeeeej");
		if(!subs.isEmpty()){
			
			usersSub = UserDAO.getUserSubs(subs);	
			System.out.println("subs = " + usersSub);
			
		}
		
		
	
		
		
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("videos", videos);
		data.put("users", users);
		data.put("subs", usersSub);
		data.put("loggedInUser", loggedInUser);
		data.put("numOfSubs", numOfSubs);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " +jsonData);
		System.out.println(loggedInUser + "asdlkasjdlkjsalkdjlasjdlj");

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}
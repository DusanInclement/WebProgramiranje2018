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
import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.org.apache.xpath.internal.operations.NotEquals;

import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.model.Video;
import YTdusan.tools.UserLogCheck;


public class SherSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		String owner = request.getParameter("owner");
		String videoName = request.getParameter("videoName");
		String nubOfV = request.getParameter("numberOfViews");
		String date = request.getParameter("date");
		String sortBy = request.getParameter("sortBy");
		String how = request.getParameter("how");
		String numberOfViews = null;
		System.out.println(owner + " " + videoName + " " + sortBy + " " + how);	
		ArrayList<Video> videos = null;
		
		if (nubOfV == "") {
			numberOfViews = "LIKE '%%'";
		}else if (nubOfV != ""){
			numberOfViews = "= "+nubOfV+"";
		}
		
		System.out.println(owner + " " + videoName + " " +numberOfViews + " " + date + " " + sortBy + " " + how);
		
		
		if(loggedInUser == null){
			videos = VideoDAO.sherSearchpublic(owner,videoName,numberOfViews,date,sortBy,how);
			
		}
		
		else if(loggedInUser.getUserType().toString().equals("ADMIN")){
			videos = VideoDAO.sherSearchAdmin(owner,videoName,numberOfViews,date,sortBy,how);
		}else{
			videos = VideoDAO.sherSearchpublic(owner,videoName,numberOfViews,date,sortBy,how);
			
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);		
	
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Search: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
		
	}}
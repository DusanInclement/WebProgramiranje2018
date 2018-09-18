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

import YTdusan.dao.CommentDAO;
import YTdusan.dao.LikeDAO;
import YTdusan.dao.SubDAO;
import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Comment;
import YTdusan.model.Like;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.model.Video;
import YTdusan.model.Video.Visibility;
import YTdusan.tools.DateConverter;
import YTdusan.tools.UserLogCheck;


public class VideoSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String videoId = request.getParameter("videoId");
		
		Video video = VideoDAO.get(videoId);
		
		Map<String, Object> data = new HashMap<>();
		data.put("video", video);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
		
		
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		String videoUrl = request.getParameter("videoUrl");
		String pictureUrl = request.getParameter("pictureUrl");
		String videoName = request.getParameter("videoName");
		String description = request.getParameter("description");
		String visibilityST = request.getParameter("visibility");
		Visibility visibility = null;
		if (visibilityST.equals("PUBLIC") ){
			visibility = Visibility.PUBLIC;
		}else if (visibilityST.equals("PRIVATE")){
			visibility = Visibility.PRIVATE;
		}else if (visibilityST.equals("UNLISTED")) {
			visibility = Visibility.UNLISTED;
		}
		Boolean blocked = false;
		String comments = request.getParameter("comments");
		Boolean commentEnabled = null;
		if (comments.equals("Yes")) {
			commentEnabled = true;
		}else if (comments.equals("No")) {
			commentEnabled = false;
		}
		String rating = request.getParameter("rating");
		Boolean ratingEnabled = null;
		if (rating.equals("Yes")) {
			ratingEnabled = true;
		}else if (rating.equals("No")) {
			ratingEnabled = false;
		}
		int numberOfLikes = 0;
		int numberOfDislikes = 0;
		int views = 0;
		Date newDate = new Date();
		String datePosted = DateConverter.dateToStringForWrite(newDate);
		Boolean deleted = false;
		
		System.out.println(videoUrl+" "+pictureUrl+" "+videoName+" "+description
				+" "+visibility+" "+blocked+" "+commentEnabled+" "+ratingEnabled+" "+numberOfLikes
				+" "+numberOfDislikes+" "+views+" "+datePosted+" "+loggedInUser+" "+deleted);
		
		
		Video uploadVideo = new Video(0, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentEnabled, 
												ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, loggedInUser, deleted);		
		
		
		
		boolean status = VideoDAO.addVideo(uploadVideo);
	
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
		

		}}
		
		
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
import YTdusan.dao.LikeDAO;
import YTdusan.dao.SubDAO;
import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Comment;
import YTdusan.model.Like;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.model.Video;
import YTdusan.tools.UserLogCheck;


public class WatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		
		
		
		String videosId = request.getParameter("videoId");
		System.out.println(videosId + " iz watch servleta");
		ArrayList<Comment> comments = null;
		ArrayList<Video> videosDesno = null;
		ArrayList<Like> likeComm = null;
		boolean view = VideoDAO.plus(videosId);
		Video video = null;
		
		video = VideoDAO.get(videosId);
		User ownerVideo = video.getOwner();
		comments = CommentDAO.searchMine(videosId);
		videosDesno = VideoDAO.publicVideos();
		likeComm = LikeDAO.getLikeComm(videosId);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("loggedInUser", loggedInUser);
		data.put("likeComm",likeComm);
		data.put("videoDesno", videosDesno);
		data.put("video", video);
		data.put("ownerVideo", ownerVideo);
		data.put("comments", comments);
		System.out.println(comments + "comments");
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commentId = request.getParameter("commentId");
		
		boolean status = CommentDAO.deleteComment(commentId);
		System.out.println(commentId);
		
		
		
	}
}

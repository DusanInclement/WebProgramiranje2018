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

import YTdusan.dao.LikeDAO;
import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Like;
import YTdusan.model.Sub;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.User.UserType;
import YTdusan.tools.UserLogCheck;



public class CommentLikeDislikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		
		
		
		
		String videoIdU = request.getParameter("videoIdU");
		ArrayList<Like> likeComm = null;
		
		likeComm = LikeDAO.getLikeComm(videoIdU);
		
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("loggedInUser", loggedInUser);
		data.put("likeComm", likeComm);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
		
		
		
		
		
		
		
	}}
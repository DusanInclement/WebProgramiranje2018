package YTdusan.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import YTdusan.dao.CommentDAO;
import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Comment;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.tools.DateConverter;

/**
 * Servlet implementation class RegisterServlet
 */
public class UpdateCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String videoId = request.getParameter("videoId");
		
		boolean status = VideoDAO.deleteVideo(videoId);
		System.out.println(status);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String commentId = request.getParameter("commentId");
		String text = request.getParameter("text");
		
		boolean status = CommentDAO.updateComment(text, commentId);
		System.out.println(commentId + "  " + text);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
	}}

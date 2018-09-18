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
import YTdusan.model.Comment;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.tools.DateConverter;

/**
 * Servlet implementation class RegisterServlet
 */
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commentId = request.getParameter("commentId");
		
		Comment comment = CommentDAO.get(commentId);
		Map<String, Object> data = new HashMap<>();
		data.put("comment", comment);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		String owner = request.getParameter("owner");
		String videoId = request.getParameter("videoId");
		Date newDate = new Date();
		String myNewDate = DateConverter.dateToStringForWrite(newDate);
		
		
		
		
		boolean status = CommentDAO.addComment(text,owner,videoId,myNewDate);
		
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
		
		
		
		
		
		
	}}

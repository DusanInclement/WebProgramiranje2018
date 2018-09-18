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
import YTdusan.dao.UserDAO;
import YTdusan.dao.VideoDAO;
import YTdusan.model.Like;
import YTdusan.model.User;
import YTdusan.model.Video;
import YTdusan.model.User.UserType;
import YTdusan.tools.DateConverter;
import YTdusan.tools.UserLogCheck;


public class LikeCommServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String primer = request.getParameter("primer");
		String owner = request.getParameter("owner");
		String commentId = request.getParameter("commentId");
		Date newDate = new Date();
		String myNewDate = DateConverter.dateToStringForWrite(newDate);		
		System.out.println(primer+ " ; " + commentId + " ; " + owner);
		
		if(primer.equals("1")){
			boolean status = CommentDAO.addCommLike(myNewDate,owner,commentId);
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if (primer.equals("2")) {
			
			
			boolean status = CommentDAO.removeCommentLike(owner,commentId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
			
			
			
		}else if(primer.equals("3")){
			
			
			boolean status = CommentDAO.updateCommentLikeToLike(owner,commentId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}else if(primer.equals("4")){
			
			boolean status = CommentDAO.addCommentDisLike(myNewDate,owner,commentId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
			
			
		}else if(primer.equals("5")){
			boolean status = CommentDAO.removeCommentDisLike(owner,commentId);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
			
			
		}else if(primer.equals("6")){
			
			boolean status = CommentDAO.updateCommentLikeToDisLike(owner,commentId);
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
	

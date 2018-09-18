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

import YTdusan.dao.UserDAO;
import YTdusan.model.User;
import YTdusan.model.User.UserType;
import YTdusan.tools.DateConverter;

/**
 * Servlet implementation class RegisterServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String primer = request.getParameter("primer");
		String blString = request.getParameter("blocked");
		boolean blocked = true;
		if (blString.equals("true")){
			blocked = true;
		}
		else if (blString.equals("false")){
			blocked = false;
		}
		String userType = request.getParameter("userType");
		
		
		if(primer.equals("1")){
			
		
			
			
			boolean status = UserDAO.blocked(userName,blocked);
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}else if(primer.equals("2")){
			
			System.out.println(userName+" "+userType);
			
			boolean status = UserDAO.changeType(userName,userType);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
		else if(primer.equals("3")){
			
			boolean status = UserDAO.deleteUser(userName);
			Map<String, Object> data = new HashMap<>();
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("ovo je jsonData "+jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
		
		
		
		
		
		
		
	}}
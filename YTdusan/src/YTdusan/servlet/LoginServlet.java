package YTdusan.servlet;

import java.io.IOException;
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
import YTdusan.tools.UserLogCheck;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		
		Map<String, Object> data = new HashMap<>();
		data.put("user", loggedInUser);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		User user = null;
		boolean status = true;
		
		
			
			user = UserDAO.get(userName);
			
			if (user == null){status = false;}
			else if  (!user.getPassword().equals(password)){status = false;}
			else {
				HttpSession session = request.getSession();				
				session.setAttribute("loggedInUser", user);
				status = true;
			}
			
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		data.put("user", user);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data + "pozdrav iz posta");
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}


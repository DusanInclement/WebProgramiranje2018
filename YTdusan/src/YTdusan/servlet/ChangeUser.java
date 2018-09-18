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
public class ChangeUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		
		User user = UserDAO.get(userName);
		
		Map<String, Object> data = new HashMap<>();
		data.put("user", user);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String profileUrl = request.getParameter("profileUrl");
		
		
		System.out.println("iz CHANGEEEEEEEEEEE   " + userName );
		
		String ok = UserDAO.changeUser(userName,password,firstName,lastName,email,profileUrl); 
		
		
		System.out.println("String koji vraca DAO " + ok );
		
		Map<String, Object> data = new HashMap<>();
		data.put("ok", ok);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

package YTdusan.tools;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import YTdusan.dao.UserDAO;
import YTdusan.model.User;

public class UserLogCheck {
	
	public static User findCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser!=null) {
			User tempUser = UserDAO.get(loggedInUser.getUserName());
			if(tempUser==null) {
				request.getSession().invalidate();
			}
			loggedInUser = tempUser;
		}
		return loggedInUser;
	}
}
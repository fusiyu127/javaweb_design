

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class changeSession
 */
@WebServlet("/changeSession")
public class changeSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		if(username==null) {
			request.getSession().removeAttribute("cur_user");
		}else {
			request.getSession().setAttribute("cur_user", username);
			System.out.println(request.getSession().getAttribute("cur_user"));
		}
	}

}

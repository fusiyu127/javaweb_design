import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/api/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Au	2to-generated method stub
		//解决中文乱码问题
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");  
		
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		DBUtils dbUtils = null;
		
		try {
			dbUtils = new DBUtils();
			ResultSet resultSet = dbUtils.doSelect("select * from users where username=\'"+username+"\' and password=\'"+password+"\'");
			String result = dbUtils.resultSetToJson(resultSet);
			request.getSession().setAttribute("cur_user", username);
			out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection();
		}
	}
	
}

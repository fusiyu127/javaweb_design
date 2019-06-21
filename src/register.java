import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class register
 */
@WebServlet("/api/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决中文乱码
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "insert into users values(null,\""+username+"\",\""+password+"\")";
			Boolean result = dbUtils.excute(sql);
			sql = "insert into userinfo values(null,'default.jpg','','',1,'')";
			boolean rs2 = dbUtils.excute(sql);
			if(result && rs2){
				out.print("true");
			}else {
				out.print("false");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbUtils.closeConnection();
		}
	}

}

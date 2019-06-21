import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class deleteUser
 */
@WebServlet("/api/deleteUser")
public class deleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "delete from users where id = "+id;
			boolean rq = dbUtils.excute(sql);
			sql = "delete from userinfo where id = "+id;
			boolean rq2 = dbUtils.excute(sql);
			if(rq) {
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

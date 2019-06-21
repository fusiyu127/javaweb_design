

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class changeUserInfo
 */
@WebServlet("/api/changeUserInfo")
public class changeUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String username = request.getParameter("username");
		String tel = request.getParameter("tel");
		String sex = request.getParameter("sex");
		String introduction = request.getParameter("introduction");
		DBUtils dbUtils = new DBUtils();
		PrintWriter out = response.getWriter();
		
		try {
			String sql_info = "update userinfo set tel=\""+tel+"\",sex="+sex+",introduction=\""+introduction+"\" where id="+id;
			boolean rq1 = dbUtils.excute(sql_info);
			String sql_name = "update users set username =\""+username+"\" where id="+id;
			boolean rq2 = dbUtils.excute(sql_name);
			if(rq1 && rq2) {
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

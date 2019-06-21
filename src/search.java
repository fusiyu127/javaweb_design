

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class search
 */
@WebServlet("/api/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		String searchInfo = request.getParameter("searchInfo");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "SELECT id,title,content,feature  FROM articles  where content like \"%"+searchInfo+"%\" order by created DESC";
			ResultSet resultSet = dbUtils.doSelect(sql);
			String result = dbUtils.resultSetToJson(resultSet);
			out.print(result);
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbUtils.closeConnection();
		}
	}

}

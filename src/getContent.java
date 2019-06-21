

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getContent
 */
@WebServlet("/api/getContent")
public class getContent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决中文乱码问题
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");  
		
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "select articles.content,articles.title,articles.created,articles.likes,users.username\n" + 
					"from articles\n" + 
					"inner join users on users.id = articles.author_id\n" + 
					"where articles.id =" + id;
			ResultSet resultSet = dbUtils.doSelect(sql);
			String content = dbUtils.resultSetToJson(resultSet);
			out.print(content);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection();
		}
	}

}

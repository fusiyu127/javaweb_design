

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getUserArticle
 */
@WebServlet("/api/getUserArticle")
public class getUserArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//解决中文乱码问题
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");  
				
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "SELECT id,title,content,feature FROM blog.articles where author_id="+id;
			ResultSet resultSet = dbUtils.doSelect(sql);
			String articles = dbUtils.resultSetToJson(resultSet);
			out.print(articles);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection();
		}
	}

}


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getArticle
 */
@WebServlet("/api/getArticle")
public class getArticle extends HttpServlet {
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
		int page = Integer.parseInt(request.getParameter("page"));
		int offset = (page-1) * 4;
		String isHome = request.getParameter("isHome");
		DBUtils dbUtils = new DBUtils();
		String articles = null;
		String result = null;
		
		try {
			String sql = "SELECT id,title,content,feature  FROM articles order by created DESC limit "+offset+", 4" ;
			ResultSet resultSet = dbUtils.doSelect(sql);
			articles = dbUtils.resultSetToJson(resultSet);
			result = articles;
			if(isHome != null) {
				String sql1 = "SELECT count(0) as nums FROM blog.articles;";
				ResultSet resultSet1 = dbUtils.doSelect(sql1);
				if(resultSet1.next()) {
					float nums = Integer.parseInt(resultSet1.getString("nums"));
					int totalPages = (int) Math.ceil(nums/4);
					result = "{\"totalpage\":"+totalPages+",\"articles\":"+articles+"}";
				}
			}
			out.print(result);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			dbUtils.closeConnection();
		}
		
		//返回总页数
		if(isHome != null) {
			
		}
	}

}

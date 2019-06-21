

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getCollection
 */
@WebServlet("/api/getCollection")
public class getCollection extends HttpServlet {
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
		String userId = request.getParameter("id");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "select collection from userinfo where id="+userId;
			ResultSet resultSet = dbUtils.doSelect(sql);
			String colString = null;
			if(resultSet.next()) {
				colString = resultSet.getString(1);
				colString = colString.substring(1, colString.length()-1);
			}
			sql = "SELECT id,title,content,feature  FROM blog.articles where id in ("+colString+")";
			resultSet = dbUtils.doSelect(sql);
			String collection = dbUtils.resultSetToJson(resultSet);
			out.print(collection);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbUtils.closeConnection();
		}
	}

}

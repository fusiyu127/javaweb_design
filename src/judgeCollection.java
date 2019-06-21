import java.io.IOException;
import java.io.PrintWriter;import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class judgeCollection
 */
@WebServlet("/api/judgeCollection")
public class judgeCollection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		String articleId = request.getParameter("articleId");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "select collection from userinfo where id="+userId;
			ResultSet resultSet = dbUtils.doSelect(sql);
			String colString = null;
			String[] colArray = null ;
			
			if(resultSet.next()) {
				colString = resultSet.getString(1);
				colString = colString.substring(1, colString.length()-1);
				colArray = colString.split(",");
			}
			List<String> colList = new ArrayList<String>(Arrays.asList(colArray));	
			if(colList.contains(articleId)) {
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

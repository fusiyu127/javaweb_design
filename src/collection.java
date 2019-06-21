

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class collection
 */
@WebServlet("/api/collection")
public class collection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		String articleId = request.getParameter("articleId");
		String how = request.getParameter("how");
		DBUtils dbUtils = new DBUtils();
		
		try {
			String sql = "select collection from userinfo where id="+userId;
			String count_sql = "select likes from articles where id = "+ articleId;
			ResultSet resultSet = dbUtils.doSelect(sql);
			String colString = null;
			int likes = 0;
			
			String[] colArray = null ;
			if(resultSet.next()) {
				colString = resultSet.getString(1);
				colString = colString.substring(1, colString.length()-1);
				colArray = colString.split(",");
			}
			
			ResultSet resultSet2 = dbUtils.doSelect(count_sql);
			if(resultSet2.next()) {
				likes = Integer.parseInt(resultSet2.getString(1));
			}
			List<String> colList = new ArrayList<String>(Arrays.asList(colArray));
			if(how.equals("add")) {
				colList.add(articleId);
				likes++;
			}
			if(how.equals("delete")) {
				colList.remove(articleId);
				likes--;
			}
			colString = colList.toString();
			colString = colString.replace(" ", ""); 
			sql = "update userinfo set collection = \""+colString+"\" where id = "+userId;
			boolean rq1 = dbUtils.excute(sql);
			sql = "update articles set likes = "+likes+" where id = "+articleId;
		
			boolean rq2 = dbUtils.excute(sql); 
			if(rq1 && rq2) {
				out.print("true");
			}else {
				out.print("false");
			}
			//System.out.println(colList.get(4));
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbUtils.closeConnection();
		}
		
		
	}

}

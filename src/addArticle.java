

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addArticle
 */
@WebServlet("/api/addArticle")
public class addArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//解决中文乱码
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content =request.getParameter("content");
		content = content.replaceAll("\"","\'");
		DBUtils dbUtils = new DBUtils();
		try {
			String sql = "insert into articles values(null,'default.jpg',"+id+",\""+title+"\",\""+content+"\",null,0)";
			Boolean result = dbUtils.excute(sql);
			if(result) {
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

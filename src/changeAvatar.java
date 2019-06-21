import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.jasper.tagplugins.jstl.core.Out;


@WebServlet("/api/changeAvatar")
public class changeAvatar extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 private static String Image_Path = "/opt/tomcat/webapps/blog/image/user/";
	 private static String Image_name = "";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	    if(isMultipart){
	      //创建可设置的磁盘节点工厂
	      DiskFileItemFactory factory = new DiskFileItemFactory();
	      //获取请求的上下文信息
	      ServletContext servletContext = request.getServletContext();
	      //缓存目录，每个服务器特定的目录
	      File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	      //设置服务器的缓存目录
	      factory.setRepository(repository);
	      //ServletFileUpload 对象的创建需要依赖于 FileItemFactory 
	      //工厂将获得的上传文件 FileItem 对象保存至服务器硬盘，即 DiskFileItem 对象。
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      try {
	        //解析即被包装之后的 HttpServletRequest对象，既是分离文本表单和上传文件（http请求会被包装为HttpServletRequest）
	        List<FileItem> items = upload.parseRequest(request);
	        for(FileItem item:items){
	          String fieldName = item.getFieldName();  
	          String fileName = item.getName();
	          String contentType = item.getContentType();
	          boolean isInMemory = item.isInMemory();
	          long sizeInBytes = item.getSize();
	          //实例化一个文件
	          //request.getRealPath(获取真实路径)
	          String path = request.getRealPath("/")+"image/user/"+fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
	          Image_name = fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
	          File file = new File(Image_Path+Image_name);	         
	          item.write(file);
	          out.print("true");
	        }
	      } catch (FileUploadException e) {
	        e.printStackTrace();
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	    
	    DBUtils dbUtils = new DBUtils();
	    try {
		String sql = "update userinfo set avatar = \""+Image_name+"\" where id = "+id;
		dbUtils.excute(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbUtils.closeConnection();
		}
	}
}



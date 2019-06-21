import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/api/*")
public class filter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		//强制转换
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
				
		//获取资源路径
		String uri = req.getRequestURI();
				
		//访问未登录用户可以访问的资源
		if(uri.contains("login")||uri.contains("getArticle")||uri.contains("getContent")||uri.contains("register")||uri.contains("search")) {
			chain.doFilter(request, response);
		}else {
			String cur_user = (String) req.getSession().getAttribute("cur_user");
			if(cur_user != null) {
				chain.doFilter(request, response);
			}else {
				PrintWriter out = res.getWriter();
				out.print("false");
			}
		}
		
	}

}

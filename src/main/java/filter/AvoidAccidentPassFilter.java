package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止意外用户没有登录直接在地址栏输入地址访问到不能访问的页面（-------测试中-------------！！！！！！！！！！！！！！！！！！
 * @author Florence
 */
@WebFilter(dispatcherTypes = {DispatcherType.ASYNC, DispatcherType.REQUEST, DispatcherType.FORWARD})
public class AvoidAccidentPassFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain){
        try {
            HttpServletRequest request =(HttpServletRequest) req;
            String uri = request.getRequestURI();
            //包含添加博客页面和我是设置页面
            if(uri.contains("/AvoidAccidentPassServlet")){
                //说明登录了
                if(request.getCookies()!=null){
                    System.out.println("正确传输");
                    chain.doFilter(req,resp);
                    return;
                }
                System.out.println("重定向");
                request.getRequestDispatcher(request.getContextPath()+"/page/blogs.html");
                System.out.println(request.getContextPath()+"/page/blogs.html");
                return;
            }
            System.out.println("成功传输");
            chain.doFilter(req, resp);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig config) {

    }

}

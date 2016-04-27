package co.com.qabox.soainvportal.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.com.qabox.soainvportal.login.LoginController;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/inv/*")
public class LoginFilter implements Filter {

	@Inject
	LoginController loginBean;

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpRequest.setCharacterEncoding("UTF-8");
		
//		System.out.println("########################################");
//		System.out.print("Path requested: " + httpRequest.getServletPath() + " - ");
		
		if (httpRequest.getServletPath().equals("login.xhtml")
				|| httpRequest.getServletPath().contains(
						"/javax.faces.resource")) {
//			System.out.println("Recurso Permitdo");
			chain.doFilter(request, response);
		} else {
//			System.out.println("Recurso Denegado");
			
//			if ( loginBean != null )
//				System.out.println("LoginBean: " + loginBean.isLogged());

			if (loginBean == null || !loginBean.isLogged()) {
//				System.out.println("Se redirige al login.xhmtl");
				httpResponse.sendRedirect(httpRequest.getContextPath()
						+ "/login.xhtml");
			} else {
//				System.out.println("Se realiza el doFilter y ya");
				chain.doFilter(request, response);
			}
		}
//		System.out.println("########################################");

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

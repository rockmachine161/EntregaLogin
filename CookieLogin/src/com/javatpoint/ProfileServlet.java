package com.javatpoint;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ProfileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		request.getRequestDispatcher("link.html").include(request, response);
		Cookie cook[]=request.getCookies();
		if(cook!=null){
			String name=cook[0].getValue();
			if(!name.equals("") || name != null){
				out.print("<b>Bienvenido a su Perfil</b>");
			}
		}else{
			out.print("Por favor, logueate primero");
			request.getRequestDispatcher("login.html").include(request, response);
		}
		out.close();
	}

}

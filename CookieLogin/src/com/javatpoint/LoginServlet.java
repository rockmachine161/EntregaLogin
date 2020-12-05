package com.javatpoint;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Utiles.Password;

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Password pass = new Password();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		/** Se realizan cambios para securizar los datos */
		
		request.getRequestDispatcher("link.html").include(request, response);
		
		try {
			pass.creaPass("admin".getBytes());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String name = request.getParameter("name");
		/** Teniendo en cuenta IDS15-J se convierte el string a una cadena de bytes*/
		
		byte[] password = request.getParameter("password").getBytes();
		
		/** Considerando MSC03-J, IDS15-J y MSC62-J se guardan las contraseñas hasheadas */
		try {
			if(pass.checkPassword(password)){
				out.print("Hey! Ahora estás dentro");
				out.print("<br>Welcome, "+name);
				
				/** Considerando FIO52-J. No se debe almacenar información sensible sin encriptar */
				
				Cookie cook=new Cookie("name",pass.token());
				response.addCookie(cook);
			}else{
				out.print("ERROR, Usuario o Password incorrectos!");
				request.getRequestDispatcher("login.html").include(request, response);
			}
		} catch (Exception e) {
			out.print("ERROR, algo ha ido mal!");			
		}finally {
			out.close();
		}
	}

}

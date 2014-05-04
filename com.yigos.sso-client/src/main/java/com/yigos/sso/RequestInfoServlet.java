package com.yigos.sso;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestInfoServlet extends HttpServlet {  
    
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws IOException, ServletException {  
         response.setContentType("text/html");  
         PrintWriter out = response.getWriter();  
        //header  
         Enumeration<?> headerNames = request.getHeaderNames();  
         out.println("<B>*********[1].begin print headerNames************</B><p>");  
        while (headerNames.hasMoreElements()) {  
             String name = (String) headerNames.nextElement();  
             String value = request.getHeader(name);  
             out.println(name + " = " + value + "<p>");  
         }  
         out.println("<B>*********[1].end print headerNames************</B><p><p>");  
          
        //attributeNames  
         out.println("<B>*********[2].begin print attributeNames************</B><p>");  
         Enumeration<?> attributeNames = request.getAttributeNames();  
        while (attributeNames.hasMoreElements()) {  
             String name = (String) attributeNames.nextElement();  
             String value = (String)request.getAttribute(name);  
             out.println(name + " = " + value + "<p>");  
         }  
         out.println("<B>*********[2].end print attributeNames************</B><p><p>");  
          
        //parameterNames  
         out.println("<B>*********[3].begin print parameterNames************</B><p>");  
         Enumeration<?> parameterNames = request.getParameterNames();  
        while (parameterNames.hasMoreElements()) {  
             String name = (String) parameterNames.nextElement();  
             String value = request.getParameter(name);  
             out.println(name + " = " + value + "<p>");  
         }  
         out.println("<B>*********[3].end print parameterNames************</B><p><p>");  
          
        //session  
         out.println("<B>*********[4].begin print session AttributeNames************</B><p>");  
         HttpSession session = request.getSession();  
         Enumeration<?> sAttributeNames = session.getAttributeNames();  
        while (sAttributeNames.hasMoreElements()) {  
             String name = (String) sAttributeNames.nextElement();  
             Object value = session.getAttribute(name);  
             out.println(name + " = " + value + "<p>");  
         }  
         out.println("<B>*********[4].end print session attributeNames************</B><p><p>");  
          
        //cookie  
         out.println("<B>*********[4].begin print Cookie************</B><p><p>");  
         Cookie[] cookie = request.getCookies();  
        for(int i=0; i< cookie.length; i++) {  
             String name = cookie[i].getName();  
             String value = cookie[i].getValue();  
             out.println(name + " = " + value + "<p>");  
         }     
         out.println("<B>*********[4].end print Cookie************</B><p><p>");  
          
     }  
  
} 
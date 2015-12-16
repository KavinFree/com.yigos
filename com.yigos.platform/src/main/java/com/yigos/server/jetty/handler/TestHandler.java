package com.yigos.server.jetty.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class TestHandler extends AbstractHandler {
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>Hello World</h1>");
		response.getWriter().println("<li>Request url: " + target + "</li>");
		response.getWriter().println(
				"<li>Server port: " + request.getServerPort() + "</li>");
	}
/*
	URL fav = this.getClass().getClassLoader().getResource("org/eclipse/jetty/favicon.ico");
    if (fav!=null)
    {
        Resource r = Resource.newResource(fav);
        _favicon=IO.readBytes(r.getInputStream());
    }
    */
}
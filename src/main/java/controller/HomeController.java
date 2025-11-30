package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "HomeController", urlPatterns = "/home")
public class HomeController extends HttpServlet {
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 여기에 사전에 필요한 기능 정의
    	request.getRequestDispatcher("/cert/list").include(request, response);
    	request.getRequestDispatcher("/major/info").include(request, response);
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}

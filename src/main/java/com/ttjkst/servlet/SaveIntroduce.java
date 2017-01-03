package com.ttjkst.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ttjkst.tools.FileTools;

/**
 * Servlet implementation class SaveWordTxt
 */
public class SaveIntroduce extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveIntroduce() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = request.getReader();
		String localWebRoot = System.getProperty("webapp.root");
		HttpSession session = request.getSession();
		String separator = System.getProperty("file.separator");
		String dest = localWebRoot+separator +"temp_intro.html";
		FileTools tool = new FileTools();
		tool.saveInAdress(dest, br);
		session.setAttribute("tempFileDest_in", dest);
		request.getRequestDispatcher("/saveintor.action").forward(request, response);
	}

}

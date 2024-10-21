package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InitServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Field field = new Field();
		Map<Integer, Sign> fieldData = field.getField();
		List<Sign> data = field.getFieldData();

		session.setAttribute("field", field);
		session.setAttribute("data", data);

		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
}

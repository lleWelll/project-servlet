package com.tictactoe;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class LogicServlet extends HttpServlet {
	int index;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession currentSession = request.getSession();
		Field field = extractField(currentSession);
		index = getSelectedIndex(request);

		if (field.getEmptyFieldIndex() >= 0) {

			if (!field.getField().get(index).equals(Sign.EMPTY)) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
				return;
			}

			field.getField().put(index, Sign.CROSS);
			if (checkWin(request, response)) {
				return;
			}
			if (field.getEmptyFieldIndex() < 0) {
				draw(request, response);
				return;
			}

			if (field.getEmptyFieldIndex() >= 0) {
				field.getField().put(field.getEmptyFieldIndex(), Sign.NOUGHT);
				if (checkWin(request, response)) {
					return;
				}
				if (field.getEmptyFieldIndex() < 0) {
					draw(request, response);
					return;
				}
			}

			List<Sign> data = field.getFieldData();
			currentSession.setAttribute("field", field);
			currentSession.setAttribute("data", data);

			response.sendRedirect("/index.jsp");
		}

	}

	private int getSelectedIndex(HttpServletRequest request) {
		String clickParameter = request.getParameter("click");
		boolean isNumber = clickParameter.chars().allMatch(Character::isDigit);
		return isNumber ? Integer.parseInt(clickParameter) : -1;
	}

	private Field extractField(HttpSession session) {
		return (Field) session.getAttribute("field");
	}

	private boolean checkWin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession currentSession = request.getSession();
		Field field = extractField(currentSession);
		Sign winner = field.checkWin();
		if (Sign.CROSS == winner || Sign.NOUGHT == winner) {
			currentSession.setAttribute("winner", winner);

			List<Sign> data = field.getFieldData();

			currentSession.setAttribute("data", data);

			response.sendRedirect("/index.jsp");
			return true;
		}
		return false;
	}

	private void draw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession currentSession = request.getSession();
		Field field = extractField(currentSession);
		currentSession.setAttribute("draw", true);

		List<Sign> data = field.getFieldData();

		currentSession.setAttribute("data", data);

		response.sendRedirect("/index.jsp");
	}
}

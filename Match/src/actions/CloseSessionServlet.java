package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilsAndData.SessionUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class OpenSessionServlet
 */
@WebServlet("/CloseSessionServlet")
public class CloseSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CloseSessionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String resp = null;

		System.out.println("closing session...");

		String userIdFromSession = SessionUtils.getUserId(request);
		if (userIdFromSession != null) {
			SessionUtils.clearSession(request);
			resp = "true";
		} else {
			throw new ServletException(
					"closing session which was not initiated.");
		}

		Gson gson = new Gson();
		try {
			String jsonResponse = gson.toJson(resp);
			System.out.println(jsonResponse);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}

	}
}

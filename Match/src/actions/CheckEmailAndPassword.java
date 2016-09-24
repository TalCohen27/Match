package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilsAndData.Consts;
import utilsAndData.JDBC;
import utilsAndData.SessionUtils;
import utilsAndData.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class CheckEmailAndPassword
 */
@WebServlet("/checkemailandpassword")
public class CheckEmailAndPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public CheckEmailAndPassword() {
		super();
	}
	
	public void init() throws ServletException {
		Object temp = getServletContext().getAttribute("matchDataJDBCTemplate");
		if (temp != null) {
			jdbc = (JDBC) temp;
		} else {
			throw new ServletException(
					"matchDataJDBCTemplate is not initiated.");
		}
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

		getServletContext().getRequestDispatcher("/Manager").include(request,
				response);

		String passwordParameter = request.getParameter("password");
		String emailParameter = request.getParameter("email");
		String responce = "";
		System.out.println(passwordParameter);

		User user = new User();

		if (passwordParameter == "" || emailParameter == "") {
			responce = "false";
		} else {
			user = jdbc.loginCheck(emailParameter, passwordParameter);
			if (user == null) {
				responce = "Email or password is not correct";
			} else {
				responce = "true";

				getServletContext().getRequestDispatcher("/OpenSessionServlet")
						.include(request, response);
				String userId = SessionUtils.getUserId(request);
				if (userId != null) {
					if (Long.parseLong(userId) == Consts.ADMIN_ID) {
						responce = "admin";
					}
				}
			}
		}

		Gson gson = new Gson();
		try {
			String jsonResponse = gson.toJson(responce);
			System.out.println(jsonResponse);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}
	}
}

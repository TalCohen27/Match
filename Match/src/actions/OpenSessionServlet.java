package actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import utilsAndData.JDBC;
import utilsAndData.SessionUtils;

/**
 * Servlet implementation class OpenSessionServlet
 */
@WebServlet("/OpenSessionServlet")
public class OpenSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Jedis jedis = null;
	private Long userId = null;
	private JDBC matchDataJDBCTemplate = null;

	public OpenSessionServlet() {
		super();
	}

	public void init() throws ServletException {
		Object temp = getServletContext().getAttribute("jedis");
		if (temp != null) {
			jedis = (Jedis) temp;
		} else {
			throw new ServletException("jedis is not initiated.");
		}

		temp = getServletContext().getAttribute("matchDataJDBCTemplate");
		if (temp != null) {
			matchDataJDBCTemplate = (JDBC) temp;
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
		String emailParameter = request.getParameter("email");
		System.out.println(emailParameter);

		SessionUtils.clearSession(request);
		System.out.println("opening session...");
		if (matchDataJDBCTemplate.checkIfEmailExists(emailParameter)) {
			userId = matchDataJDBCTemplate.retrievalOfUserByEmail(
					emailParameter).getId();
		} else {
			userId = jedis.incr("global:id");
		}
		System.out.println("userId is: " + userId);

		// open session
		String userIdFromSession = SessionUtils.getUserId(request);

		if (userIdFromSession == null) {
			request.getSession(true).setAttribute("userId", userId);
			System.out.println("session is opened");
			request.getSession().setAttribute("jedis", jedis);
		}
	}
}

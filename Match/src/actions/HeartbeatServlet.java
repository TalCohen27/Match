package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilsAndData.JDBC;
import utilsAndData.SessionUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/heartbeat")
public class HeartbeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public HeartbeatServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		Object temp = config.getServletContext().getAttribute(
				"matchDataJDBCTemplate");
		if (temp != null) {
			jdbc = (JDBC) temp;
		} else {
			throw new ServletException(
					"matchDataJDBCTemplate is not initiated.");
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Long targetId = null;
		try {
			targetId = Long.parseLong(request.getParameter("targetId"));
		} catch (NumberFormatException nfe) {
			throw new ServletException("Wrong target id, received '"
					+ request.getParameter("targetId") + "'");
		}
		Timestamp lastLogin = null;
		if (targetId != null) {
			lastLogin = jdbc.getLastLogin(targetId);
		}

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		try {
			String jsonResponse = gson.toJson(lastLogin.getTime());
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Long userId = getCurrentUser(request);

		if (userId != null)
			jdbc.updateLastLogin(userId);

	}

	private Long getCurrentUser(HttpServletRequest request)
			throws ServletException {
		String userIdFromSession = SessionUtils.getUserId(request);
		Long userId = null;

		if (userIdFromSession != null) {
			userId = Long.parseLong(userIdFromSession);
		}
		return userId;
	}

}

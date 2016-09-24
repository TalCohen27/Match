package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilsAndData.JDBC;
import utilsAndData.Message;
import utilsAndData.MessageToShow;
import utilsAndData.SessionUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class MessagesServlet
 */
@WebServlet("/messages")
public class MessagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public MessagesServlet() {
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

		String action = request.getParameter("action");
		if (action == null || "".equals(action)) {
			getMessages(request, response);
		} else if ("unread".equals(action)) {
			getUnreadMessagesCount(request, response);
		}
	}

	private void getUnreadMessagesCount(HttpServletRequest request,
			ServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Long userId = null;
		Gson gson = new Gson();
		String userIdFromSession = SessionUtils.getUserId(request);

		if (userIdFromSession == null) {
			String responce = "noSessionID";

			try {
				System.out.println("noSessionID");
				String jsonResponse = gson.toJson(responce);
				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
			}
		} else {
			try {
				userId = Long.parseLong(userIdFromSession);
			} catch (NumberFormatException e) {
				throw new ServletException();
			}

			try {

				Long count = jdbc.getUnreadMessagesCount(userId);
				String jsonResponse = gson.toJson(count);

				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
			}
		}
	}

	private void getMessages(HttpServletRequest request,
			ServletResponse response) throws IOException {
		String userIdFromSession = SessionUtils.getUserId(request);
		PrintWriter out = response.getWriter();
		long userId;
		Gson gson = new Gson();

		if (userIdFromSession == null) {
			String responce = "go to sign in";

			try {
				System.out.println("redirecting to sign in page");
				String jsonResponse = gson.toJson(responce);
				System.out.println(jsonResponse);
				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
			}
		}

		else if (userIdFromSession != null) {
			userId = Long.parseLong(userIdFromSession);
			// jdbc.retrievalOfUserById(userId);

			List<Message> messageList = new ArrayList<Message>();
			messageList = jdbc.listMessagesGroupedByTimestamp(userId);

			List<MessageToShow> messageToShowList = new ArrayList<MessageToShow>();
			for (Message message : messageList) {
				if (jdbc.isBannedUser(userId))
				{
					if (message.getUserId()==0)
					{
						messageToShowList.add(new MessageToShow(jdbc, message, userId));
					}
				}
				else
				{
				messageToShowList.add(new MessageToShow(jdbc, message, userId));
				}
			}

			try {
				System.out.println("try to show user data ");
				String jsonResponse = gson.toJson(messageToShowList);
				System.out.println(jsonResponse);
				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
			}
		}
	}

}

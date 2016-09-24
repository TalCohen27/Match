package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
 * Servlet implementation class ChatServlet
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public ChatServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		Object temp = config.getServletContext().getAttribute("matchDataJDBCTemplate");
		if (temp != null) {
			jdbc = (JDBC) temp;
		} else {
			throw new ServletException(
					"matchDataJDBCTemplate is not initiated.");
		}
		
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Long userId = getCurrentUser(request);
		Long targetId = null;
		try {
			targetId =  Long.parseLong(request.getParameter("targetId"));
		}
		catch (NumberFormatException nfe) {
			throw new ServletException("Wrong target id, received '"+request.getParameter("targetId")+"'");
		}
		
		
		List<Message> messages = jdbc.listMessagesByTwoUsers(userId, targetId);
		
	
		Gson gson = new Gson();
		try {
			List<MessageToShow> messageToShowList = new ArrayList<MessageToShow>();
			for (Message message : messages) {
				messageToShowList.add(new MessageToShow(jdbc, message, userId));
			}
			
			String jsonResponse = gson.toJson(messageToShowList);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}
	
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String result = "";
		String messageText = request.getParameter("messageText");
		Long targetId =  Long.parseLong(request.getParameter("targetId"));
		
		Long userId = getCurrentUser(request);
		
		Message message = new Message(userId, targetId, messageText);
		jdbc.insertToMessages(message );
		
		Gson gson = new Gson();
		try {
			String jsonResponse = gson.toJson(result);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}
	
	}
	
	private Long getCurrentUser(HttpServletRequest request) throws ServletException {
		String userIdFromSession = SessionUtils.getUserId(request);
		Long userId = null;

		if (userIdFromSession != null) {
			userId = Long.parseLong(userIdFromSession);
		} 
		return userId;
	}

	

}

package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import utilsAndData.JDBC;
import utilsAndData.SessionUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class BlocksServlet
 */
@WebServlet("/BlockOrBanServlet")
public class BlockOrBanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;
	private Jedis jedis = null;
	private static final int BLOCKCOMMAND = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BlockOrBanServlet() {
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
		long userId;
		long blockedId;
		String result = "false";

		String is_Block = request.getParameter("is_Block");
		String is_Insert = request.getParameter("is_Insert");
		String userIdfromParam = request.getParameter("profileId");
		String other_ban_reason = request.getParameter("other_ban_reason");
		String sessionCheck = request.getParameter("check");
		PrintWriter out = response.getWriter();
		String userIdFromSession = SessionUtils.getUserId(request);
		Gson gson = new Gson();
		System.out.println("start of BlocksServlet");

		if (userIdFromSession == null) {
			result = "go to sign in";

		} else {
			userId = Long.parseLong(userIdFromSession);

			if (userIdfromParam != null) {
				blockedId = Long.parseLong(userIdfromParam);

				if (sessionCheck != null) {
					System.out.println("BlockOrBanServlet: sessionCheck = "
							+ sessionCheck + ", userId = " + userId
							+ ", sessionId = "
							+ Long.parseLong(userIdFromSession));
					if (jdbc.isBlockedUser(Long.parseLong(userIdFromSession),
							blockedId)) {
						result = "true";
					}
					try {
						System.out.println("partner servlet request");
						String jsonResponse = gson.toJson(result);
						System.out.println(jsonResponse);
						out.print(jsonResponse);
						out.flush();
					} finally {
						out.close();
					}
				} else {

					if ((is_Block.equals("true")) && (is_Insert.equals("true"))) { // need
																					// to
																					// BLOCK
																					// this
																					// user
						jdbc.insertToBlocks(userId, blockedId, BLOCKCOMMAND,
								other_ban_reason);
						jedis.sadd("uid:" + userId + ":blocked",
								userIdfromParam);
						result = "true";
					} else if ((is_Block.equals("false"))
							&& (is_Insert.equals("true"))) { // need to Ban this
																// user
						// need to get from atribute the reason. and send to
						// this
						// function
						String ban_reasonFromParam = request
								.getParameter("ban_reason");
						if (ban_reasonFromParam != null) {
							if ( (ban_reasonFromParam.equals("2"))
									|| (ban_reasonFromParam.equals("3"))) {
								jdbc.insertToBlocks(userId, blockedId,
										Integer.parseInt(ban_reasonFromParam),
										other_ban_reason);
								jedis.sadd("uid:" + userId + ":blocked",
										userIdfromParam);
								result = "true";
							}
						} else {
							throw new ServletException(
									"null parameter ban_reason from js");
						}
					} else if ((is_Block.equals("true"))
							&& (is_Insert.equals("false"))) { // need to Unblock
																// or
																// UnBan this
																// user
						jdbc.deleteFromBlocks(userId, blockedId);
						jedis.srem("uid:" + userId + ":blocked",
								userIdfromParam);
						if (userId == 0) {
							result = "admin";
						} else {
							result = "true";
						}
					} else {
						throw new ServletException(
								"null parameter is_Block or is_Insert from js");
					}
				}
			} else {
				throw new ServletException("null partnerId parameter");
			}

		}

		try {
			String jsonResponse = gson.toJson(result);
			System.out.println(jsonResponse);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}
	}
}

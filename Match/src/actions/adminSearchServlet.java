package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilsAndData.JDBC;
import utilsAndData.SessionUtils;
import utilsAndData.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class adminSearchServlet
 */
@WebServlet("/adminSearchServlet")
public class adminSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public adminSearchServlet() {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("IN processRequest of MatchesServlet");
		String userIdFromSession = SessionUtils.getUserId(request);
		PrintWriter out = response.getWriter();
		String search_field = request.getParameter("search_field");
		String search_value = request.getParameter("search_value");
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

		// switch case
		List<User> userList = new ArrayList<User>();
		switch (search_field) {
		case "id":
			userList.add(jdbc.retrievalOfUserById(Long.parseLong(search_value)));
			break;
		case "name":
			userList = jdbc.SearchUsersByName(search_value);
			break;
		case "email":
			userList.add(jdbc.retrievalOfUserByEmail(search_value));
			break;
		case "sex":
			userList = jdbc.SearchUsersBySex(search_value);
			break;
		default:
			userList = null;
			break;
		}
		for (User myuser : userList){
			myuser.setPhoto("PhotoServlet?id=" + myuser.getId() + "");
		}

		try {
			System.out.println("try to show admin search results");
			String jsonResponse = gson.toJson(userList);
			System.out.println(jsonResponse);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}
	}
}

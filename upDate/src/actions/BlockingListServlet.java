package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 * Servlet implementation class MatchesServlet
 */
@WebServlet("/BlockingListServlet")
public class BlockingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BlockingListServlet() {
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
		} else if (userIdFromSession != null) {
			userId = Long.parseLong(userIdFromSession);
			List<User> blockedUserList = jdbc.listOfBlockedUsersById(userId);
			List<UserToShow> userList = new ArrayList<UserToShow>();
			Iterator<User> itr = blockedUserList.iterator();
			while (itr.hasNext()) {
				UserToShow tempUser = new UserToShow(itr.next());
				userList.add(tempUser);
			}

			ReturningJson rj = new ReturningJson();
			User user = jdbc.retrievalOfUserById(userId);
			rj.isAdmin = user.getId().equals(Consts.ADMIN_ID); // this is admin
			rj.userList = userList;
			try {
				System.out
						.println("try to show review data from BlockingListServletServlet");
				String jsonResponse = gson.toJson(rj);
				System.out.println(jsonResponse);
				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
			}
		}
	}

	class ReturningJson {
		List<UserToShow> userList;
		boolean isAdmin = false;
	}

	@SuppressWarnings("unused")
	class UserToShow {
		private Long id;
		private String firstName;
		private String lastName;
		private String photo;
		private String aboutMe = null;
		private String sex;
		private Date birthday = null;
		private String education;
		private String area;
		private String language;
		private String maritalStatus;
		private int children;
		private String eyeColor;
		private String hairType;
		private String hairColor;
		private String origin;
		private int height;
		private int weight;
		private String earningLevel;
		private String religion;
		private String smoking;
		private String physique;
		private String intrestedIn;

		private UserToShow(User userToShow) {
			this.id = userToShow.getId();
			this.firstName = userToShow.getFirstName();
			this.lastName = userToShow.getLastName();
			this.photo = "PhotoServlet?id=" + userToShow.getId() + "";
			this.aboutMe = userToShow.getAboutMe();
			this.sex = userToShow.getSex();
			this.birthday = userToShow.getBirthday();
			this.education = userToShow.getEducation();
			this.area = userToShow.getArea();
			this.language = userToShow.getLanguage();
			this.maritalStatus = userToShow.getMaritalStatus();
			this.children = userToShow.getChildren();
			this.eyeColor = userToShow.getEyeColor();
			this.hairType = userToShow.getHairType();
			this.hairColor = userToShow.getHairColor();
			this.origin = userToShow.getOrigin();
			this.height = userToShow.getHeight();
			this.weight = userToShow.getWeight();
			this.earningLevel = userToShow.getEarningLevel();
			this.religion = userToShow.getReligion();
			this.smoking = userToShow.getSmoking();
			this.physique = userToShow.getPhysique();
			this.intrestedIn = userToShow.getIntrestedIn();
		}
	}

}

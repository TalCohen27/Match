package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

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
 * Servlet implementation class MyProfileServlet
 */
@WebServlet("/partnerprofile")
public class PartnerProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public PartnerProfileServlet() {
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

		long userId;
		long reviewerId;
		String userIdfromParam = request.getParameter("profileId");
		String sessionCheck = request.getParameter("check");
		User user = new User();
		PrintWriter out = response.getWriter();
		String userIdFromSession = SessionUtils.getUserId(request);
		Gson gson = new Gson();
		String responce;

		if (userIdFromSession == null) {
			responce = "go to sign in";

			try {
				System.out.println("redirecting to sign in page");
				String jsonResponse = gson.toJson(responce);
				System.out.println(jsonResponse);
				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
			}
		} else {
			if (userIdfromParam != null) {
				userId = Long.parseLong(userIdfromParam);
			} else {
				throw new ServletException("null partnerId parameter");
			}

			if (sessionCheck != null) {
				System.out.println("PrtnerProfileServlet: sessionCheck = "
						+ sessionCheck + ", userId = " + userId
						+ ", sessionId = " + Long.parseLong(userIdFromSession));
				if (jdbc.isBlockedUser(Long.parseLong(userIdFromSession),
						userId)) {
					responce = "true";
					if (Long.parseLong(userIdFromSession) == Consts.ADMIN_ID) {
						responce = "trueAdmin";
					}
				} else {
					responce = "false";
					if (Long.parseLong(userIdFromSession) == Consts.ADMIN_ID) {
						responce = "falseAdmin";
					}
				}
				try {
					System.out.println("partner servlet request");
					String jsonResponse = gson.toJson(responce);
					System.out.println(jsonResponse);
					out.print(jsonResponse);
					out.flush();
				} finally {
					out.close();
				}
			} else {
				reviewerId = Long.parseLong(userIdFromSession);
				user = jdbc.retrievalOfUserById(userId);
				UserToShow userToShow = new UserToShow(user);
				if(reviewerId!=0){
				   jdbc.insertNewReview(userId, reviewerId);
				}

				try {
					System.out.println("try to show user data ");
					String jsonResponse = gson.toJson(userToShow);
					System.out.println(jsonResponse);
					out.print(jsonResponse);
					out.flush();
				} finally {
					out.close();
				}
			}
		}
	}

	@SuppressWarnings("unused")
	class UserToShow {
		private Long id;
		private String firstName;
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
		private String photo = null;

		private UserToShow(User user) {
			this.id = user.getId();
			this.firstName = user.getFirstName();
			this.aboutMe = user.getAboutMe();
			this.sex = user.getSex();
			this.birthday = user.getBirthday();
			this.education = user.getEducation();
			this.area = user.getArea();
			this.language = user.getLanguage();
			this.maritalStatus = user.getMaritalStatus();
			this.children = user.getChildren();
			this.eyeColor = user.getEyeColor();
			this.hairType = user.getHairType();
			this.hairColor = user.getHairColor();
			this.origin = user.getOrigin();
			this.height = user.getHeight();
			this.weight = user.getWeight();
			this.earningLevel = user.getEarningLevel();
			this.religion = user.getReligion();
			this.smoking = user.getSmoking();
			this.physique = user.getPhysique();
			this.intrestedIn = user.getIntrestedIn();
			this.photo = "PhotoServlet?id=" + user.getId() + "";

		}
	}
}

package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

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
 * Servlet implementation class MyProfileServlet
 */
@WebServlet("/profiledata")
public class ProfileDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public ProfileDataServlet() {
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
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String userIdFromSession = SessionUtils.getUserId(request);

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
			User user = new User();
			userId = Long.parseLong(userIdFromSession);
			user = jdbc.retrievalOfUserById(userId);
			UserToShow userToShow = new UserToShow(user);

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

	@SuppressWarnings("unused")
	class UserToShow {
		private String firstName;
		private String lastName;
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
		// ------------------Partner's Preferences----------------------------
		private int partnerMaxHeight;
		private int partnerMinHeight;
		private int partnerMaxAge;
		private int partnerMinAge;
		private String partner_education;
		private String partner_area;
		private String partner_language;
		private String partner_maritalStatus;
		private String partner_eyeColor;
		private String partner_hairType;
		private String partner_hairColor;
		private String partner_origin;
		private String partner_earningLevel;
		private String partner_religion;
		private String partner_smoking;
		private String partner_physique;

		private UserToShow(User user) {
			this.firstName = user.getFirstName();
			this.lastName = user.getLastName();
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
			this.partnerMaxHeight = user.getPartnerMaxHeight();
			this.partnerMinHeight = user.getPartnerMinHeight();
			this.partnerMaxAge = user.getPartnerMaxAge();
			this.partnerMinAge = user.getPartnerMinAge();
			this.partner_education = user.getPartner_education();
			this.partner_area = user.getPartner_area();
			this.partner_language = user.getPartner_language();
			this.partner_maritalStatus = user.getPartner_maritalStatus();
			this.partner_eyeColor = user.getPartner_eyeColor();
			this.partner_hairType = user.getPartner_hairType();
			this.partner_hairColor = user.getPartner_hairColor();
			this.partner_origin = user.getPartner_origin();
			this.partner_earningLevel = user.getPartner_earningLevel();
			this.partner_religion = user.getPartner_religion();
			this.partner_smoking = user.getPartner_smoking();
			this.partner_physique = user.getPartner_physique();
		}
	}

}

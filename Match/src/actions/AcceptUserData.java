package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import utilsAndData.JDBC;
import utilsAndData.SessionUtils;
import utilsAndData.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class AcceptUserData
 */
@WebServlet("/acceptuserdata")
public class AcceptUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;
	private Jedis jedis = null;

	public AcceptUserData() {
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
		PrintWriter out = response.getWriter();

		String responce = "";
		String email = request.getParameter("email");
		Boolean emailCheck = jdbc.checkIfEmailExists(email);
		if (emailCheck) {
			responce = "Email already exists!";
		} else {
			getAndProcessUserSignUpParameters(request, response, responce);
			responce = "true";
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

	@SuppressWarnings("deprecation")
	private void getAndProcessUserSignUpParameters(HttpServletRequest request,
			HttpServletResponse response, String responce)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Manager").include(request,
				response);
		getServletContext().getRequestDispatcher("/OpenSessionServlet")
				.include(request, response);
		String userIdFromSession = SessionUtils.getUserId(request);

		Enumeration<String> enParams = request.getParameterNames();
		while (enParams.hasMoreElements()) {
			String paramName = enParams.nextElement();
			System.out.println("Attribute Name - " + paramName + ", Value - "
					+ request.getParameter(paramName));
		}
		long userId = -1;
		try {
			userId = Long.parseLong(userIdFromSession);
		} catch (Exception e) {
			System.out.println("null session parameter");
		}

		User newUser = new User(userId);

		// MySql
		String firstName = request.getParameter("first");
		newUser.setFirstName(firstName);
		String lastName = request.getParameter("last");
		newUser.setLastName(lastName);
		String email = request.getParameter("email");
		newUser.setEmail(email);
		String password = request.getParameter("password");
		newUser.setPassword(password);
		String aboutMe = request.getParameter("about_me");
		newUser.setAboutMe(aboutMe);
		// Redis
		String sex = request.getParameter("sex");
		newUser.setSex(sex);
		String birthday = request.getParameter("birthday");
		String yearOfBirth = "";
		if (birthday != null) {
			newUser.setBirthday(new Date(Integer.parseInt(birthday.substring(0,
					4)) - 1900, Integer.parseInt(birthday.substring(5, 7)) - 1,
					Integer.parseInt(birthday.substring(8, 10))));

			yearOfBirth = newUser.getBirthday().toString().substring(0, 4);
		}

		String education = request.getParameter("education");
		newUser.setEducation(education);
		String area = request.getParameter("area");
		newUser.setArea(area);
		String language = request.getParameter("language");
		newUser.setLanguage(language);
		String maritalStatus = request.getParameter("marital_status");
		newUser.setMaritalStatus(maritalStatus);
		String children = request.getParameter("children");
		int childrenInt = Integer.parseInt(children);
		newUser.setChildren(childrenInt);
		String eyeColor = request.getParameter("eye_color");
		newUser.setEyeColor(eyeColor);
		String hairType = request.getParameter("hair_type");
		newUser.setHairType(hairType);
		String hairColor = request.getParameter("hair_color");
		newUser.setHairColor(hairColor);
		String origin = request.getParameter("origin");
		newUser.setOrigin(origin);
		System.out.println(birthday);
		String height = request.getParameter("height");
		int heightInt = Integer.parseInt(height);
		newUser.setHeight(heightInt);
		heightInt = Integer.parseInt(height) - (Integer.parseInt(height) % 10);
		String weight = request.getParameter("weight");
		int weightInt = Integer.parseInt(weight);
		newUser.setWeight(weightInt);
		String earningLevel = request.getParameter("earning_level");
		newUser.setEarningLevel(earningLevel);
		String religion = request.getParameter("religion");
		newUser.setReligion(religion);
		String physique = request.getParameter("physique");
		newUser.setPhysique(physique);
		String smoking = request.getParameter("smoking");
		newUser.setSmoking(smoking);
		String intrestedIn = request.getParameter("intrestedIn");
		newUser.setIntrestedIn(intrestedIn);

		request.getSession().setAttribute("user", newUser);

		// Insert to Redis
		System.out.println("useruid=" + userId);
		jedis.sadd("sex:" + sex, String.valueOf(userId)); // sex set is simple
															// to allow
															// incorporate in
															// SDIFFSTORE
		jedis.zadd("yearOfBirth:" + yearOfBirth, 1, String.valueOf(userId));
		jedis.zadd("education:" + education, 1, String.valueOf(userId));
		jedis.zadd("area:" + area, 1, String.valueOf(userId));
		jedis.zadd("language:" + language, 1, String.valueOf(userId));
		jedis.zadd("maritalStatus:" + maritalStatus, 1, String.valueOf(userId));
		jedis.zadd("eyeColor:" + eyeColor, 1, String.valueOf(userId));
		jedis.zadd("hairType:" + hairType, 1, String.valueOf(userId));
		jedis.zadd("hairColor:" + hairColor, 1, String.valueOf(userId));
		jedis.zadd("origin:" + origin, 1, String.valueOf(userId));
		jedis.zadd("height:" + heightInt + "_" + (heightInt + 9), 1,
				String.valueOf(userId));
		jedis.zadd("earningLevel:" + earningLevel, 1, String.valueOf(userId));
		jedis.zadd("religion:" + religion, 1, String.valueOf(userId));
		jedis.zadd("smoking:" + smoking, 1, String.valueOf(userId));
		jedis.zadd("physique:" + physique, 1, String.valueOf(userId));

	}
}

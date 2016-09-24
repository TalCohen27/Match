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
import utilsAndData.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class AcceptPartnerData
 */
@WebServlet("/acceptpartnerdata")
public class AcceptPartnerData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC matchDataJDBCTemplate = null;
	private Jedis jedis = null;

	public AcceptPartnerData() {
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
		PrintWriter out = response.getWriter();
		String responce = "";

		getAndProcessPartnerSignUpParameters(request, response);

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

	private void getAndProcessPartnerSignUpParameters(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userIdFromSession = SessionUtils.getUserId(request);
		long userId;

		if (userIdFromSession != null) {
			userId = Long.parseLong(userIdFromSession);
		} else {
			throw new ServletException("null session parameter");
		}

		User newUser = (User) request.getSession().getAttribute("user");
		String education = request.getParameter("partner_education");
		newUser.setPartner_education(education);
		String area = request.getParameter("partner_area");
		newUser.setPartner_area(area);

		String language = request.getParameter("partner_language");
		newUser.setPartner_language(language);

		String maritalStatus = request.getParameter("partner_marital_status");
		newUser.setPartner_maritalStatus(maritalStatus);

		String eyeColor = request.getParameter("partner_eye_color");
		newUser.setPartner_eyeColor(eyeColor);
		String hairType = request.getParameter("partner_hair_type");
		newUser.setPartner_hairType(hairType);

		String hairColor = request.getParameter("partner_hair_color");
		newUser.setPartner_hairColor(hairColor);
		String origin = request.getParameter("partner_origin");
		newUser.setPartner_origin(origin);
		String earningLevel = request.getParameter("partner_earning_level");
		newUser.setPartner_earningLevel(earningLevel);
		String religion = request.getParameter("partner_religion");
		newUser.setPartner_religion(religion);
		String physique = request.getParameter("partner_physique");
		newUser.setPartner_physique(physique);
		String smoking = request.getParameter("partner_smoking");
		newUser.setPartner_smoking(smoking);

		String ageMin = request.getParameter("partner_age_min");
		int ageMinInt = Integer.parseInt(ageMin);
		newUser.setPartnerMinAge(ageMinInt);

		String ageMax = request.getParameter("partner_age_max");
		int ageMaxInt = Integer.parseInt(ageMax);
		newUser.setPartnerMaxAge(ageMaxInt);

		String heightMin = request.getParameter("partner_height_min");
		int heightMinInt = Integer.parseInt(heightMin);
		newUser.setPartnerMinHeight(heightMinInt);

		String heightMax = request.getParameter("partner_height_max");
		int heightMaxInt = Integer.parseInt(heightMax);
		newUser.setPartnerMaxHeight(heightMaxInt);

		// Insert to DB
		matchDataJDBCTemplate.insertToUSer(newUser);

		// Important Properties! for example the partner's ZSET uid:123:must
		// if both then don't add the property to list because we don't need to
		// make intersection
		// String bla = newUser.getIntrestedIn();
		if (!(newUser.getIntrestedIn()).equals("both")) {
			jedis.zadd("uid:" + userId + ":must", 1,
					"sex:" + newUser.getIntrestedIn());
		}
		jedis.zadd("uid:" + userId + ":must", 1, "ageMin:" + ageMin);
		jedis.zadd("uid:" + userId + ":must", 1, "ageMax:" + ageMax);
		if (!area.equals("none")) {
			jedis.zadd("uid:" + userId + ":must", 1, "area:" + area);
		}

		if (education.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "education:higher");
			jedis.zadd("uid:" + userId, 1, "education:secondary");
		} else {
			jedis.zadd("uid:" + userId, 1, "education:" + education);
		}
		if (language.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "language:hebrew");
			jedis.zadd("uid:" + userId, 1, "language:english");
			jedis.zadd("uid:" + userId, 1, "language:russian");
		} else {
			jedis.zadd("uid:" + userId, 1, "language:" + language);
		}
		if (maritalStatus.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "maritalStatus:single");
			jedis.zadd("uid:" + userId, 1, "maritalStatus:divorced");
			jedis.zadd("uid:" + userId, 1, "maritalStatus:widow/widower");
		} else {
			jedis.zadd("uid:" + userId, 1, "maritalStatus:" + maritalStatus);
		}
		if (eyeColor.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "eyeColor:blue");
			jedis.zadd("uid:" + userId, 1, "eyeColor:green");
			jedis.zadd("uid:" + userId, 1, "eyeColor:brown");
			jedis.zadd("uid:" + userId, 1, "eyeColor:black");
		} else {
			jedis.zadd("uid:" + userId, 1, "eyeColor:" + eyeColor);
		}
		if (hairType.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "hairType:curly");
			jedis.zadd("uid:" + userId, 1, "hairType:straight");
			jedis.zadd("uid:" + userId, 1, "hairType:dreads");
			jedis.zadd("uid:" + userId, 1, "hairType:wavy");
		} else {
			jedis.zadd("uid:" + userId, 1, "hairType:" + hairType);
		}
		if (hairColor.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "hairColor:blond");
			jedis.zadd("uid:" + userId, 1, "hairColor:brunet");
			jedis.zadd("uid:" + userId, 1, "hairColor:black");
			jedis.zadd("uid:" + userId, 1, "hairColor:color");
		} else {
			jedis.zadd("uid:" + userId, 1, "hairColor:" + hairColor);
		}
		if (origin.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "origin:ashkenazic");
			jedis.zadd("uid:" + userId, 1, "origin:sephardi");
			jedis.zadd("uid:" + userId, 1, "origin:else");
		} else {
			jedis.zadd("uid:" + userId, 1, "origin:" + origin);
		}
		for (int i = heightMinInt; i < heightMaxInt; i += 10) {
			jedis.zadd("uid:" + userId, 1, "height:" + i + "_" + (i + 9));
		}
		if (earningLevel.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "earningLevel:high");
			jedis.zadd("uid:" + userId, 1, "earningLevel:medium");
			jedis.zadd("uid:" + userId, 1, "earningLevel:low");
		} else {
			jedis.zadd("uid:" + userId, 1, "earningLevel:" + earningLevel);
		}
		if (religion.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "religion:christian");
			jedis.zadd("uid:" + userId, 1, "religion:jew");
			jedis.zadd("uid:" + userId, 1, "religion:muslim");
			jedis.zadd("uid:" + userId, 1, "religion:buddhism");
			jedis.zadd("uid:" + userId, 1, "religion:atheist");
		} else {
			jedis.zadd("uid:" + userId, 1, "religion:" + religion);
		}
		if (smoking.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "smoking:smoker");
			jedis.zadd("uid:" + userId, 1, "smoking:non_smoker");
		} else {
			jedis.zadd("uid:" + userId, 1, "smoking:" + smoking);
		}
		if (physique.equals("none")) {
			jedis.zadd("uid:" + userId, 1, "physique:Thin");
			jedis.zadd("uid:" + userId, 1, "physique:Normal");
			jedis.zadd("uid:" + userId, 1, "physique:Plump");
			jedis.zadd("uid:" + userId, 1, "physique:Athletic");
		} else {
			jedis.zadd("uid:" + userId, 1, "physique:" + physique);
		}
	}

}

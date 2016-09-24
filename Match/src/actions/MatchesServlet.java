package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.ZParams.Aggregate;
import utilsAndData.Consts;
import utilsAndData.JDBC;
import utilsAndData.SessionUtils;
import utilsAndData.User;
import utilsAndData.UserAndScore;
import static java.lang.Math.abs;

import com.google.gson.Gson;

@SuppressWarnings("unused")
@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC matchDataJDBCTemplate = null;
	private Jedis jedis = null;
	private User user = null;
	private int maxScore = 13;

	public MatchesServlet() {
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
		}

		else if (userIdFromSession != null) {

			userId = Long.parseLong(userIdFromSession);

			if (matchDataJDBCTemplate.isBannedUser(userId)) {
				String responce = "bannedUser";

				try {
					System.out.println("this user is banned user");
					String jsonResponse = gson.toJson(responce);
					System.out.println(jsonResponse);
					out.print(jsonResponse);
					out.flush();
				} finally {
					out.close();
				}
			} else {

				user = matchDataJDBCTemplate.retrievalOfUserById(userId);
				// If user banned by admin then he will not be able to view the
				// matches
				if (jedis.sismember("uid:" + Consts.ADMIN_ID + ":blocked",
						userIdFromSession)) {
					jedis.zremrangeByRank("uid:" + userIdFromSession
							+ ":matches", 0, -1);
				} else {
					updateMatches(userIdFromSession);
				}

			System.out.println("match process done");

				Set<Tuple> matchesNamesAndScores = jedis.zrevrangeWithScores(
						"uid:" + userId + ":matches", 0, -1);
				Iterator<Tuple> matchItr = matchesNamesAndScores.iterator();
				List<UserAndScoreToShow> userList = new ArrayList<UserAndScoreToShow>();
				while (matchItr.hasNext()) {
					Tuple matchTup = matchItr.next();
					User userParam = matchDataJDBCTemplate
							.retrievalOfUserById(Long.parseLong(matchTup
									.getElement()));
					UserAndScore userAndScore = new UserAndScore(userParam,
							matchTup.getScore());
					UserAndScoreToShow userAndScoreToShow = new UserAndScoreToShow(
							userAndScore);
					userList.add(userAndScoreToShow);
				}

				try {
					System.out
							.println("try to show review data from MatchesServlet");
					String jsonResponse = gson.toJson(userList);
					System.out.println(jsonResponse);
					out.print(jsonResponse);
					out.flush();
				} finally {
					out.close();
				}
			}
		}
	}

	private void updateMatches(String userId) {
		// unionstore scope
		ZParams params = new ZParams();
		Set<Tuple> requirementsSetsNamesAndScores = jedis.zrangeWithScores(
				"uid:" + userId, 0, -1);
		String[] sortedSets = new String[requirementsSetsNamesAndScores.size()];
		int[] weights = new int[requirementsSetsNamesAndScores.size()];
		Iterator<Tuple> requirementItr = requirementsSetsNamesAndScores
				.iterator();
		int i = 0;
		while (requirementItr.hasNext()) {
			Tuple requirementTup = requirementItr.next();
			sortedSets[i] = requirementTup.getElement();
			weights[i] = (int) requirementTup.getScore();
			i++;
		}
		params.aggregate(Aggregate.SUM);
		params.weights(weights);
		jedis.zremrangeByRank("uid:" + userId + ":matches", 0, -1);
		jedis.zunionstore("uid:" + userId + ":matches", params, sortedSets);

		// interstore scope
		Set<String> mustRequirementsSetsNames = jedis.zrange("uid:" + userId
				+ ":must", 0, -1);
		Iterator<String> mustReqItr = mustRequirementsSetsNames.iterator();
		String sexSetName = "";
		String area = "";
		Integer ageMin = 0;
		Integer ageMax = 0;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		while (mustReqItr.hasNext()) {
			String str = mustReqItr.next().toString();
			if (str.contains("sex")) {
				sexSetName = str;
			} else if (str.contains("area")) {
				area = str;
			} else if (str.contains("ageMin")) {
				ageMin = Integer.parseInt(str.replaceAll("\\D+", ""));
			} else if (str.contains("ageMax")) {
				ageMax = Integer.parseInt(str.replaceAll("\\D+", ""));
			}
		}

		for (i = ageMin; i <= ageMax; i++) {
			jedis.zunionstore("uid:" + userId + ":requiredAges", "uid:"
					+ userId + ":requiredAges", "yearOfBirth:"
					+ (currentYear - i));
		}

		jedis.zunionstore("uid:" + userId + ":matches", "uid:" + userId
				+ ":matches", "uid:" + userId + ":requiredAges");

		if (!sexSetName.isEmpty() && !area.isEmpty()) {
			jedis.zinterstore("uid:" + userId + ":matches", "uid:" + userId
					+ ":matches", "uid:" + userId + ":requiredAges",
					sexSetName, area);
		} else if (!sexSetName.isEmpty()) {
			jedis.zinterstore("uid:" + userId + ":matches", "uid:" + userId
					+ ":matches", "uid:" + userId + ":requiredAges", sexSetName);
		} else if (!area.isEmpty()) {
			jedis.zinterstore("uid:" + userId + ":matches", "uid:" + userId
					+ ":matches", "uid:" + userId + ":requiredAges", area);
		} else {
			jedis.zinterstore("uid:" + userId + ":matches", "uid:" + userId
					+ ":matches", "uid:" + userId + ":requiredAges");
		}

		jedis.zremrangeByRank("uid:" + userId + ":requiredAges", 0, -1);

		// sdiffstore scope
		if (sexSetName.isEmpty()) {
			jedis.sunionstore("uid:" + userId + ":filter", "sex:male",
					"sex:female");
			jedis.sdiffstore("uid:" + userId + ":filter", "uid:" + userId
					+ ":filter", "uid:" + userId + ":blocked");
		} else {
			jedis.sdiffstore("uid:" + userId + ":filter", sexSetName, "uid:"
					+ userId + ":blocked");
		}

		// interstore with filter
		jedis.zinterstore("uid:" + userId + ":matches", "uid:" + userId
				+ ":matches", "uid:" + userId + ":filter");

		// Reduce matches to 500 users
		jedis.zremrangeByRank("uid:" + userId + ":matches", 0, -501);

		// Remove the current user's id from match list in case he/she
		// homosexual or both
		jedis.zrem("uid:" + userId + ":matches", userId);

		// partner matching score calculation scope
		Set<String> partnerIdSet = jedis.zrevrange(
				"uid:" + userId + ":matches", 0, -1);
		Iterator<String> PartnersItr = partnerIdSet.iterator();

		while (PartnersItr.hasNext()) {
			String partnerId = PartnersItr.next();

			Set<Tuple> partnerRequirementsSetsNamesAndScores = (jedis
					.zrangeWithScores("uid:" + partnerId, 0, -1));
			String[] partnerSortedSets = new String[partnerRequirementsSetsNamesAndScores
					.size()];
			int[] partnerWeights = new int[partnerRequirementsSetsNamesAndScores
					.size()];
			if (jedis.sismember("uid:" + partnerId + ":blocked", userId)) {
				jedis.zrem("uid:" + userId + ":matches", partnerId);
			} else if (jedis.sismember("uid:" + Consts.ADMIN_ID + ":blocked",
					partnerId)) {
				jedis.zrem("uid:" + userId + ":matches", partnerId);
			} else {
				mustRequirementsSetsNames = jedis.zrange("uid:" + partnerId
						+ ":must", 0, -1);
				Iterator<String> partnerMustReqItr = mustRequirementsSetsNames
						.iterator();
				sexSetName = "";
				area = "";
				ageMin = 0;
				ageMax = 0;
				while (partnerMustReqItr.hasNext()) {
					String str = partnerMustReqItr.next().toString();
					if (str.contains("sex")) {
						sexSetName = str;
					} else if (str.contains("area")) {
						area = str;
					} else if (str.contains("ageMin")) {
						ageMin = Integer.parseInt(str.replaceAll("\\D+", ""));
					} else if (str.contains("ageMax")) {
						ageMax = Integer.parseInt(str.replaceAll("\\D+", ""));
					}
				}
				boolean isUserFit = true;
				int age = currentYear
						- Integer.parseInt(user.getBirthday().toString()
								.substring(0, 4));

				if (age < ageMin || age > ageMax) {
					isUserFit = false;
				}

				if (isUserFit && !sexSetName.isEmpty()
						&& !jedis.sismember(sexSetName, userId)) {
					isUserFit = false;
				}

				if (isUserFit && !area.isEmpty()
						&& jedis.zrank(area, userId) == null) {
					isUserFit = false;
				}

				if (!isUserFit) {
					jedis.zrem("uid:" + userId + ":matches", partnerId);
				} else {
					double partnerScore = 0;
					i = 0;
					Iterator<Tuple> partnerRequirementsItr = partnerRequirementsSetsNamesAndScores
							.iterator();
					while (partnerRequirementsItr.hasNext()) {
						Tuple t = partnerRequirementsItr.next();
						partnerSortedSets[i] = t.getElement();
						partnerWeights[i] = (int) t.getScore();
						if (jedis.zscore(partnerSortedSets[i], userId) != null) {
							partnerScore += partnerWeights[i]
									* jedis.zscore(partnerSortedSets[i], userId);
						}
						i++;
					}
					double userScore = jedis.zscore("uid:" + userId
							+ ":matches", partnerId);
					if (partnerScore < userScore) {
						jedis.zadd("uid:" + userId + ":matches", partnerScore,
								partnerId);
					}
					
					userScore = jedis.zscore("uid:" + userId
							+ ":matches", partnerId);
					double privacylevel = percentToScore();
					if (privacylevel > userScore) {
						jedis.zrem("uid:" + userId + ":matches", partnerId);
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	class UserAndScoreToShow {
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
		private double score;

		private UserAndScoreToShow(UserAndScore userAndScore) {
			this.id = userAndScore.getUser().getId();
			this.firstName = userAndScore.getUser().getFirstName();
			this.lastName = userAndScore.getUser().getLastName();
			this.photo = "PhotoServlet?id=" + userAndScore.getUser().getId() + "";
			this.aboutMe = userAndScore.getUser().getAboutMe();
			this.sex = userAndScore.getUser().getSex();
			this.birthday = userAndScore.getUser().getBirthday();
			this.education = userAndScore.getUser().getEducation();
			this.area = userAndScore.getUser().getArea();
			this.language = userAndScore.getUser().getLanguage();
			this.maritalStatus = userAndScore.getUser().getMaritalStatus();
			this.children = userAndScore.getUser().getChildren();
			this.eyeColor = userAndScore.getUser().getEyeColor();
			this.hairType = userAndScore.getUser().getHairType();
			this.hairColor = userAndScore.getUser().getHairColor();
			this.origin = userAndScore.getUser().getOrigin();
			this.height = userAndScore.getUser().getHeight();
			this.weight = userAndScore.getUser().getWeight();
			this.earningLevel = userAndScore.getUser().getEarningLevel();
			this.religion = userAndScore.getUser().getReligion();
			this.smoking = userAndScore.getUser().getSmoking();
			this.physique = userAndScore.getUser().getPhysique();
			this.intrestedIn = userAndScore.getUser().getIntrestedIn();
			this.score = userAndScore.getScore();
		}
	}
	
	double percentToScore(){
		int percent = user.getPrivacyLevel();
		double score = Math.ceil(percent * maxScore / 100);
		return score;
	}

}

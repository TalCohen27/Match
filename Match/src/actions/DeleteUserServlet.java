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
import java.util.List;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/deleteuserservlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;
	private Jedis jedis = null;

	public DeleteUserServlet() {
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
		String userIdFromSession = SessionUtils.getUserId(request);
		long userId = 0;
		Gson gson = new Gson();
		String responce = "";

		if (userIdFromSession != null) {
			try {
				userId = Long.parseLong(userIdFromSession);
			} catch (Exception e) {
				System.out.println("null session parameter");
			}

			try {
				
				deleteFromRedis(userIdFromSession);
				jdbc.deleteProfile(userId);
				responce = "true";
				getServletContext()
						.getRequestDispatcher("/CloseSessionServlet").include(
								request, response);
			} catch (Exception e) {
				responce = "Unable to delete user";
				System.out.println("Unable to delete user");
			}
		} else {
			responce = "true";
		}

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
	
	private void deleteFromRedis(String userIDString)
	{
		long userID = Long.parseLong(userIDString);
		User user = jdbc.retrievalOfUserById(userID);
		
	    String sex = user.getSex();
	    jedis.srem("sex:" + sex, userIDString);   
	    String yearOfBirth =  user.getBirthday().toString().substring(0, 4);
	    jedis.zrem("yearOfBirth:" + yearOfBirth, userIDString);
	    String education = user.getEducation();
		jedis.zrem("education:" + education, userIDString);	
		String area = user.getArea();
		jedis.zrem("area:" + area, userIDString);
		String language = user.getLanguage();
		jedis.zrem("language:" + language, userIDString);
	    String maritalStatus = user.getMaritalStatus();
	    jedis.zrem("martialStatus:" + maritalStatus, userIDString);   
	    String eyeColor = user.getEyeColor();
	    jedis.zrem("eyeColor:" + eyeColor, userIDString);    
	    String hairType = user.getHairType();
	    jedis.zrem("hairType:" + hairType, userIDString);  
	    String hairColor = user.getHairColor();
	    jedis.zrem("hairColor:" + hairColor, userIDString);
	    String origin = user.getOrigin();
	    jedis.zrem("origin:" + origin, userIDString);
	    int height = user.getHeight();
	    int heightInt = height - height % 10;
	    jedis.zrem("height:" + heightInt + "_" + (heightInt + 9), userIDString);
	    String earningLevel = user.getEarningLevel();
	    jedis.zrem("earningLevel:" + earningLevel, userIDString);	     
		String religion = user.getReligion();
		jedis.zrem("religion:" + religion, userIDString);
	    String smoking = user.getSmoking();
	    jedis.zrem("smoking:" + smoking, userIDString);
		String physique = user.getPhysique();
		jedis.zrem("physique:" + physique, userIDString);
		//private String photo=null;
		//blocked	
		
		List<User> blockers = jdbc.listOfBlockersOfUserById(userID);
		for(User bUser : blockers)
		{
			jedis.zrem("uid:" + bUser.getId() + ":blocked", userIDString);
		}
		jedis.del("uid:" + userIDString + ":blocked");
		jedis.del("uid:" + userIDString + ":filter");
		jedis.zremrangeByRank("uid:" + userIDString + ":must", 0, -1);
		jedis.zremrangeByRank("uid:" + userIDString + ":matches", 0, -1);
		jedis.zremrangeByRank("uid:" + userIDString, 0, -1);	
	}
}
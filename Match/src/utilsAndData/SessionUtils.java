package utilsAndData;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {

	public static String getUserId(HttpServletRequest request) {
		Object sessionAttribute = request.getSession().getAttribute("userId");
		return sessionAttribute != null ? sessionAttribute.toString() : null;
	}

	public static void clearSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}

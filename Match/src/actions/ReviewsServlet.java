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
import utilsAndData.Review;
import utilsAndData.SessionUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class MessagesServlet
 */
@WebServlet("/reviews")
public class ReviewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public ReviewsServlet() {
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

			List<Review> reviewList = new ArrayList<Review>();
			reviewList = jdbc.listReviewsById(userId);

			List<ReviewToShow> reviewToShowList = new ArrayList<ReviewToShow>();
			for (Review review : reviewList) {
				reviewToShowList.add(new ReviewToShow(review));
			}
			jdbc.updateReviews(userId);

			try {
				System.out.println("try to show user data ");
				String jsonResponse = gson.toJson(reviewToShowList);
				System.out.println(jsonResponse);
				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
			}
		}
	}

	@SuppressWarnings("unused")
	class ReviewToShow {
		// private String userName;
		private Long reviewerId;
		private String reviewerName;
		private String partnerPhoto;
		private String seenFlag;

		private ReviewToShow(Review review) {
			this.reviewerId = review.getReviewerId();
			this.reviewerName = jdbc
					.retrievalOfUserById(review.getReviewerId()).getFirstName();
			this.partnerPhoto = "PhotoServlet?id=" + review.getReviewerId() + "";

			if (review.getSeenFlag()) {
				this.seenFlag = "";
			} else {
				this.seenFlag = "new";
			}

		}
	}

}

package utilsAndData;

public class UserAndScore {
	private User user;
	private double score;

	public UserAndScore() {

	}

	public UserAndScore(User user, double score) {
		this.user = user;
		this.score = score;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}

package utilsAndData;

public class Review {
	private long userId;
	private long reviewerId;
	private boolean seenFlag;

	public Review() {

	}

	public Review(long userId, long reviewerId, boolean seenFlag) {
		this.userId = userId;
		this.reviewerId = reviewerId;
		this.seenFlag = seenFlag;

	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(long reviewerId) {
		this.reviewerId = reviewerId;
	}

	public boolean getSeenFlag() {
		return seenFlag;
	}

	public void setSeenFlag(boolean seenFlag) {
		this.seenFlag = seenFlag;
	}
}

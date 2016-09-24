package utilsAndData;

public class Message {
	private long userId;
	private long secondPartyId;
	private String content;
	private boolean seenFlag = false;
	private String timeStamp;

	public Message() {
	}

	public Message(long userId, long secondPartyId) {
		this.userId = userId;
		this.secondPartyId = secondPartyId;

	}

	public Message(long userId, long secondPartyId, String content) {
		this.userId = userId;
		this.secondPartyId = secondPartyId;
		this.content = content;

	}

	public long getSecondPartyId() {
		return secondPartyId;
	}

	public void setSecondPartyId(long secondPartyId) {
		this.secondPartyId = secondPartyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getSeenFlag() {
		return seenFlag;
	}

	public void setSeenFlag(boolean seenFlag) {
		this.seenFlag = seenFlag;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
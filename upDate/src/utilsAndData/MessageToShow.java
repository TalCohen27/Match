package utilsAndData;

public @SuppressWarnings("unused")
class MessageToShow {
	private Long secondPartyId;
	private String secondPartyName;
	private String content;
	private boolean seenFlag;
	private String inOut; // incoming message or outcoming message
	private String partnerPhoto;
	private String timeStamp;

	public MessageToShow(JDBC jdbc, Message message, Long userId) {
		if (userId == message.getUserId()) {
			this.secondPartyName = jdbc.retrievalOfUserById(
					message.getSecondPartyId()).getFirstName();
			this.secondPartyId = message.getSecondPartyId();
			this.inOut = "sent";
			this.partnerPhoto = "PhotoServlet?id=" + message.getSecondPartyId()
					+ "";

		} else {
			this.secondPartyName = jdbc
					.retrievalOfUserById(message.getUserId()).getFirstName();
			this.secondPartyId = message.getUserId();
			this.inOut = "received";
			this.partnerPhoto = "PhotoServlet?id=" + message.getUserId() + "";
		}

		this.content = message.getContent();
		this.seenFlag = message.getSeenFlag();
		this.timeStamp = message.getTimeStamp();

	}
}
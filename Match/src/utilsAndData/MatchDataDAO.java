package utilsAndData;

import java.util.List;

public interface MatchDataDAO {

	/**
	 * This is a method for creating the database should be done only once.
	 */
	// public void createDB();
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	// public void setDataSource(DataSource ds);
	/**
	 * This is the method to be used to create a table for a specific location
	 */
	public void createTables();

	/**
	 * This is the method to be insert a row into the table
	 */
	public void insertToUSer(User user);

	/**
	 * This is the method to be used to list down all the records from the above
	 * table.
	 */
	// public List<MatchData> listMatchDatas();

	/**
	 * This is the method to be used to delete a record from the above table
	 * corresponding to specific params
	 */
	public void deleteFromUser(long id);

	public void deleteFromMessages(long userId, long secondPartyId);

	// public List<Review> listReviewsById();
	// public List<Message> listMessagesByUser();
	public List<User> listUsers();


	public void insertToMessages(Message message);

}

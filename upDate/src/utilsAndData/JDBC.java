package utilsAndData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import snaq.db.ConnectionPool;

public class JDBC implements MatchDataDAO {
	// TODO: change to global
	private String tableUserName;
	private String tableMessagesName;
	private String tableReviewsName;
	private String tableBlocksName;
	private String tableImagesName;
	private String tableReportsName;

	private ConnectionPool pool;

	public JDBC() {
		pool = CopyOfDBConn.getPool(); // TODO: change name of class
		tableUserName = "user";
		tableMessagesName = "messages";
		tableReviewsName = "reviews";
		tableBlocksName = "blocks";
		tableImagesName = "images";
		tableReportsName = "reports";
	}

	/**
	 * This is the method to be used to create a tables for web service
	 */
	public void createTables() {
		String SQL = " CREATE TABLE IF NOT EXISTS user ";
		SQL += " ( ";
		SQL += " id BIGINT NOT NULL, ";
		SQL += " first VARCHAR(100), ";
		SQL += " last VARCHAR(100), ";
		SQL += " photo VARCHAR(100) DEFAULT 'profile.PNG', ";
		SQL += " about_me VARCHAR(2000), ";
		SQL += " password VARCHAR(100) NOT NULL, ";
		SQL += " email VARCHAR(200) NOT NULL, ";
		SQL += " birthday DATE, ";
		SQL += " sex VARCHAR(10), ";
		SQL += " education VARCHAR(20), ";
		SQL += " area VARCHAR(20), ";
		SQL += " language VARCHAR(20), ";
		SQL += " marital_status VARCHAR(20), ";
		SQL += " children INT, ";
		SQL += " eye_color VARCHAR(20), ";
		SQL += " hair_type VARCHAR(20), ";
		SQL += " hair_color VARCHAR(20), ";
		SQL += " origin VARCHAR(20), ";
		SQL += " height INT, ";
		SQL += " weight INT, ";
		SQL += " earning_level VARCHAR(20), ";
		SQL += " religion VARCHAR(20), ";
		SQL += " smoking VARCHAR(20), ";
		SQL += " physique VARCHAR(20), ";
		SQL += " intrested_in VARCHAR(20), ";
		SQL += " last_login TIMESTAMP, ";

		SQL += " partner_education VARCHAR(20), ";
		SQL += " partner_area VARCHAR(20), ";
		SQL += " partner_language VARCHAR(20), ";
		SQL += " partner_marital_status VARCHAR(20), ";
		SQL += " partner_eye_color VARCHAR(20), ";
		SQL += " partner_hair_type VARCHAR(20), ";
		SQL += " partner_hair_color VARCHAR(20), ";
		SQL += " partner_origin VARCHAR(20), ";
		SQL += " partner_height_min INT, ";
		SQL += " partner_height_max INT, ";
		SQL += " partner_religion VARCHAR(20), ";
		SQL += " partner_smoking VARCHAR(20), ";
		SQL += " partner_earning_level VARCHAR(20), ";
		SQL += " partner_physique VARCHAR(20), ";
		SQL += " partner_age_min INT, ";
		SQL += " partner_age_max INT, ";

		SQL += " privacy_level INT DEFAULT 0, ";

		SQL += " PRIMARY KEY (id) ";
		SQL += " ) ";
		SQL += " ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin; ";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			st.executeUpdate(SQL);
			System.out.println("Created the table = user");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		SQL = " CREATE TABLE IF NOT EXISTS reviews ";
		SQL += " ( ";
		SQL += " user_id BIGINT NOT NULL, ";
		SQL += " reviewer_id BIGINT NOT NULL, ";
		SQL += " seen_flag BOOLEAN DEFAULT 0, ";
		SQL += " activity_time DATETIME, ";
		SQL += " CONSTRAINT review_id PRIMARY KEY (user_id,reviewer_id)";
		SQL += " ) ";
		SQL += " ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin; ";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			st.executeUpdate(SQL);
			System.out.println("Created the table = reviews");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		SQL = " CREATE TABLE IF NOT EXISTS messages ";
		SQL += " ( ";
		SQL += " user_id BIGINT NOT NULL, ";
		SQL += " second_party_id BIGINT NOT NULL, ";
		SQL += " content VARCHAR(4000), ";
		SQL += " seen_flag BOOLEAN DEFAULT 0, ";
		SQL += " activity_time DATETIME NOT NULL, ";
		SQL += " CONSTRAINT mes_id PRIMARY KEY (user_id, second_party_id, activity_time)";
		SQL += " ) ";
		SQL += " ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin; ";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			st.executeUpdate(SQL);
			System.out.println("Created the table = messages");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		SQL = " CREATE TABLE IF NOT EXISTS blocks ";
		SQL += " ( ";
		SQL += " blocker_id BIGINT NOT NULL, ";
		SQL += " blocked_id BIGINT NOT NULL, ";
		SQL += " ban_reason INT, "; // 0 = blocked, 1 = admin banned becoz
									// of an image, 2 = user reported to the
									// admin.
		SQL += " other VARCHAR(2000), ";
		SQL += " CONSTRAINT blocks_id PRIMARY KEY (blocker_id,blocked_id)";
		SQL += " ) ";
		SQL += " ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin; ";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			st.executeUpdate(SQL);
			System.out.println("Created the table = blocks");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		SQL = " CREATE TABLE IF NOT EXISTS images ";
		SQL += " ( ";
		SQL += " user_id BIGINT NOT NULL, ";
		SQL += " image LONGBLOB, ";
		SQL += " adminFlag BOOLEAN DEFAULT 0, ";
		SQL += " activity_time TIMESTAMP NOT NULL, ";
		SQL += " isBanned BOOLEAN DEFAULT 0, ";
		SQL += " PRIMARY KEY (user_id) ";
		SQL += " ) ";
		SQL += " ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin; ";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			st.executeUpdate(SQL);
			System.out.println("Created the table = images");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		SQL = " CREATE TABLE IF NOT EXISTS reports ";
		SQL += " ( ";
		SQL += " reporter_id BIGINT NOT NULL, ";
		SQL += " reported_id BIGINT NOT NULL, ";
		SQL += " content VARCHAR(2000), ";
		SQL += " seen_flag BOOLEAN DEFAULT 0, ";
		SQL += " activity_time TIMESTAMP NOT NULL, ";
		SQL += " CONSTRAINT reports_id PRIMARY KEY (reporter_id, reported_id, activity_time)";
		SQL += " ) ";
		SQL += " ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin; ";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			st.executeUpdate(SQL);
			System.out.println("Created the table = reports");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		System.out.println("Created the tables succesfully");
		/*  insertToImages(new File("/C:/Users/User/Desktop/images/profile.png") ,-1);
          insertToImages(new File("/C:/Users/User/Desktop/images/10.jpg") ,1);
          insertToImages(new File("/C:/Users/User/Desktop/images/1.jpg") ,2);
		  insertToImages(new File("/C:/Users/User/Desktop/images/2.jpg") ,3);
		  insertToImages(new File("/C:/Users/User/Desktop/images/3.jpg") ,4);
		  insertToImages(new File("/C:/Users/User/Desktop/images/4.jpg") ,5);
		  insertToImages(new File("/C:/Users/User/Desktop/images/5.jpg") ,6);
		  insertToImages(new File("/C:/Users/User/Desktop/images/6.jpg") ,7);
		  insertToImages(new File("/C:/Users/User/Desktop/images/7.jpg") ,8);
		  insertToImages(new File("/C:/Users/User/Desktop/images/8.jpg") ,9);
		  insertToImages(new File("/C:/Users/User/Desktop/images/9.jpg") ,10);*/
		 
		// updateImages(new File("/C:/Users/User/Desktop/q.jpg") ,1);

		return;
	}

	public void insertToBlocks(long blocker, long blocked, int reason,
			String otherBanReason) {
		String SQL = "INSERT INTO " + tableBlocksName
				+ " (`blocker_id`,`blocked_id`,`ban_reason`,`other`)"
				+ " VALUES (?,?,?,?);";
		System.out.println("insert SQL=" + SQL);

		try (Connection con = pool.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(SQL)) {
			preparedStatement.setLong(1, blocker);
			preparedStatement.setLong(2, blocked);
			preparedStatement.setInt(3, reason);
			preparedStatement.setString(4, otherBanReason);
			preparedStatement.executeUpdate();

			System.out.println("Insert Record");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return;
	}

	public void insertToReports(Report report) {
		String SQL = "INSERT INTO " + tableReportsName
				+ " (`reporter_id`,`reported_id`,`content`,`seen_flag`)"
				+ " VALUES (?,?,?,?);";
		System.out.println("insert SQL=" + SQL);
		try (Connection con = pool.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(SQL)) {
			preparedStatement.setLong(1, report.getUserId());
			preparedStatement.setLong(2, report.getReported());
			preparedStatement.setString(3, report.getContent());
			preparedStatement.setBoolean(4, report.getSeenFlag());
			preparedStatement.executeUpdate();

			System.out.println("Insert Record");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return;
	}

	public void insertToUSer(User user) {

		String SQL = "INSERT INTO "
				+ tableUserName
				+ " (`id`, `first`,`last`, `photo`, `about_me`,`password`, `email`, `birthday`, `sex`, "
				+ "`education`, `area`, `language`, `marital_status`, `children`, `eye_color`, `hair_type`, `hair_color`, `origin`, `height`, "
				+ "`weight`, `earning_level`, `religion`, `smoking`, `physique`, `intrested_in`, `partner_education`, `partner_area`, `partner_language`"
				+ ", `partner_marital_status`, `partner_eye_color`, `partner_hair_type`, `partner_hair_color`, `partner_origin`, `partner_height_min`, `partner_height_max`"
				+ ", `partner_religion`, `partner_smoking`, `partner_earning_level`, `partner_physique`, `partner_age_min`, `partner_age_max`,`privacy_level` )"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		System.out.println("insert SQL=" + SQL);

		try (Connection con = pool.getConnection();
		   PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

			preparedStatement.setLong(1, user.getId());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getPhoto());
			preparedStatement.setString(5, user.getAboutMe());
			preparedStatement.setString(6, user.getPassword());
			preparedStatement.setString(7, user.getEmail());
			preparedStatement.setDate(8, user.getBirthday());
			preparedStatement.setString(9, user.getSex());
			preparedStatement.setString(10, user.getEducation());
			preparedStatement.setString(11, user.getArea());
			preparedStatement.setString(12, user.getLanguage());
			preparedStatement.setString(13, user.getMaritalStatus());
			preparedStatement.setInt(14, user.getChildren());
			preparedStatement.setString(15, user.getEyeColor());
			preparedStatement.setString(16, user.getHairType());
			preparedStatement.setString(17, user.getHairColor());
			preparedStatement.setString(18, user.getOrigin());
			preparedStatement.setInt(19, user.getHeight());
			preparedStatement.setInt(20, user.getWeight());
			preparedStatement.setString(21, user.getEarningLevel());
			preparedStatement.setString(22, user.getReligion());
			preparedStatement.setString(23, user.getSmoking());
			preparedStatement.setString(24, user.getPhysique());
			preparedStatement.setString(25, user.getIntrestedIn());

			preparedStatement.setString(26, user.getPartner_education());
			preparedStatement.setString(27, user.getPartner_area());
			preparedStatement.setString(28, user.getPartner_language());
			preparedStatement.setString(29, user.getPartner_maritalStatus());
			preparedStatement.setString(30, user.getPartner_eyeColor());
			preparedStatement.setString(31, user.getPartner_hairType());
			preparedStatement.setString(32, user.getPartner_hairColor());
			preparedStatement.setString(33, user.getPartner_origin());
			preparedStatement.setInt(34, user.getPartnerMinHeight());
			preparedStatement.setInt(35, user.getPartnerMaxHeight());
			preparedStatement.setString(36, user.getPartner_religion());
			preparedStatement.setString(37, user.getPartner_smoking());
			preparedStatement.setString(38, user.getPartner_earningLevel());
			preparedStatement.setString(39, user.getPartner_physique());
			preparedStatement.setInt(40, user.getPartnerMinAge());
			preparedStatement.setInt(41, user.getPartnerMaxAge());
			preparedStatement.setInt(42, user.getPrivacyLevel());

			preparedStatement.executeUpdate();

			System.out.println("Insert Record");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return;
	}

	public void insertToMessages(Message message) {

		String SQL = "INSERT INTO " + tableMessagesName
				+ " (`user_id`,`second_party_id`,`content`, `seen_flag`, activity_time)"
				+ " VALUES (?,?,?,?, now());";
		System.out.println("insert SQL=" + SQL);

		try (Connection con = pool.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

			preparedStatement.setLong(1, message.getUserId());
			preparedStatement.setLong(2, message.getSecondPartyId());
			preparedStatement.setString(3, message.getContent());
			preparedStatement.setBoolean(4, message.getSeenFlag());
			preparedStatement.executeUpdate();

			System.out.println("Insert Record");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return;
	}


	public void insertNewReview(long uId, long reviewerId) {

		String SQL = "INSERT INTO " + tableReviewsName
				+ " (`user_id`,`reviewer_id`,`seen_flag`, activity_time)" + " VALUES (?,?,?, now());";
		System.out.println("insert SQL=" + SQL);

		try (Connection con = pool.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

			preparedStatement.setLong(1, uId);
			preparedStatement.setLong(2, reviewerId);
			preparedStatement.setBoolean(3, false); // not seen
			preparedStatement.executeUpdate();

			System.out.println("Insert Record");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return;
	}

	public void updateReviews(Long userid) {

		String SQL = "UPDATE " + tableReviewsName
				+ " SET seen_flag=1 WHERE user_id=" + userid
				+ " and seen_flag=0;";
		System.out.println("insert SQL=" + SQL);

		try (Connection con = pool.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

			preparedStatement.executeUpdate();
			System.out.println("Update Record");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return;
	}

	public void updateUser(long id, String colunmnName, String value) {
		String sql = "UPDATE " + tableUserName + " SET " + colunmnName + " = '"
				+ value + "' WHERE id = '" + id + "\'";

		System.out.println("UPDATE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int update = st.executeUpdate(sql);

			if (update == 1) {
				System.out.println("Row:id " + id + " is updated.");
			} else {
				System.out.println("Row:id " + id + " is not updated.");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public User retrievalOfUserById(long id) {
		User user = new User();

		String SQL = "select* from " + tableUserName + " WHERE id = " + id + "";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {

				rs.next();
				user = userMapRow(rs);

			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return user;
	}

	public User retrievalOfUserByEmail(String email) {
		User user = new User();

		String SQL = "select * from " + tableUserName + " WHERE email = '"
				+ email + "\'";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {

				rs.next();
				user = userMapRow(rs);

			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return user;
	}

	public String restoreUserPassword(long id) {
		User user = new User();
		user = retrievalOfUserById(id);
		return user.getPassword();
	}

	public User loginCheck(String email, String password) {
		User user = new User();

		if (checkIfEmailExists(email)) {
			user = retrievalOfUserByEmail(email);
			if (user.getPassword().equalsIgnoreCase(password))
				return user;
			else
				return null;
		} else
			return null;
	}

	public boolean checkIfEmailExists(String email) {
		String SQL = "select * from " + tableUserName + " WHERE email = '"
				+ email + "\'";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				if (rs.next()) {
					return true;
				} else
					return false;
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return false;
	}

	public List<User> listUsers() {
		List<User> userList = new ArrayList<User>();
		String SQL = "select * from " + tableUserName;

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {

				for (; rs.next();) {
					userList.add(userMapRow(rs));
				}

			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return userList;
	}

	public List<User> listOfBlockedUsersById(long id) {
		List<User> blockeduserList = new ArrayList<User>();
		String SQL = "select * from " + tableBlocksName
				+ " WHERE blocker_id = " + id;

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					blockeduserList.add(retrievalOfUserById(rs
							.getLong("blocked_id")));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return blockeduserList;
	}

	public List<User> listOfBlockersOfUserById(long id) {
		List<User> blockeduserList = new ArrayList<User>();
		String SQL = "select * from " + tableBlocksName
				+ " WHERE blocked_id = " + id;

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					blockeduserList.add(retrievalOfUserById(rs
							.getLong("blocker_id")));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return blockeduserList;
	}

	public List<Report> RetrieveListOfReports() {
		List<Report> reportsList = new ArrayList<Report>();
		String SQL = "select * from " + tableReportsName;
		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					reportsList.add(ReportMapRow(rs));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return reportsList;
	}

	public Report ReportMapRow(ResultSet rs) throws SQLException {
		Report resultReport = new Report();
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs
				.getTimestamp("activity_time"));
		resultReport.setUserId(rs.getLong("reporter_id"));
		resultReport.setReported(rs.getLong("reported_id"));
		resultReport.setContent(rs.getString("content"));
		resultReport.setSeenFlag(rs.getBoolean("seen_flag"));
		resultReport.setTimeStamp(timeStamp);
		resultReport.setSecondPartyId(Consts.ADMIN_ID);
		System.out.println("reportMapRow");

		return resultReport;
	}

	public boolean isBlockedUser(long blocker, long blocked) {
		boolean isSelectedRow = false;
		String SQL = "select * from " + tableBlocksName
				+ " WHERE blocker_id = " + blocker + " AND blocked_id = "
				+ blocked;

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				if (rs.next()) {
					isSelectedRow = true;
				}
				System.out.println("isBlockedUser: " + isSelectedRow);
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return isSelectedRow;
	}

	public boolean isBannedUser(long userID) {
		boolean isSelectedRow = false;
		String SQL = "select * from " + tableBlocksName
				+ " WHERE blocker_id = " + 0 + " AND blocked_id = " + userID;
		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				if (rs.next()) {
					isSelectedRow = true;
				}
				System.out.println("isBannedUser: " + isSelectedRow);
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return isSelectedRow;
	}

	public List<User> SearchUsersBySex(String sex) {
		List<User> userList = new ArrayList<User>();
		String SQL = "select * from " + tableUserName + " WHERE sex = '" + sex
				+ "'";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					userList.add(retrievalOfUserById(rs.getLong("id")));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return userList;
	}

	public List<User> SearchUsersByName(String first) {
		List<User> nameUserList = new ArrayList<User>();
		String SQL = "select * from " + tableUserName + " WHERE first LIKE '%"
				+ first + "%' OR last LIKE '%" + first + "%'";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					nameUserList.add(retrievalOfUserById(rs.getLong("id")));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return nameUserList;
	}

	private User userMapRow(ResultSet rs) throws SQLException {
		User userData = new User();
		userData.setId(rs.getLong("id"));
		userData.setFirstName(rs.getString("first"));
		userData.setLastName(rs.getString("last"));

		if (rs.getString("photo") == null) {
			userData.setPhoto(User.DEFAULT_PHOTO);
		} else {
			userData.setPhoto(rs.getString("photo"));
		}

		userData.setAboutMe(rs.getString("about_me"));
		userData.setPassword(rs.getString("password"));
		userData.setEmail(rs.getString("email"));
		userData.setBirthday(rs.getDate("birthday"));
		userData.setSex(rs.getString("sex"));
		userData.setEducation(rs.getString("education"));
		userData.setArea(rs.getString("area"));
		userData.setLanguage(rs.getString("language"));
		userData.setMaritalStatus(rs.getString("marital_status"));
		userData.setChildren(rs.getInt("children"));
		userData.setEyeColor(rs.getString("eye_color"));
		userData.setHairType(rs.getString("hair_type"));
		userData.setHairColor(rs.getString("hair_color"));
		userData.setOrigin(rs.getString("origin"));
		userData.setHeight(rs.getInt("height"));
		userData.setWeight(rs.getInt("weight"));
		userData.setEarningLevel(rs.getString("earning_level"));
		userData.setReligion(rs.getString("religion"));
		userData.setSmoking(rs.getString("smoking"));
		userData.setPhysique(rs.getString("physique"));
		userData.setIntrestedIn(rs.getString("intrested_in"));

		userData.setPartner_education(rs.getString("partner_education"));
		userData.setPartner_area(rs.getString("partner_area"));
		userData.setPartner_language(rs.getString("partner_language"));
		userData.setPartner_maritalStatus(rs
				.getString("partner_marital_status"));
		userData.setPartner_eyeColor(rs.getString("partner_eye_color"));
		userData.setPartner_hairType(rs.getString("partner_hair_type"));
		userData.setPartner_hairColor(rs.getString("partner_hair_color"));
		userData.setPartner_origin(rs.getString("partner_origin"));
		userData.setPartnerMinHeight(rs.getInt("partner_height_min"));
		userData.setPartnerMaxHeight(rs.getInt("partner_height_max"));
		userData.setPartner_religion(rs.getString("partner_religion"));
		userData.setPartner_smoking(rs.getString("partner_smoking"));
		userData.setPartner_earningLevel(rs.getString("partner_earning_level"));
		userData.setPartner_physique(rs.getString("partner_physique"));
		userData.setPartnerMinAge(rs.getInt("partner_age_min"));
		userData.setPartnerMaxAge(rs.getInt("partner_age_max"));
		userData.setPrivacyLevel(rs.getInt("privacy_level"));


	//	System.out.println("userMapRow");

		return userData;
	}

	public List<Message> listMessagesByUser(long id) {
		List<Message> messageList = new ArrayList<Message>();
		String SQL = "select * from " + tableMessagesName
				+ " WHERE user_id = '" + id + "\'";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					messageList.add(messageMapRow(rs));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return messageList;
	}

	public List<Long> retrieveAllSecondPartyIdOfUserFromMessages(
			List<Message> messageList) {
		List<Long> secondPartyIdList = new ArrayList<Long>();
		for (Message message : messageList) {
			if (!secondPartyIdList.contains(message.getSecondPartyId())) {
				secondPartyIdList.add(message.getSecondPartyId());
			}
		}
		return secondPartyIdList;
	}

	public List<Message> listMessagesByTwoUsers(long userId, long secondPartyId) {
		List<Message> messageList = new ArrayList<Message>();
		String sql = "select * from " + tableMessagesName
				+ " WHERE user_id in (?, ?) AND second_party_id IN (?, ?) "
				+ " ORDER BY activity_time";

		try (Connection con = pool.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {

			int count = 0;
			st.setLong(++count, userId);
			st.setLong(++count, secondPartyId);
			st.setLong(++count, userId);
			st.setLong(++count, secondPartyId);
			try (ResultSet rs = st.executeQuery()) {
				for (; rs.next();) {
					messageList.add(messageMapRow(rs));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		
		markSeenMessages(userId, secondPartyId);

		return messageList;
	}

	private void markSeenMessages(long userId, long secondPartyId) {
		String sql = "UPDATE " + tableMessagesName + " SET seen_flag = 1 "
				+ " where user_id = ? and second_party_id = ? and seen_flag = 0";
		try (Connection con = pool.getConnection();
				PreparedStatement st = con.prepareStatement(sql )) {

			int count = 0;
			st.setLong(++count, secondPartyId);
			st.setLong(++count, userId);
			
			st.execute();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private Message messageMapRow(ResultSet rs) throws SQLException {
		Message messageData = new Message();
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs
				.getTimestamp("activity_time"));
		messageData.setUserId(rs.getLong("user_id"));
		messageData.setSecondPartyId(rs.getLong("second_party_id"));
		messageData.setContent(rs.getString("content"));
		messageData.setSeenFlag(rs.getBoolean("seen_flag"));
		messageData.setTimeStamp(timeStamp);

		//System.out.println("userMapRow");

		return messageData;
	}

	public List<Message> listMessagesGroupedByTimestamp(long userId) {
		List<Message> messageList = new ArrayList<Message>();

		String SQL = " select k.* "
				+ " from "
				+ tableMessagesName
				+ " k, (select auser, withuser, max(activity_time) datesent "
				+ "		 			  from (select user_id as auser, second_party_id as withuser, activity_time "
				+ "					 		from "
				+ tableMessagesName
				+ "					 		union "
				+ "					 		select second_party_id as auser, user_id as withuser, activity_time "
				+ "					 		from " + tableMessagesName + ") as ud "
				+ "			 		  group by auser , withuser) as temp "
				+ "	where	k.user_id = temp.auser "
				+ " and 	k.second_party_id = temp.withuser "
				+ " and 	k.activity_time = temp.datesent "
				+ " and 	(k.user_id = " + userId + " or k.second_party_id = "
				+ userId + ") " + " order by k.activity_time desc ";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					messageList.add(messageMapRow(rs));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return messageList;
	}

	public List<Review> listReviewsById(long id) {
		List<Review> reviewList = new ArrayList<Review>();
		String SQL = "select * from " + tableReviewsName + " WHERE user_id = '"
				+ id + "\'";

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					reviewList.add(reviewMapRow(rs));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return reviewList;
	}

	public List<Long> retrieveAllReviewerIdOfUserFromReviews(
			List<Review> reviewList) {
		List<Long> reviewerIdList = new ArrayList<Long>();
		for (Review review : reviewList) {
			if (!reviewerIdList.contains(review.getReviewerId())) {
				reviewerIdList.add(review.getReviewerId());
			}
		}
		return reviewerIdList;
	}

	private Review reviewMapRow(ResultSet rs) throws SQLException {
		Review reviewData = new Review();
		reviewData.setUserId(rs.getLong("user_id"));
		reviewData.setReviewerId(rs.getLong("reviewer_id"));
		reviewData.setSeenFlag(rs.getBoolean("seen_flag"));

	//	System.out.println("userMapRow");

		return reviewData;
	}

	public void deleteFromUser(long id) {
		String sql = "DELETE FROM " + tableUserName + " WHERE id = '" + id
				+ "\'";

		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete == 1) {
				System.out.println("Row:id" + id + " is deleted.");
			} else {
				System.out.println("Row:id" + id + "is not deleted.");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void changeSeenFlag(long reporterId, long reportedId,
			String timeStamp) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.util.Date date = (java.util.Date) formatter.parse(timeStamp);
		Timestamp activityTime = new Timestamp(date.getTime());

		String sql = "UPDATE " + tableReportsName + " SET " + " seen_flag "
				+ " = '" + 1 + "' WHERE reporter_id = '" + reporterId + "'AND "
				+ "reported_id = '" + reportedId + "'AND "
				+ "activity_time = '" + activityTime + "\'";
		System.out.println("UPDATE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int update = st.executeUpdate(sql);
			if (update == 1) {
				System.out.println("Row:id " + reporterId + " is updated.");
			} else {
				System.out.println("Row:id " + reporterId + " is not updated.");
			}
		} catch (SQLException e) {
			System.out.println("SQL statement: updating" + "id=" + reporterId
					+ " of column " + " activity_time " + " to value " + 1
					+ " is not executed!");
			e.printStackTrace();
		}
	}

	public void deleteFromReports(long reporterId, long reportedId) {
		String sql = "DELETE FROM " + tableReportsName
				+ " WHERE reporter_id = '" + reporterId + "'AND "
				+ "reported_id = '" + reportedId + "\'";
		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete == 1) {
				System.out.println("Row:id" + reporterId + " is deleted.");
			} else {
				System.out.println("Row:id" + reporterId + "is not deleted.");
			}
		} catch (SQLException e) {
			System.out.println("SQL statement: deleteing" + "id=" + reporterId
					+ "is not executed!");
			e.printStackTrace();
		}
	}

	public void deleteFromBlocks(long blocker, long blocked) {
		String sql = "DELETE FROM " + tableBlocksName + " WHERE blocker_id = '"
				+ blocker + "'AND " + "blocked_id = '" + blocked + "\'";

		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete == 1) {
				System.out.println("Row:id" + blocker + " is deleted.");
			} else {
				System.out.println("Row:id" + blocker + "is not deleted.");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void deleteFromBlocksByOneId(long id) {
		String sql = "DELETE FROM " + tableBlocksName + " WHERE blocker_id = '"
				+ id + "' OR " + "blocked_id = '" + id + "\'";

		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete >= 1) {
				System.out.println("Blocks rows with id: " + id
						+ " are deleted.");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void deleteFromMessages(long userId, long secondPartyId) {
		String sql = "DELETE FROM " + tableMessagesName + " WHERE user_id = '"
				+ userId + "\'AND second_party_id = '" + secondPartyId + "\' ";

		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete == 1) {
				System.out.println("Row:id" + userId + "and second id"
						+ secondPartyId + " is deleted.");
			} else {
				System.out.println("Row:id" + userId + "and second id"
						+ secondPartyId + " is not deleted.");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void deleteFromMessagesByOneId(long id) {
		String sql = "DELETE FROM " + tableMessagesName + " WHERE user_id = '"
				+ id + "\' OR second_party_id = '" + id + "\' ";

		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete >= 1) {
				System.out.println("Messages rows with id: " + id
						+ " are deleted.");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void deleteFromReviews(long userId, long reviewerId) {
		String sql = "DELETE FROM " + tableReviewsName + " WHERE user_id = '"
				+ userId + "\'AND reviewer_id = '" + reviewerId + "\' ";

		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete == 1) {
				System.out.println("Row:id" + userId + "and second id"
						+ reviewerId + " is deleted.");
			} else {
				System.out.println("Row:id" + userId + "and second id"
						+ reviewerId + " is not deleted.");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void deleteFromReviewsByOneId(long id) {
		String sql = "DELETE FROM " + tableReviewsName + " WHERE user_id = '"
				+ id + "\' OR reviewer_id = '" + id + "\' ";

		System.out.println("DELETE action, SQL=" + sql);

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int delete = st.executeUpdate(sql);

			if (delete >= 1) {
				System.out.println("Reviews rows with id: " + id
						+ " are deleted.");
			}
		} catch (SQLException ex) {
			System.out
					.println("SQL statement: deleteing from reviews by user id="
							+ id + " not executed!");
			ex.printStackTrace();
		}
	}

	public void deleteProfile(long userId) {
		deleteFromUser(userId);
		deleteFromBlocksByOneId(userId);
		deleteFromMessagesByOneId(userId);
		deleteFromReviewsByOneId(userId);
	}

	public BufferedImage imageById(long userId) {
		String SQL = "select * from " + tableImagesName + " WHERE user_id = '"
				+ userId + "\'";
		System.out.println("Photo action, SQL=" + SQL);

		BufferedImage im = null;
		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				// InputStream stream = rs.getBinaryStream(2);
				rs.next();
				im = ImageIO.read(rs.getBinaryStream(2));

			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return im;

	}

	public void updateImages(File image, int id) {
		String SQL = "DELETE from " + tableImagesName + " WHERE user_id=" + id
				+ ";";

		System.out.println("insert SQL=" + SQL);

		try (Connection con = pool.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(SQL)) {
			preparedStatement.executeUpdate();
			System.out.println("Delete Record");

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		insertToImages(image, id);
		return;
	}

	public void insertToImages(File image, int id) {

		String SQL = "INSERT INTO " + tableImagesName + " (`user_id`,`image`,`adminFlag`)"
				+ " VALUES (?,?,?);";
		System.out.println("insert SQL=" + SQL);

		try (Connection con = pool.getConnection();

		FileInputStream fis = new FileInputStream(image);

		PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

			preparedStatement.setLong(1, id);
			preparedStatement.setBinaryStream(2, (InputStream) fis,
					(int) (image.length()));
			preparedStatement.setBoolean(3, false);
			preparedStatement.executeUpdate();

			System.out.println("Insert Record");

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		return;
	}

	public void updateLastLogin(Long userId) {
		String SQL = "UPDATE " + tableUserName
				+ " SET last_login = now() WHERE id=?";
	
		try (Connection con = pool.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

			preparedStatement.setLong(1, userId);
			preparedStatement.executeUpdate();
			//System.out.println("updateLastLogin finished");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return;
	}

	public Timestamp getLastLogin(Long userId) {
		String SQL = "select last_login from " + tableUserName + " WHERE id = '"
				+ userId + "\'";
	
		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				// InputStream stream = rs.getBinaryStream(2);
				rs.next();
				return rs.getTimestamp(1);

			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return null;
	}

	public Long getUnreadMessagesCount(Long userId) {
		String SQL = "select count(1) from " + tableMessagesName + " WHERE second_party_id = ? and seen_flag = 0";
	
		try (Connection con = pool.getConnection();
				PreparedStatement st = con.prepareStatement(SQL)) {
			
			st.setLong(1, userId);
			try (ResultSet rs = st.executeQuery()) {
				// InputStream stream = rs.getBinaryStream(2);
				rs.next();
				return rs.getLong(1);

			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return null;
	}
	
	public List<Image> RetrieveUsersForApproval()
	{
		List<Image> imageList = new ArrayList<Image>();
		//String SQL = "select* from " + tableUserName + " WHERE id = " + id + "";
		String SQL = "select * from " + tableImagesName + " WHERE adminFlag = " + 0 + "";
		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				for (; rs.next();) {
					imageList.add(ImageMapRow(rs));
				}
			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return imageList;
	}
	
	public Image ImageMapRow(ResultSet rs) throws SQLException{
		
		Image imageData = new Image();
		imageData.setAdminFlag(rs.getBoolean("adminFlag"));
		imageData.setId(rs.getLong("user_id"));
		imageData.setImage("PhotoServlet?id=" + rs.getLong("user_id") + "");

		return imageData;
	}
	
	public void imagesSetAdminFlag(long id)
	{
		String sql = "UPDATE " + tableImagesName + " SET " + " adminFlag "
				+ " = '" + 1 + "' WHERE user_id = '" + id + "\'";
		System.out.println("UPDATE action, SQL=" + sql);
		
		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {
			int update = st.executeUpdate(sql);
			if (update == 1) {
				System.out.println("Row:id " + id + " is updated.");
			} else {
				System.out.println("Row:id " + id + " is not updated.");
			}
		} catch (SQLException e) {
			System.out.println("SQL statement: updating" + "id=" + id
					+ " of column " + " adminFlag " + " to value " + 1
					+ " is not executed!");
			e.printStackTrace();
	}
	
}


	public BufferedImage getDefaultImage() {
		String SQL = "select * from " + tableImagesName + " WHERE user_id = '"
				+ (-1) + "\'";
		System.out.println("Photo action, SQL=" + SQL);

		BufferedImage im = null;
		try (Connection con = pool.getConnection();
				Statement st = con.createStatement()) {

			try (ResultSet rs = st.executeQuery(SQL)) {
				// InputStream stream = rs.getBinaryStream(2);
				rs.next();
				im = ImageIO.read(rs.getBinaryStream(2));

			} catch (SQLException sqlx) {
				System.err.println(sqlx.getMessage());
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return im;

	}

	public int getAdminFlag(long userId) {
			String SQL = "select * from " + tableImagesName + " WHERE user_id = '"
					+ userId + "\'";
			System.out.println("admin flag action, SQL=" + SQL);

			int adminFlag = -1;
			try (Connection con = pool.getConnection();
					Statement st = con.createStatement()) {

				try (ResultSet rs = st.executeQuery(SQL)) {
					rs.next();
					adminFlag = (rs.getInt(3));

				} catch (SQLException sqlx) {
					System.err.println(sqlx.getMessage());
				} 

			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}

			return adminFlag;

	}

}
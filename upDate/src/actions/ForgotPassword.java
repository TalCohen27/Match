package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilsAndData.JDBC;
import utilsAndData.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class ForgotPassword
 */
@WebServlet("/forgotpassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String username = "matchwebservice@gmail.com";
	private final String password = "findlove";
	private JDBC jdbc = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForgotPassword() {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/Manager").include(request,
				response);
		PrintWriter out = response.getWriter();
		String newPassword = request.getParameter("password");
		String userEmail = request.getParameter("email");
		String responce = null;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		if (jdbc.checkIfEmailExists(userEmail)) {

			User user = jdbc.retrievalOfUserByEmail(userEmail);
			if (newPassword != null && newPassword != "") {
				try {
					jdbc.updateUser(user.getId(), "password", newPassword);
					responce = "true";
				} catch (Exception e) {
					responce = "failed to update password";
					System.out.println("failed to update password");
				}
			} else {
				responce = "failed to update password";
			}

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("matchwebservice@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(userEmail));
				message.setSubject("Password Update");
				message.setText("Your new password is "
						+ newPassword
						+ ". Now you can register using your new password. Good luck!");

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		} else {
			responce = "email doesnt exists";
		}
		Gson gson = new Gson();
		try {
			String jsonResponse = gson.toJson(responce);
			System.out.println(jsonResponse);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}
	}
}

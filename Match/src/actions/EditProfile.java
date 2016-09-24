package actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import utilsAndData.JDBC;
import utilsAndData.SessionUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class EditProfile
 */
@WebServlet("/editprofile")
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public EditProfile() {
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
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	protected synchronized void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		PrintWriter out = response.getWriter();
		String userIdFromSession = SessionUtils.getUserId(request);
		long userId = 0;
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
		}

		else if (userIdFromSession != null) {

			try {
				userId = Long.parseLong(userIdFromSession);
			} catch (Exception e) {
				System.out.println("null session parameter");
			}

			String responce = "";
			getAndEditUserParameters(request, response, responce);

			try {
				Part filePart = request.getPart("file");
				if (filePart.getSize() != 0) {
					String fileType = filePart.getContentType();
					if (fileType.substring(0, 5).equalsIgnoreCase("image")) {
						System.out.println("type: " + fileType);
						
						InputStream inputStream = null;
						OutputStream outputStream = null;
						File img = new File("photo");
					 
						try {
							// read this file into InputStream
							inputStream = filePart.getInputStream();
							// write the inputStream to a FileOutputStream
							outputStream = new FileOutputStream(img);
					 
							int read = 0;
							byte[] bytes = new byte[1024];
					 
							while ((read = inputStream.read(bytes)) != -1) {
								outputStream.write(bytes, 0, read);
							}
					 
							System.out.println("Done!");
					 
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (inputStream != null) {
								try {
									inputStream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if (outputStream != null) {
								try {
									outputStream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
					 
							}
						}

						jdbc.updateImages(img, (int)userId);
						responce = "true";
					} else {
						responce = "The type of image is not valid. Please choose jpg or png type of photo";
					}
				}else {
						responce = "true";
				}
			} catch (Exception ex) {
				responce = "Server failed to upload photo, please try again";
				System.out.println(ex.getLocalizedMessage());
				throw new IOException(ex);
			}

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

	private void getAndEditUserParameters(final HttpServletRequest request,
			final HttpServletResponse response, final String responce) {

		long userId = Long.parseLong(SessionUtils.getUserId(request));
		jdbc.updateUser(userId, "privacy_level", request.getParameter("privacy_level"));

		jdbc.updateUser(userId, "first", request.getParameter("update_first"));
		jdbc.updateUser(userId, "last", request.getParameter("update_last"));
		jdbc.updateUser(userId, "about_me",
				request.getParameter("update_about_me"));
		jdbc.updateUser(userId, "birthday",
				request.getParameter("update_birthday"));
		jdbc.updateUser(userId, "sex", request.getParameter("update_sex"));
		jdbc.updateUser(userId, "education",
				request.getParameter("update_education"));
		jdbc.updateUser(userId, "area", request.getParameter("update_area"));
		jdbc.updateUser(userId, "language",
				request.getParameter("update_language"));
		jdbc.updateUser(userId, "marital_status",
				request.getParameter("update_marital_status"));
		jdbc.updateUser(userId, "children",
				request.getParameter("update_children"));
		jdbc.updateUser(userId, "eye_color",
				request.getParameter("update_eye_color"));
		jdbc.updateUser(userId, "hair_type",
				request.getParameter("update_hair_type"));
		jdbc.updateUser(userId, "hair_color",
				request.getParameter("update_hair_color"));
		jdbc.updateUser(userId, "origin", request.getParameter("update_origin"));
		jdbc.updateUser(userId, "height", request.getParameter("update_height"));
		jdbc.updateUser(userId, "weight", request.getParameter("update_weight"));
		jdbc.updateUser(userId, "earning_level",
				request.getParameter("update_earning_level"));
		jdbc.updateUser(userId, "religion",
				request.getParameter("update_religion"));
		jdbc.updateUser(userId, "smoking",
				request.getParameter("update_smoking"));
		jdbc.updateUser(userId, "physique",
				request.getParameter("update_physique"));
		jdbc.updateUser(userId, "intrested_in",
				request.getParameter("update_intrested_in"));

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// get current date time with Date()
		Date date = new Date();
		jdbc.updateUser(userId, "last_login", dateFormat.format(date));

		jdbc.updateUser(userId, "partner_education",
				request.getParameter("update_education2"));
		jdbc.updateUser(userId, "partner_area",
				request.getParameter("update_area2"));
		jdbc.updateUser(userId, "partner_language",
				request.getParameter("update_language2"));
		jdbc.updateUser(userId, "partner_marital_status",
				request.getParameter("update_marital_status2"));
		jdbc.updateUser(userId, "partner_eye_color",
				request.getParameter("update_eye_color2"));
		jdbc.updateUser(userId, "partner_hair_type",
				request.getParameter("update_hair_type2"));
		jdbc.updateUser(userId, "partner_hair_color",
				request.getParameter("update_hair_color2"));
		jdbc.updateUser(userId, "partner_origin",
				request.getParameter("update_origin2"));
		jdbc.updateUser(userId, "partner_height_min",
				request.getParameter("update_minheight2"));
		jdbc.updateUser(userId, "partner_height_max",
				request.getParameter("update_maxheight2"));
		jdbc.updateUser(userId, "partner_religion",
				request.getParameter("update_religion2"));
		jdbc.updateUser(userId, "partner_smoking",
				request.getParameter("update_smoking2"));
		jdbc.updateUser(userId, "partner_earning_level",
				request.getParameter("update_earning_level2"));
		jdbc.updateUser(userId, "partner_physique",
				request.getParameter("update_physique2"));
		jdbc.updateUser(userId, "partner_age_min",
				request.getParameter("update_min_age2"));
		jdbc.updateUser(userId, "partner_age_max",
				request.getParameter("update_max_age2"));
	}
}

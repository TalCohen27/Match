package actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/upload" })
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public void init() throws ServletException {
		Object temp = getServletContext().getAttribute("matchDataJDBCTemplate");
		if (temp != null) {
			jdbc = (JDBC) temp;
		} else {
			throw new ServletException(
					"matchDataJDBCTemplate is not initiated.");
		}
	}

	protected synchronized void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		PrintWriter out = response.getWriter();
		String userIdFromSession = SessionUtils.getUserId(request);
		long userId = 0;

		try {
			userId = Long.parseLong(userIdFromSession);
		} catch (Exception e) {
			System.out.println("null session parameter");
		}
		String responce = "";

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

					jdbc.insertToImages(img, (int) userId);
					responce = "true";
				} else {
					responce = "The type of image is not valid. Please choose jpg or png type of photo";
				}
			} else {
				responce = "Please choose your photo";
			}
		} catch (Exception ex) {
			responce = "Server failed to upload photo, please try again";
			System.out.println(ex.getLocalizedMessage());
			throw new IOException(ex);
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

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

}

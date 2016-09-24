package actions;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilsAndData.JDBC;
import utilsAndData.SessionUtils;

@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;

	public PhotoServlet() {
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
		System.out.println("IN processRequest of PhotoServlet");
		String userIdFromSession = SessionUtils.getUserId(request);
		long userId;
		BufferedImage image;
		if (userIdFromSession != null) {
			String id = request.getParameter("id");
			System.out.println("id = " + id);
			userId = Long.parseLong(id);
            if(jdbc.getAdminFlag(userId) == 0){
    		   image = jdbc.getDefaultImage();
             }
            else{
			   image = jdbc.imageById(userId);
            }
			if (image == null)
				System.out.println("image =  null!");
			// else{
			//
			// ByteArrayOutputStream os = new ByteArrayOutputStream();
			// ImageIO.write(image, "png", os);
			// InputStream is = new ByteArrayInputStream(os.toByteArray());
			// response.setContentType("image/jpeg");
			// ServletOutputStream out;
			// out = response.getOutputStream();
			// BufferedInputStream bin = new BufferedInputStream(is);
			// BufferedOutputStream bout = new BufferedOutputStream(out);
			// int ch =0;
			// while((ch=bin.read())!=-1)
			// {
			// bout.write(ch);
			// }
			//
			// bin.close();
			// is.close();
			// bout.close();
			// out.close();
			// }

			else {
				response.setContentType("image/jpeg");
				ServletOutputStream out;

				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "png", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				out = response.getOutputStream();
				int ch = 0;
				while ((ch = is.read()) != -1) {
					out.write(ch);
				}

				is.close();
				out.close();
			}
		}
	}

}

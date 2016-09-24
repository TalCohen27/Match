package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import utilsAndData.JDBC;
import utilsAndData.Message;
import utilsAndData.SessionUtils;
import utilsAndData.Image;

/**
 * Servlet implementation class ImageApprovalServlet
 */
@WebServlet("/ImageApprovalServlet")
public class ImageApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC jdbc = null;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageApprovalServlet() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			processRequest(request, response);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			processRequest(request, response);
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
	}
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, ParseException {
		
		Gson gson = new Gson();
		List<Image> imageList = null;
		PrintWriter out = response.getWriter();
		String userIdFromSession = SessionUtils.getUserId(request);
		String responce = "";
		//String userIdfromParam = request.getParameter("profileId");
	//	String isDelete = request.getParameter("isDelete");
		String isChangeFlag = request.getParameter("isChangeFlag");
		String isBan = request.getParameter("isBan");
		//String timeStamp = "";	
		long id;
		
		if (userIdFromSession == null) {
			 responce = "go to sign in";

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
		else
		{
			
			if(isChangeFlag.equals("true"))
			{
					
				if(isBan.equals("false"))
				{
					id = Long.parseLong(request.getParameter("profileId"));
					if(jdbc.isBannedUser(id))
					{
						jdbc.deleteFromBlocks(0, id);
					}
					
					jdbc.imagesSetAdminFlag(id);			
			
					}
				else
				{
			
					id = Long.parseLong(request.getParameter("profileId"));
					jdbc.insertToBlocks(0, id, 1, "");
					Message banMessage = new Message();
					banMessage.setSecondPartyId(id);
					banMessage.setContent("Change your photo!");
					banMessage.setUserId(0);
					jdbc.insertToMessages(banMessage);		
				}
			}
			
			imageList = jdbc.RetrieveUsersForApproval();
			
			try {
				System.out.println("report servlet request");
				String jsonResponse = gson.toJson(imageList);
				System.out.println(jsonResponse);
				out.print(jsonResponse);
				out.flush();
			} finally {
				out.close();
		}
		
	}
	}
}


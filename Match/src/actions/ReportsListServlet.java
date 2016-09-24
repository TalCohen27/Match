package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.catalina.manager.util.SessionUtils;





import utilsAndData.JDBC;
import utilsAndData.Message;
import utilsAndData.Report;
import utilsAndData.SessionUtils;

import com.google.gson.Gson;







import java.text.ParseException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class ReportsListServlet
 */
@WebServlet("/ReportsListServlet")
public class ReportsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JDBC jdbc = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsListServlet() {
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
		List<Report> reportsList = null;
		PrintWriter out = response.getWriter();
		String userIdFromSession = SessionUtils.getUserId(request);
		String responce = "";
		String isDelete = request.getParameter("isDelete");
		String isChangeFlag = request.getParameter("isChangeFlag");
		long reporterIdNum = 0;
		long reportedIdNum = 0;
		String reporterId = "";
		String reportedId = "";
		String timeStamp = "";
				
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
			if(isDelete.equals("true")){
			
				 String isBan = request.getParameter("isBan");
				 reporterId = request.getParameter("reporterId");
				 reportedId = request.getParameter("reportedId");
				 //String timeStamp = request.getParameter("timeStamp");
				
				try{
					
				      reporterIdNum = Long.parseLong(reporterId);
					  reportedIdNum = Long.parseLong(reportedId);					
				}
				catch (Exception e) {
				System.out.println("null session parameter");
				}
				
				jdbc.deleteFromReports(reporterIdNum, reportedIdNum);
				
				if(isBan.equals("true"))
				{
					//reportedIdNum = Long.parseLong(reportedId);	
					jdbc.insertToBlocks(0, reportedIdNum, 2, "");
					Message banMessage = new Message();
					banMessage.setSecondPartyId(reportedIdNum);
					banMessage.setContent("You've been banned due to other user's complaints");
					banMessage.setUserId(0);
					jdbc.insertToMessages(banMessage);	
					
				}
				
			}
			else if(isChangeFlag.equals("true"))
			{
				reporterId = request.getParameter("reporterId");
				reportedId = request.getParameter("reportedId");
				timeStamp = request.getParameter("timeStamp");
				
				try{
				      reporterIdNum = Long.parseLong(reporterId);
					  reportedIdNum = Long.parseLong(reportedId);					
				}
				catch (Exception e) {
				System.out.println("null session parameter");
				}
				
				jdbc.changeSeenFlag(reporterIdNum, reportedIdNum, timeStamp);
				
			}
		
		}
		
		reportsList = jdbc.RetrieveListOfReports();	
		
		try {
			System.out.println("report servlet request");
			String jsonResponse = gson.toJson(reportsList);
			System.out.println(jsonResponse);
			out.print(jsonResponse);
			out.flush();
		} finally {
			out.close();
		}
	}

}

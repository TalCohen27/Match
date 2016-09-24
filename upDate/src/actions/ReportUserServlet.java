package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;






import utilsAndData.JDBC;
import utilsAndData.SessionUtils;
import utilsAndData.Report;
/**
 * Servlet implementation class ReportUserServlet
 */
@WebServlet("/ReportUserServlet")
public class ReportUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JDBC jdbc = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportUserServlet() {
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
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String userIdFromSession = SessionUtils.getUserId(request);
		long userId = 0;
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String responce = "";
		String content = request.getParameter("content");
		String reported = request.getParameter("reported");
		long reportedId = 0;
		
		if (userIdFromSession == null || reported == null) {
			
			responce = "go to sign in";
		}
		else
		{
			try {
				userId = Long.parseLong(userIdFromSession);
				reportedId = Long.parseLong(reported);
				jdbc.insertToReports(new Report(userId, reportedId, content));
			} catch (Exception e) {
				System.out.println("null session parameter");
			}
				
			responce = "true";			
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



package managers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import utilsAndData.JDBC;

@WebServlet("/Manager")
public class Manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBC matchDataJDBCTemplate = null;
	private Jedis jedis = null; // //

	public Manager() {
		super();
	}

	public void init() throws ServletException {
		matchDataJDBCTemplate = new JDBC();
		jedis = new Jedis("localhost");
		System.out.println("matchDataJDBCTemplate=" + matchDataJDBCTemplate);
		matchDataJDBCTemplate.createTables();
		System.out.println("table created or already exist");
		getServletContext().setAttribute("jedis", jedis);
		getServletContext().setAttribute("matchDataJDBCTemplate",
				matchDataJDBCTemplate);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("IN doGet");
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("IN processRequest");
		response.setContentType("text/html;charset=UTF-8");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
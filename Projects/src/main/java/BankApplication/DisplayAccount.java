package BankApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/displaylink")
public class DisplayAccount extends HttpServlet {
	Connection con=null;
    @Override
    public void init() throws ServletException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/product_info","root","sql123");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Statement stmt=null;
    	ResultSet rs=null;
    	PrintWriter pw=resp.getWriter();
    	
    	String query="select account_no,name from bank_info";
    	try {
			stmt=con.createStatement();
			rs=stmt.executeQuery(query);
			pw.print("<table border='2'>");
			pw.print("<tr>");
			pw.print("<th>Account Number</th>");
			pw.print("<th>Account Holder</th>");
			pw.print("</tr>");
			while(rs.next()) {
				long accNo=rs.getLong(1);
				String name=rs.getString(2);
				
				pw.print("<tr>");
				pw.print("<td>"+accNo+"</td>");
				pw.print("<td>"+name+"</td>");
				pw.print("</tr>");
			
			}
			pw.print("</table>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}

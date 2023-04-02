package BankApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/multilink")
public class Transaction extends HttpServlet{
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
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
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String accNo=req.getParameter("account");
	long accno=Long.parseLong(accNo);
	String amt=req.getParameter("balance");
	double amount=Double.parseDouble(amt);
	String depo=req.getParameter("deposite");
	String withd=req.getParameter("withdraw");
	
	String query1="select ibal from bank_info where account_no=?";
	String query2="update bank_info set ibal=? where account_no=?";
	PrintWriter pw=resp.getWriter();
	if(depo!=null) {
		try {
			pstmt=con.prepareStatement(query1);
			pstmt.setLong(1, accno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				double balance=rs.getDouble("ibal");
				if(balance>=amount) {
					pstmt=con.prepareStatement(query2);
					pstmt.setDouble(1, balance+amount);
					pstmt.setLong(2, accno);
					pstmt.executeUpdate();
					
					pw.print("<h1 style='color:green'>Amount Deposited Successfully</h1>");
					RequestDispatcher rd=req.getRequestDispatcher("HomePage.html");
					rd.include(req, resp);
				}else {
					pw.print("<h1 style='color:red'>Wrong Input</h1>");
					RequestDispatcher rd=req.getRequestDispatcher("HomePage.html");
					rd.include(req, resp);
				}
			}else {
				pw.print("<h1 style='color:red'>Account Not Found</h1>");
				RequestDispatcher rd=req.getRequestDispatcher("HomePage.html");
				rd.include(req, resp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	if(withd!=null) {
		try {
			pstmt=con.prepareStatement(query1);
			pstmt.setLong(1, accno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				double balance=rs.getDouble("ibal");
				if(balance>=amount) {
					pstmt=con.prepareStatement(query2);
					pstmt.setDouble(1, balance-amount);
					pstmt.setLong(2, accno);
					pstmt.executeUpdate();
					
					pw.print("<h1 style='color:green'>Amount Withdraw Successfully</h1>");
					RequestDispatcher rd=req.getRequestDispatcher("HomePage.html");
					rd.include(req, resp);
				}
				else {
					pw.print("<h1 style='color:red'>Insufficient Balance</h1>");
					RequestDispatcher rd=req.getRequestDispatcher("HomePage.html");
					rd.include(req, resp);
				}
			}
			else {
				pw.print("<h1 style='color:red'>Account Not Found</h1>");
				RequestDispatcher rd=req.getRequestDispatcher("HomePage.html");
				rd.include(req, resp);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
}

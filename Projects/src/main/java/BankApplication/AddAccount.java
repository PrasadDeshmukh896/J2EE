package BankApplication;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/addlink")
public class AddAccount extends HttpServlet {
    Connection con = null;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_info", "root", "sql123");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accnumber = req.getParameter("accnumber");
        String username = req.getParameter("username");
        String ibal = req.getParameter("ibalance");

        long accNo = 0;
        double initialBal = 0;

        // check for null and parse the values
        if (accnumber != null) {
            accNo = Long.parseLong(accnumber);
        }

        if (ibal != null) {
            initialBal = Double.parseDouble(ibal);
        }

        PreparedStatement pstmt = null;
        String query = "insert into bank_info(account_no,name,ibal) values(?,?,?)";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, accNo);
            pstmt.setString(2, username);
            pstmt.setDouble(3, initialBal);
            int count = pstmt.executeUpdate();
            PrintWriter pw = resp.getWriter();
            pw.print("<h1>" + count + " Account Generated Successfully</h1>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

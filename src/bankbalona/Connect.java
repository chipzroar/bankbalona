package bankbalona;

import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Connect {
    Connection conn = null;
    
    public Connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bankbalona", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registerUser(User user) {
        Statement stmt;
        String sql = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            sql = "select * from user where username = '"+user.getUsername()+"'";
            rs =  stmt.executeQuery(sql);
            if(rs.next() == false) {
                sql = "insert into user values ('"+user.getUsername()+"', '"+user.getPassword()+"', '"+user.getFirstname()+"', '"+user.getLastname()+"', 0)";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "New Record Saved.");
            } else {
                JOptionPane.showMessageDialog(null, "Username already exists.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                 Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addAccount(Account acc) {
        Statement stmt;
        String sql;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            sql = "select * from account where accountNumber = '"+acc.getAccountNumber()+"'";
            rs = stmt.executeQuery(sql);
            if(!rs.next()) {
                sql = "insert into account values('"+acc.getAccountNumber()+"',"+acc.getBalance()+", '"+acc.getUsername()+"')";
                stmt.executeUpdate(sql);
            } else {
                throw new IllegalArgumentException("Account already exists!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int login(String username, String password){
        Statement stmt;
        String sql;
        ResultSet rs;
        try {
            stmt=conn.createStatement();
            sql ="select * from user where username='"+username+"' and password='"+password+"'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                sql = "select userType from user where username = '"+username+"'";
                rs = stmt.executeQuery(sql);
                if(rs.next()) {
                    return Integer.parseInt(rs.getString("userType"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public ArrayList<Account> displayAccount(String username){
        ArrayList<Account> account = new ArrayList<Account>();
        String sql ="select * from account where username='"+username+"'";
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
              Account acc = new Account(rs.getString(1),rs.getDouble(2));
              account.add(acc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }
    
    public boolean updateBalance(Account account) {
        Statement stmt;
        String sql = null;
        try {
            stmt = conn.createStatement();
            sql = "update account set balance='" + account.getBalance() +"' where accountNumber = '" + account.getAccountNumber() +"'";
            stmt.executeUpdate(sql);
            return true;
        } catch(SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void deleteAccount(Account account){
        Statement stmt;
        String sql = null;
        try {
            stmt = conn.createStatement();
            sql = "delete from account where accountNumber = '"+ account.getAccountNumber() +"'";
            stmt.executeUpdate(sql);
        } catch(SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verify(Verification ver) {
        Statement stmt;
        String sql;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            sql ="select * from verification where customerUsername='"+ver.getCustomerUsername()+"' and accountNumber='"+ver.getAccountNumber()+"' and amount='"+ver.getAmount()+"' and typeoftransaction='"+ver.getTypeoftransaction()+"' ";
            rs = stmt.executeQuery(sql);
            if(!rs.next()) {
                sql = "insert into verification values ('"+ver.getCustomerUsername()+"','"+ver.getAccountNumber()+"','"+ver.getAmount()+"', '"+ver.getTypeoftransaction()+"', 0)";
                stmt.executeUpdate(sql);
            } else {
                JOptionPane.showMessageDialog(null, "Process is already being initiated");
            }
        } catch(SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    
    public ArrayList<Verification> displayVerification(){
        ArrayList<Verification> verify = new ArrayList<Verification>();
        String sql ="select * from verification where status="+0+"";
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
              Verification ver = new Verification(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
              verify.add(ver);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verify;
    }
    
    public void updateStatus(Verification ver) {
        Statement stmt;
        String sql = null;
        try {
            stmt = conn.createStatement();
            sql = "update verification set status="+1+" where accountNumber ='"+ver.getAccountNumber()+"' and customerUsername='"+ver.getCustomerUsername()+"' ";
            stmt.executeUpdate(sql);
        } catch(SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int checkAccount(String accountNumber) {
        Statement stmt;
        String sql;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            sql = "select * from account where accountNumber = '"+accountNumber+"' ";
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public double getAccountBalance(String accountNumber) {
        Statement stmt;
        String sql;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            sql = "select * from account where accountNumber = '"+accountNumber+"' ";
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public String getAccountUsername(String accountNumber) {
        Statement stmt;
        String sql;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            sql = "select * from account where accountNumber = '"+accountNumber+"' ";
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
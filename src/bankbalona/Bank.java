package bankbalona;

import javax.swing.JFrame;


public class Bank extends JFrame {

    public static void main(String[] args) {
        // TODO code application logic here
        Register register = new Register();
        JLogin jlogin = new JLogin();
        jlogin.show();  
        register.setLocationRelativeTo(null);
        jlogin.setLocationRelativeTo(null);

    }
    
}

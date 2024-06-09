package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame{
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JTextField fld_pass;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_pass;

    public LoginView(){ //loginView constructor.
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(400,400); // setting size and title of the container.

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2 ;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2 ;
        this.setLocation(x,y); // container centered.
        this.setVisible(true);
    }
}

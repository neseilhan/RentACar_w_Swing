package view;

import core.Helper;

import javax.swing.*;

public class Layout extends JFrame {

    public void guiInitialize(int width, int height){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(width,height); // setting size and title of the container.
        this.setLocation(Helper.getLocationPoint("x", this.getSize()),Helper.getLocationPoint("y", this.getSize())); // container centered.
        this.setVisible(true);
    }
}

package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme() {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){ //added "Nimbus" theme.
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            }
        }

    }
    public static void showMsg (String str){
        String msg;
        String title;
        switch (str) {
            case "fill" -> {
                msg = "Lutfen tum alanlari doldurunuz.";
                title = "HATA";
            }
            case "done" -> {
                msg = "Islem basarili.";
                title = "Sonuc";
            }
            case "notFound" -> {
                msg = str + "bulunamadi.";
                title = "Bulunamadi";
            }

            default -> {
                msg = str;
                title = "Mesaj";
            }

        }
        JOptionPane.showMessageDialog(null,str,"HATA", JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty(); // if its empty return true.
    }
    public static boolean isFieldListEmpty(JTextField[] fieldlist){
        for(JTextField field : fieldlist){
            if(isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }
    public static int getLocationPoint(String type, Dimension size){
        return switch (type){
            case "x"  -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2 ;
            case "y"  -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2 ;
            default -> 0;
        };
    }
}

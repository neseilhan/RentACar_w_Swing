package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme() {
        optionPaneTR();
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
        optionPaneTR();
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
                msg = "Kayit bulunamadi.";
                title = "Bulunamadi";
            }
            case "error" -> {
                msg = "Hatali islem";
                title = "HATA";
            }
            default -> {
                msg = str;
                title = "Mesaj";
            }

        }
        JOptionPane.showMessageDialog(null,msg,title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean confirm(String str){ //Confirm operation for deleting.
        optionPaneTR();
        String msg;
        if(str.equals("sure")){
            msg = "Bu islemi yapmak istedigine emin misin?";
        }
        else{
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Silme Onay", JOptionPane.YES_NO_OPTION) == 0;
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
    public static void optionPaneTR(){ //translation of OptionPane
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayir");

    }
}

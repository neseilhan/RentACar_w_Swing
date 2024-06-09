package core;

import javax.swing.*;

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
}

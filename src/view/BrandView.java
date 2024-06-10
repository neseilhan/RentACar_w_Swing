package view;

import business.BrandManager;
import core.Helper;
import entity.Brand;

import javax.swing.*;

public class BrandView extends Layout{
    private BrandManager brandManager;
    private JPanel container;
    private Brand brand;
    private JLabel lbl_brand;
    private JLabel lbl_brand_name;
    private JTextField fld_brand_name;
    private JButton btn_brand_save;

    public BrandView(Brand brand){
        this.brandManager = new BrandManager();
        this.brand = brand;
        this.add(container);
        this.guiInitialize(300,300);

        if(brand != null){
            fld_brand_name.setText(brand.getName()); // the update screen appears wth brand name.
        }

        btn_brand_save.addActionListener(e ->{
            if(Helper.isFieldEmpty(this.fld_brand_name)){
                Helper.showMsg("fill");
            }
            else{
                boolean result = true;
                if(this.brand == null){
                    Brand obj = new Brand(fld_brand_name.getText());
                    result = this.brandManager.save(obj);
                }
                else{
                    this.brand.setName(fld_brand_name.getText());
                    result = this.brandManager.update(this.brand);

                }
                if(result){
                    Helper.showMsg("done");
                    dispose();

                }
                else{
                    Helper.showMsg("error");
                }
            }

        });

    }
}

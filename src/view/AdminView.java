package view;

import business.BrandManager;
import business.ModelManager;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private User user;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;

    public AdminView(User user) {

        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(800, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hosgeldiniz " + this.user.getUsername());
        loadBrandTable();
        loadBrandComponent();
        loadModelTable();
        loadModelComponent();

    }
    public void loadModelComponent(){
        tableRowSelect(this.tbl_model);
        this.modelMenu = new JPopupMenu();
        this.modelMenu.add("Yeni").addActionListener(e ->{
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable();
                }
            });
        });

        this.modelMenu.add("Guncelle").addActionListener(e ->{

            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));

            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable();
                }
            });
        });

        this.modelMenu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedModelId = this.getTableSelectedRow(tbl_model, 0);
                if (this.modelManager.delete(selectedModelId)) {
                    Helper.showMsg("done");
                    loadModelTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(modelMenu);

    }
    public void loadModelTable(){
        Object[] col_model = {"Model ID","Marka","Model Adi","Tip", "Yil", "Yakit Tipi", "Vites"};
        ArrayList<Object[]> modelList = this.modelManager.getForTable(col_model.length, this.modelManager.findAll());
        createTable(this.tmdl_model, this.tbl_model, col_model,modelList);
    }
    public void loadBrandComponent(){
            tableRowSelect(this.tbl_brand);
            this.brandMenu = new JPopupMenu();
            this.brandMenu.add("Yeni").addActionListener(e ->{
                BrandView brandView = new BrandView(null); //It must be null since we are going to add it.
                brandView.addWindowListener( new WindowAdapter(){
                    @Override
                    public void windowClosed(WindowEvent e){
                        loadBrandTable();
                    }
                });
            });
            this.brandMenu.add("Guncelle").addActionListener(e ->{
                int selectBrandId = this.getTableSelectedRow(tbl_brand,0);
                BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
                brandView.addWindowListener( new WindowAdapter(){
                    @Override
                   public void windowClosed(WindowEvent e){
                    loadBrandTable();
                    }
                });
            });
        this.brandMenu.add("Sil").addActionListener(e -> {
             if(Helper.confirm("sure")){
                 int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
//                 System.out.println(selectBrandId);
                 if(this.brandManager.delete(selectBrandId)){
                     Helper.showMsg("done");
                     loadBrandTable();
                 }
                 else{
                     Helper.showMsg("error");
                 }

             }

        });
        this.tbl_brand.setComponentPopupMenu(brandMenu);


    }
    public void loadBrandTable(){
        String[] col_brand = {"Marka ID", "Marka Adi"}; //columns created.
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
//        tmdl_brand.setColumnIdentifiers(col_brand); //columns identified.
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand,brandList);
    }
}

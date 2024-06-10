package view;

import business.BrandManager;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private BrandManager brandManager;
    private JPopupMenu brandMenu;

    public AdminView(User user){

        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(1000,500);
        this.user = user ;
        if(this.user == null){
            dispose();
        }
        this.lbl_welcome.setText("Hosgeldiniz "+ this.user.getUsername());

        String[] col_brand = {"Marka ID", "Marka Adi"}; //columns created.
        ArrayList<Brand> brandList = brandManager.findAll();
        tmdl_brand.setColumnIdentifiers(col_brand); //columns identified.
        for(Brand brand : brandList){
            Object[] obj = {brand.getId(),brand.getName()};
            this.tmdl_brand.addRow(obj); //kolonlarla aynı boyutta olan bir objeye ihtiyac var.
        }

        this.tbl_brand.setModel(tmdl_brand); //table set.
        this.tbl_brand.getTableHeader().setReorderingAllowed(false);// kolonların yeri elle degişmesin
//        this.tbl_brand.setEnabled(false); //düzenlenmesini önlemek icin

        this.tbl_brand.addMouseListener(new MouseAdapter() { //tabloda tıklanan satırın seçili hale gelmesi icin.
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = tbl_brand.rowAtPoint(e.getPoint());
                tbl_brand.setRowSelectionInterval(selected_row, selected_row);
            }
        });

        this.brandMenu = new JPopupMenu();
        this.brandMenu.add("Yeni").addActionListener(e ->{
            System.out.println("Yeni butonu tiklandi.");
        });
        brandMenu.add("Guncelle");
        brandMenu.add("Sil");
        this.tbl_brand.setComponentPopupMenu(brandMenu);

    }
}

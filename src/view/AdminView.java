package view;

import business.BrandManager;
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
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private BrandManager brandManager;
    private JPopupMenu brandMenu;

    public AdminView(User user){

        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(800,500);
        this.user = user ;
        if(this.user == null){
            dispose();
        }
        this.lbl_welcome.setText("Hosgeldiniz "+ this.user.getUsername());

        loadBrandTable();

        this.brandMenu = new JPopupMenu();
        this.brandMenu.add("Yeni").addActionListener(e ->{
            BrandView brandView = new BrandView(null); //It must be null since we are going to add it.
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) { // for the update screen.
                    loadBrandTable();
                }
            });
        });

        brandMenu.add("Guncelle").addActionListener(e ->{
            int selectBrandId = Integer.parseInt(tbl_brand.getValueAt(tbl_brand.getSelectedRow(),0).toString());
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) { // for the update screen.
                    loadBrandTable();
                }
            });
        });
        brandMenu.add("Sil");
        this.tbl_brand.setComponentPopupMenu(brandMenu);

    }
    public void loadBrandTable(){
        String[] col_brand = {"Marka ID", "Marka Adi"}; //columns created.
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        tmdl_brand.setColumnIdentifiers(col_brand); //columns identified.
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand,brandList);

//        DefaultTableModel clearModel = (DefaultTableModel) tbl_brand.getModel();
//        clearModel.setRowCount(0);
//        for(Brand brand : brandList){
//            Object[] obj = {brand.getId(),brand.getName()};
//            this.tmdl_brand.addRow(obj); //an object with the same dimensions as the columns is needed.
//        }
//
//        this.tbl_brand.setModel(tmdl_brand); //table set.
//        this.tbl_brand.getTableHeader().setReorderingAllowed(false);// preventing columns from being changed manually
////        this.tbl_brand.setEnabled(false); //to prevent editing
//
//        this.tbl_brand.addMouseListener(new MouseAdapter() { //to make the clicked row in the table selected.
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int selected_row = tbl_brand.rowAtPoint(e.getPoint());
//                tbl_brand.setRowSelectionInterval(selected_row, selected_row);
//            }
//        });
    }
}

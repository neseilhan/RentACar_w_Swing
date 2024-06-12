package view;

import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JButton btn_search_model;
    private JComboBox cmb_s_model_brand;
    private JComboBox cmb_s_model_type;
    private JComboBox cmb_s_model_fuel;
    private JComboBox cmb_s_model_gear;
    private JButton btn_clear_model;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;
    private Object[] col_model;

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
        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();


    }
    public void loadModelComponent(){
        tableRowSelect(this.tbl_model);
        this.modelMenu = new JPopupMenu();
        this.modelMenu.add("Yeni").addActionListener(e ->{
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });

        this.modelMenu.add("Guncelle").addActionListener(e ->{

            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));

            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });

        this.modelMenu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedModelId = this.getTableSelectedRow(tbl_model, 0);
                if (this.modelManager.delete(selectedModelId)) {
                    Helper.showMsg("done");
                    loadModelTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(modelMenu);


        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();

            int brandId=0;
            if (selectedBrand != null){
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    brandId,
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
                    (Model.Type) cmb_s_model_type.getSelectedItem()
            );
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length,modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });
        this.btn_clear_model.addActionListener(e -> {
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });


    }
    public void loadModelTable(ArrayList<Object[]> modelList){
        this.col_model = new Object[]{"Model ID", "Marka", "Model Adi", "Tip", "Yil", "Yakit Turu", "Vites"};
        if(modelList == null){
            modelList = this.modelManager.getForTable(this.col_model.length, this.modelManager.findAll());
        }

        createTable(this.tmdl_model, this.tbl_model, col_model, modelList);
    }
    public void loadModelFilter() {
        this.cmb_s_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_model_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();
    }

    public void loadModelFilterBrand() {
        this.cmb_s_model_brand.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
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
                        loadModelTable(null);
                        loadModelFilterBrand();
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
                    loadModelTable(null);
                    loadModelFilterBrand();
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
                     loadModelTable(null);
                     loadModelFilterBrand();
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

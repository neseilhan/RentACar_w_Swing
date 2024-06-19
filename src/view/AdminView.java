package view;

import business.BookManager;
import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
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
    private JScrollPane scrl_car;
    private JTable tbl_car;
    private JPanel pnl_car;
    private JPanel pnl_booking;
    private JScrollPane scrl_booking;
    private JPanel pnl_booking_search;
    private JTable tbl_booking;
    private JComboBox <Model.Gear>cmb_booking_gear;
    private JComboBox <Model.Fuel>cmb_booking_fuel;
    private JComboBox <Model.Type>cmb_booking_type;
    private JFormattedTextField fld_start_date;
    private JFormattedTextField fld_fnsh_date;
    private JButton btn_booking_search;
//    private JComboBox cmb_booking_gear;
//    private JComboBox cmb_booking_fuel;
//    private JComboBox cmb_booking_type;
    private JButton btn_booking_clear;
    private JTable tbl_book;
    private JButton btn_book_clear;
    private JButton btn_book_search;
    private JComboBox cmb_s_book_car;
    private JScrollPane scrl_book;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private DefaultTableModel tmdl_booking = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private BookManager bookManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;
    private JPopupMenu carMenu;
    private JPopupMenu booking_menu;
    private Object[] col_model;
    private Object[] col_car ;

    public AdminView(User user) {

        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.bookManager = new BookManager();
        this.add(container);
        this.guiInitialize(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hosgeldiniz " + this.user.getUsername());

        //General Code
        loadComponent();
        //Brand Tab Menu
        loadBrandTable();
        loadBrandComponent();

        //Model Tab Menu
        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

        //Car Tab Menu
        loadCarTable();
        loadCarComponent();

        //Booking Tab Menu
        loadBookingFilter();
        loadBookingTable(null);
        loadBookingComponent();

    }

    private void loadComponent() {
        this.btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView  loginView = new LoginView();
            }
        });
    }

    public void loadBookingComponent(){
        tableRowSelect(this.tbl_booking);
        this.booking_menu = new JPopupMenu();
        this.booking_menu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(this.tbl_booking,0); //Selected Car ID.
            BookingView bookingView = new BookingView(
                    this.carManager.getById(selectCarId),
                    this.fld_start_date.getText(),
                    this.fld_fnsh_date.getText()
            );
            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBookingTable(null);
                    loadBookingFilter();
                }
            });

        });
        this.tbl_booking.setComponentPopupMenu(booking_menu);
//        tableRowSelected(this.tbl_booking,booking_menu);

        btn_booking_search.addActionListener(e -> {
            ArrayList<Car> carList = this.carManager.searchForBooking(
                    fld_start_date.getText(), //This place should be sorted by searchForBooking method.
                    fld_fnsh_date.getText(),
                    (Model.Type)cmb_booking_type.getSelectedItem(),
                    (Model.Fuel)cmb_booking_fuel.getSelectedItem(),
                    (Model.Gear)cmb_booking_gear.getSelectedItem()

            );
            ArrayList<Object[]> carBookingRow = this.carManager.getForTable(this.col_car.length, carList);
            loadBookingTable(carBookingRow);
        });
        btn_booking_clear.addActionListener(e -> {
            loadBookingFilter();
        });
    }

    public void loadBookingTable(ArrayList<Object[]> carList) {
        Object[] col_booking_list = {"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl", "Tip", "Yakıt Türü", "Vites"};
        createTable(this.tmdl_booking, this.tbl_booking, col_booking_list, carList);
    }

    public void loadBookingFilter() {
        this.cmb_booking_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_booking_type.setSelectedItem(null);
        this.cmb_booking_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_booking_fuel.setSelectedItem(null);
        this.cmb_booking_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_booking_gear.setSelectedItem(null);
    }

    private void loadCarComponent(){
        this.carMenu = new JPopupMenu();
        this.carMenu.add("Yeni").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.carMenu.add("Güncelle").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(tbl_car, 0);
            CarView carView = new CarView(this.carManager.getById(selectCarId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.carMenu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedCarId = this.getTableSelectedRow(tbl_car, 0);
                if (this.carManager.delete(selectedCarId)) {
                    Helper.showMsg("done");
                    loadCarTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_car.setComponentPopupMenu(carMenu);
    }

    public void loadCarTable() {
        col_car = new Object[]{"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl","Tip", "Yakıt Türü", "Vites"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length, this.carManager.findAll());
        createTable(this.tmdl_car, this.tbl_car, col_car, carList);
    }

    public void loadModelComponent(){
//        tableRowSelect(this.tbl_model);
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
                    loadCarTable();
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
            //Converting ArrayList with a model into objects with getForTable.
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

    private void createUIComponents() throws ParseException{
        this.fld_start_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_start_date.setText("10/10/2023"); //Default date
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_fnsh_date.setText("16/10/2023");
    }
}

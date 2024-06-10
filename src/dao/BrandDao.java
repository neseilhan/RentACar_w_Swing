package dao;

import core.Db;
import entity.Brand;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection con;
    public BrandDao(){
        this.con = Db.getInstance();
    }

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> BrandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while(rs.next()){
                BrandList.add(this.match(rs));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return BrandList;
    }
    public Brand match(ResultSet rs) throws SQLException{
        Brand obj = new Brand();

        obj.setId(rs.getInt("brand_id"));
        obj.setName(rs.getString("brand_name"));

        return obj;

    }
}

package DataAccesor;

import Models.Produk;
import config.DBConnector;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbControl extends DBConnector {
    
    public static List<Produk> readData() {
        getConnection();
        List<Produk> dataList = new ArrayList<>();
        
        try {
            statement = connect.prepareStatement("SELECT * FROM `tb_produk` ");
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAMA");
                int price = resultSet.getInt("HARGA");
                int stock = resultSet.getInt("JUMLAH");

                Produk product = new Produk(id, name, price, stock);
                dataList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
    
    public static void deleteData(int id) {
        getConnection();

        try {
            String query = "DELETE FROM `tb_produk` WHERE `tb_produk`.`ID` = ?";
            statement = connect.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            updateId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    public static void insertData(String nama, long harga, int stock) {
        getConnection();
        try {
            resetId();
            statement = connect.prepareStatement("INSERT INTO tb_produk (NAMA, HARGA, JUMLAH) VALUES (?, ?, ?)");
            statement.setString(1, nama);
            statement.setLong(2, harga);
            statement.setInt(3, stock);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateJumlah(int id, int stock) {
        getConnection();
        try {
            statement = connect.prepareStatement("UPDATE tb_produk SET JUMLAH=? WHERE ID=?");
            statement.setInt(1, stock);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateHarga(int id, long harga) {
        getConnection();
        try {
            statement = connect.prepareStatement("UPDATE tb_produk SET HARGA=? WHERE ID=?");
            statement.setLong(1, harga);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateNama(int id, String nama) {
        getConnection();
        try {
            statement = connect.prepareStatement("UPDATE tb_produk SET NAMA=? WHERE ID=?");
            statement.setString(1, nama);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void resetId(){
        getConnection();
        try{
            statement = connect.prepareStatement("ALTER TABLE `tb_produk` AUTO_INCREMENT = 1");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateId(){
        getConnection();
        try{
            List<Produk> dataList = readData();
            for (int i = 0; i < dataList.size(); i++){
                Produk data = dataList.get(i);
                int newId = i + 1;
                statement = connect.prepareStatement("UPDATE tb_produk SET ID = ? WHERE ID = ?");
                statement.setInt(1, newId);
                statement.setInt(2, data.getId());
                statement.executeUpdate();
                statement.close();
            } 
        } catch (SQLException e){
                e.printStackTrace();
        }
    }
}


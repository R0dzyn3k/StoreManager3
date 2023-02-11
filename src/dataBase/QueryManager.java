package dataBase;

import dataModel.Category;
import dataModel.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryManager {

    public static void setLastProductId(int id) {

        String query = "UPDATE Settings " +
                        "SET value = ? WHERE key = 'last_product_id';";

        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);
            prepStmt.setInt(1, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizowaniu last_product_id");
            e.printStackTrace();
        }
    }

    public static int getLastProductId(){

        String query =  "SELECT value " +
                        "FROM Settings " +
                        "WHERE key like 'last_product_id'";
        int lastProductId = 0;

        try {
            ResultSet result = DataBaseConnection.stat.executeQuery(query);
            if(result.next())
                lastProductId = result.getInt("value");
        } catch (SQLException e) {
            System.err.println("Blad przy pobieraniu last_product_id");
            e.printStackTrace();
            return 0;
        }
        return lastProductId;
    }

    public static void setLastCategoryId(int id) {

        String query = "UPDATE Settings " +
                "SET value = ? WHERE key = 'last_category_id';";

        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);
            prepStmt.setInt(1, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizowaniu last_category_id");
            e.printStackTrace();
        }
    }

    public static int getLastCategoryId(){

        String query =  "SELECT value " +
                "FROM Settings " +
                "WHERE key like 'last_category_id'";
        int lastCategoryId = 0;

        try {
            ResultSet result = DataBaseConnection.stat.executeQuery(query);
            if(result.next())
                lastCategoryId = result.getInt("value");
        } catch (SQLException e) {
            System.err.println("Blad przy pobieraniu last_category_id");
            e.printStackTrace();
            return 0;
        }
        return lastCategoryId;
    }

    public static void insertProducts(ArrayList<Product> products) {

        String query = "INSERT INTO Products " +
                        "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);

            for (Product product : products) {
                prepStmt.setString(1, product.getName());
                prepStmt.setString(2, product.getDesc());
                prepStmt.setDouble(3, product.getPrice());
                prepStmt.setDouble(4, product.getSalePrice());
                prepStmt.setString(5, product.getSaleFrom());
                prepStmt.setString(6, product.getSaleTo());
                prepStmt.setInt(7, product.getQty());
                prepStmt.setInt(8, product.getCategoryId());
                prepStmt.addBatch();
            }
            prepStmt.executeBatch();

        } catch (SQLException e) {
            System.err.println("Blad przy kilku wstawianiu produktów");
            e.printStackTrace();
        }
    }
    public static void insertProducts(Product product) {

        String query = "INSERT INTO Products " +
                        "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);

            prepStmt.setString(1, product.getName());
            prepStmt.setString(2, product.getDesc());
            prepStmt.setDouble(3, product.getPrice());
            prepStmt.setDouble(4, product.getSalePrice());
            prepStmt.setString(5, product.getSaleFrom());
            prepStmt.setString(6, product.getSaleTo());
            prepStmt.setInt(7, product.getQty());
            prepStmt.setInt(8, product.getCategoryId());
            prepStmt.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu produktu");
            e.printStackTrace();
        }
    }

    public static void insertCategory(Category category) {

        String query = "INSERT INTO Categories " +
                "VALUES (NULL, ?, ?);";
        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);

            prepStmt.setString(1, category.getName());
            prepStmt.setString(2, category.getDesc());
            prepStmt.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu kategorii");
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id) {

        String query = "DELETE FROM Products " +
                        "WHERE id = ?;";

        try{
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu produktu");
            e.printStackTrace();
        }
    }
    public static void updateProduct(Product product) {

        String query = "UPDATE Products " +
                        "SET name = ?, " +
                        "desc = ?, " +
                        "price = ?, " +
                        "sale_price = ?, " +
                        "sale_from = ?, " +
                        "sale_to = ?, " +
                        "qty = ?, " +
                        "category_id = ? "+
                        "WHERE id = ?;";

        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);
            prepStmt.setString(1, product.getName());
            prepStmt.setString(2, product.getDesc());
            prepStmt.setDouble(3, product.getPrice());
            prepStmt.setDouble(4, product.getSalePrice());
            prepStmt.setString(5, product.getSaleFrom());
            prepStmt.setString(6, product.getSaleTo());
            prepStmt.setInt(7, product.getQty());
            prepStmt.setInt(8, product.getCategoryId());
            prepStmt.setInt(8, product.getCategoryId());
            prepStmt.setInt(9, product.getId());

            prepStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji produktu");
            e.printStackTrace();
        }
    }

    public static List<Product> selectProducts() {

        String query = "SELECT * " +
                        "FROM Products";
        int id, qty, categoryId;
        double price, salePrice;
        String name, desc, saleFrom, saleTo;
        List<Product> products = new ArrayList<Product>();

        try {
            ResultSet result = DataBaseConnection.stat.executeQuery(query);
            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                desc = result.getString("desc");
                price = result.getDouble("price");
                salePrice = result.getDouble("sale_price");
                saleFrom = result.getString("sale_from");
                saleTo = result.getString("sale_to");
                qty = result.getInt("qty");
                categoryId = result.getInt("category_id");

                        products.add(new Product(id, name, desc, price, salePrice, saleFrom, saleTo, qty, categoryId));
            }
        } catch (SQLException e) {
            System.err.println("Blad przy pobieraniu produktów");
            e.printStackTrace();
            return null;
        }
        return products;
    }

    public static void updateCategory(Category category) {

        String query = "UPDATE Categories " +
                "SET name = ?, desc = ? " +
                "WHERE id = ?;";

        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);
            prepStmt.setString(1, category.getName());
            prepStmt.setString(2, category.getDesc());
            prepStmt.setInt(3, category.getId());

            prepStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji kategori");
            e.printStackTrace();
        }
    }
    public static ArrayList<Category> selectCategories() {

        String query = "SELECT * " +
                "FROM Categories";
        int id;
        String name, desc;
        ArrayList<Category> categories = new ArrayList<Category>();

        try {
            ResultSet result = DataBaseConnection.stat.executeQuery(query);
            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                desc = result.getString("desc");
                categories.add(new Category(id, name, desc));
            }
        } catch (SQLException e) {
            System.err.println("Blad przy pobieraniu kategorii");
            e.printStackTrace();
            return null;
        }
        return categories;
    }

    public static void deleteCategory(int id) {

        String query = "DELETE FROM Categories " +
                "WHERE id = ?;";

        try{
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu kategorii");
            e.printStackTrace();
        }
    }

    public static List<Product> selectProductsLite() {
        String query = "SELECT id, name " +
                        "FROM Products";
        int id;
        String name;
        List<Product> products = new ArrayList<Product>();

        try {
            ResultSet result = DataBaseConnection.stat.executeQuery(query);
            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("name");

                products.add(new Product(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Blad przy pobieraniu id i nazwy produktów");
            e.printStackTrace();
            return null;
        }
        return products;
    }


    public static Product selectProductById(int id) {

        String query = "SELECT * " +
                        "FROM Products " +
                        "WHERE id = ?";

        try {
            PreparedStatement prepStmt = DataBaseConnection.conn.prepareStatement(query);
            prepStmt.setInt(1, id);
            ResultSet result = prepStmt.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                String desc = result.getString("desc");
                double price = result.getDouble("price");
                double salePrice = result.getDouble("sale_price");
                String saleFrom = result.getString("sale_from");
                String saleTo = result.getString("sale_to");
                int qty = result.getInt("qty");
                int categoryId = result.getInt("category_id");

                return new Product(id, name, desc, price, salePrice, saleFrom, saleTo, qty, categoryId);
            }

        } catch (SQLException e) {
            System.err.println("Blad przy pobieraniu produktu po id");
            e.printStackTrace();
        }
        return null;

    }
//    public static void checkCategoriesExists() {
//
//        String query1 = "SELECT count(*) AS count " +
//                        "FROM Categories " +
//                        "WHERE name LIKE ?";
//        String query2 = "INSERT INTO Categories (name, parent_ID, level) " +
//                        "VALUES (?, ?, 1);";
//        String[] table = {"Sklep","Produkty spożywcze", "Produkty nonfood", "Inne"};
//
//        try {
//            PreparedStatement prepStmt1 = DataBaseConnection.conn.prepareStatement(query1);
//
//            for (String tab : table) {
//                prepStmt1.setString(1, tab);
//                ResultSet result = prepStmt1.executeQuery();
//
//                if (result.next()) {
//                    int count = result.getInt("count");
//
//                    if (count == 0) {
//                        try {
//                            PreparedStatement prepStmt2 = DataBaseConnection.conn.prepareStatement(query2);
//                            prepStmt2.setString(1, tab);
//                            prepStmt2.setInt(2, tab.equals("Sklep") ? 0 : 1);
//                            prepStmt2.execute();
//                        } catch (SQLException e) {
//                            System.err.println("Blad przy sprawdzaniu pojedyńczych kategorii");
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Blad przy sprawdzaniu kategorii");
//            e.printStackTrace();
//        }
//    }
}

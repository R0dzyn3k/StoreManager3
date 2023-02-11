package dataModel;

import dataBase.QueryManager;
import util.Validate;

import java.util.List;

public class Product {
    private final int id;
    private String name;
    private String desc;
    private double price;
    private double salePrice;
    private String saleFrom;
    private String saleTo;
    private int qty;
    private int category_id;


    //only for db
    public Product(int id, String name) {

        this.id = id++;
        QueryManager.setLastProductId(id);
        this.name = name;
        //QueryManager.setLastProductId(id);
    }


    public Product(String name, String desc, double price, int qty, int category_id) {

        Validate.validateName(name);
        Validate.validateDesc(desc);
        Validate.validatePrice(price);
        Validate.validateQty(qty);


        int nextId = QueryManager.getLastProductId();
        this.id = nextId++;
        QueryManager.setLastProductId(nextId);
        this.name = name;
        this.desc = desc;
        this.price = Math.round(price * 100d) / 100d;
        this.salePrice = 0;
        this.saleFrom = "";
        this.saleTo = "";
        this.qty = qty;
        this.category_id = category_id;
    }

    public Product(String name, String desc, double price,double salePrice, String saleFrom, String saleTo, int qty, int category_id) {

        Validate.validateName(name);
        Validate.validateDesc(desc);
        Validate.validatePrice(price);
        Validate.validateSalePrice(price,salePrice);
        Validate.validateQty(qty);
        Validate.validateDate(saleFrom);
        Validate.validateDate(saleTo);
        //Validate.validateCategory(qty);


        int nextId = QueryManager.getLastProductId();
        this.id = nextId++;
        QueryManager.setLastProductId(nextId);
        this.name = name;
        this.desc = desc;
        this.price = Math.round(price * 100d) / 100d;
        this.salePrice = Math.round(salePrice * 100d) / 100d;;
        this.saleFrom = saleFrom;
        this.saleTo = saleTo;
        this.qty = qty;
        this.category_id = category_id;
    }

    //only for DB!!!
    public Product(int id, String name, String desc, double price,double salePrice, String saleFrom, String saleTo, int qty, int category_id) {

        this.id = id++;
        QueryManager.setLastProductId(id);
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.salePrice = salePrice;
        this.saleFrom = saleFrom;
        this.saleTo = saleTo;
        this.qty = qty;
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Validate.validateName(name);
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        Validate.validateDesc(desc);
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        Validate.validatePrice(price);
        this.price = Math.round(price * 100d) / 100d;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        Validate.validateQty(qty);
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public static Product findProduct(int id) {
        List<Product> products = QueryManager.selectProducts();
        assert products != null;
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        double price = getPrice();
        Validate.validateSalePrice(price,salePrice);
        this.salePrice = salePrice;
    }

    public  String getSaleFrom() {
        return saleFrom;
    }

    public void setSaleFrom(String saleFrom) {
        Validate.validateDate(saleFrom);
        this.saleFrom = saleFrom;
    }

    public String getSaleTo() {
        return saleTo;
    }

    public void setSaleTo(String saleTo) {
        Validate.validateDate(saleTo);
        this.saleTo = saleTo;
    }

    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {

        Validate.validateCategory(category_id);
        this.category_id = category_id;
    }
//    public static int nextProductId(){
//
//        return id;
//    }
}
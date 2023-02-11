package dataBase;

import dataModel.Product;

import java.util.ArrayList;


public class SampleData {

    public static void AddSampleProducts(){
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(new Product("Maslo","Masło firmy biedronka",5.20, 3, 0));
        products.add(new Product("Mleko","Masło firmy łaciate",4.10, 2, 0));
        products.add(new Product("Cebula","Cebula od polskiego rolnika",6.00, 10, 0));
        products.add(new Product("Chleb","Chleb z lokalnej piekarni",6.40, 6, "2022-10-12", "2025-10-12", 2, 0));
        products.add(new Product("Jajka","Jajka trzeciej kategorii",1.00, 12, 0));

        QueryManager.insertProducts(products);
    }



}

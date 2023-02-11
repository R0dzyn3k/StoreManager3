package util;

import dataBase.QueryManager;
import dataModel.Category;

import java.util.ArrayList;

public class Validate {

    public static void validateName(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("Product name is too short (min 3 characters)");
        }
        if (name.length() > 120) {
            throw new IllegalArgumentException("Product name is too long (max 120 characters)");
        }
        if (!name.matches("^[a-zA-Z0-9\\s]+$")) {
            throw new IllegalArgumentException("Name of the product contains invalid characters.");
        }
    }

    public static void validateDesc(String desc) {
        if (desc.length() > 800) {
            throw new IllegalArgumentException("The product description is too long (max 800 characters).");
        }
    }

    public static void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public static void validateQty(double qty) {
        if (qty < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
    }

    public static void validateSalePrice(double price, double salePrice) {
        if (salePrice < 0 || salePrice > price) {
            throw new IllegalArgumentException("Sale Price must be greater than or equal to 0 and less than or equal to the price");
        }
    }

    public static void validateDate(String dateString) {
        String pattern = "^20[2-9][0-9]-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))$";
        if (!dateString.matches(pattern) && !dateString.equals("")) {
            throw new IllegalArgumentException("Date must be in the format yyyy-MM-dd and between 2020-01-01 and 2030-12-31");
        }
    }

    public static void validateCategory(int category_id) {

        ArrayList<Category> categories = QueryManager.selectCategories();
        boolean categoryExists = categories.stream().anyMatch(x -> x.getId() == category_id);
        if (!categoryExists) {
            throw new IllegalArgumentException("Category does not exist");
        }
    }

}
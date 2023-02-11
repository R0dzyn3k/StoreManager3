package dataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {

    private final Statement stat;

    public DatabaseCreator(Statement stat) {
        this.stat = stat;
    }

    public void createTables() {

        try {
            stat.execute(SqlCollection.CREATE_SETTINGS);
            stat.execute(SqlCollection.CREATE_CATEGORIES);
            //QueryManager.checkCategoriesExists();
            stat.execute(SqlCollection.CREATE_PRODUCTS);
            if(QueryManager.getLastCategoryId() == 0)
                stat.execute(SqlCollection.SET_FIRST_CATEGORY_ID);
            if(QueryManager.getLastProductId() == 0)
                stat.execute(SqlCollection.SET_FIRST_PRODUCT_ID);

        } catch (SQLException e) {
            System.err.println("Bląd przy tworzeniu tabel");
            e.printStackTrace();
        }
    }
}
package dataModel;

import dataBase.QueryManager;
import util.Validate;

public class Category {
    private final int id;
    private String name;
    private String desc;

    public Category(String name, String desc) {

        Validate.validateName(name);
        Validate.validateDesc(desc);

        int nextId = QueryManager.getLastCategoryId();
        this.id = nextId++;
        QueryManager.setLastCategoryId(nextId);

        this.name = name;
        this.desc = desc;
    }

    //for db
    public Category(int id, String name, String desc) {

        Validate.validateName(name);
        Validate.validateDesc(desc);

        this.id = id++;
        QueryManager.setLastProductId(id);
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
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
}
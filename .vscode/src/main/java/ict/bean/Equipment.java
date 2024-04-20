package ict.bean;

public class Equipment {
    private int id;
    private String name;
    private String image;

    public Equipment() {
    }

    public Equipment(String name) {
        this.name = name;
    }
    
    public Equipment(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

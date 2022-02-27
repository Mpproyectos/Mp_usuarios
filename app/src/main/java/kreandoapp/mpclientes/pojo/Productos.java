package kreandoapp.mpclientes.pojo;

public class Productos {
    private String Nameprod,Image,Description,Price,Discount,MenuId;

    public Productos() {
    }

    public Productos(String nameprod, String image, String description, String price, String discount, String menuId) {
        Nameprod = nameprod;
        Image = image;
        Description = description;
        Price = price;
        Discount = discount;
        MenuId = menuId;
    }

    public String getNameprod() {
        return Nameprod;
    }

    public void setNameprod(String nameprod) {
        Nameprod = nameprod;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}

public class View {
    private String productName;
    private String productPrice;

    public View(String name, String price){
        this.productPrice = name;
        this.productPrice = price;
    }

    public String getName(){
        return productName;
    }

    public String getPrice(){
        return productPrice;
    }
}
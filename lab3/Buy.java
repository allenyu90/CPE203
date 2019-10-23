public class Buy {
    private String productName;
    private String price;
    private String quantity;


    public Buy(String name, String price, String quantity){
        this.productName = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName(){
        return productName;
    }
    public String getPrice(){
        return price;
    }
    public String getQuantity(){
        return quantity;
    }

}
package ra.model;

import ra.business.entity.Catalog;
import ra.service.CatalogService;

import java.util.Scanner;

import static ra.run.BookManagement.catalogList;
import static ra.run.BookManagement.productList;
import static ra.service.ProductService.inputNum;

public class Product {
    private String productId;
    private String productName;
    private double productPrice;
    private String description;
    private int stock;
    private Catalog catalog;
    private boolean status;

    public Product() {
    }

    public Product(String productId, String productName, double productPrice,
                   String description, int stock, Catalog catalog, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.stock = stock;
        this.catalog = catalog;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    //    -------------------------------
    public void inputProduct(Scanner sc, boolean flag){
        if(flag){
            this.productId = inputProductId(sc);
        }
        this.productName = inputProductName(sc);
        this.productPrice = inputProductPrice(sc);
        this.description = inputDescription(sc);
        this.stock = inputStock(sc);
        this.catalog = inputCate(sc);
        this.status = true;

    }

    //● productId - String (bắt đầu bằng chữ P và thêm 4 ký tự số , không được trùng lặp );
    private String inputProductId(Scanner sc) {
        System.out.println("Enter product ID (Pxxx): ");
        do {
            String productId = sc.nextLine();
            if (!productId.matches("^P[0-9]{3}$")) {
                System.err.println("Product ID is incorrect format (Pxxx)");
            }else {
                if(productList.stream().anyMatch(p-> p.getProductId().equals(productId))){
                    System.err.println("Product ID is already exist");
                }else{
                    return productId;
                }
            }
        }while (true);
    }
    //● productName - String (Không được để trống )
    private String inputProductName(Scanner sc) {
        System.out.println("Enter product name: ");
        do {
            String productName = sc.nextLine();
            if(productName.isBlank()){
                System.err.println("Data cannot be empty! Please try again.");
            }else{
                return productName;
            }
        }while (true);
    }
//● productPrice - double (phải lớn hơn 0)

    private double inputProductPrice(Scanner sc) {
        System.out.println("Enter product price: ");
        do{
            String productPrice = sc.nextLine();
            if(productPrice.isBlank()){
                System.err.println("Data cannot be empty! Please try again.");
            }else{
                try{
                    Double price = Double.parseDouble(productPrice);
                    if(price < 0){
                        System.err.println("Price cannot be negative! Please try again.");
                    }else{
                        return price;
                    }
                }catch (NumberFormatException e){
                    System.err.println("Product price is integer number. Please try again.");
                }
            }
        }while(true);
    }
//● description - String

    private String inputDescription(Scanner sc) {
        System.out.println("Enter description: ");
        return sc.nextLine();
    }
//● stock - int (ít nhất là 10)

    private int inputStock(Scanner sc) {
        System.out.println("Enter stock: ");
        do {
            String stock = sc.nextLine();
            if(stock.isBlank()){
                System.err.println("Data cannot be empty! Please try again.");
            }else{
                try{
                    int stockInt = Integer.parseInt(stock);
                    if(stockInt < 10){
                        System.err.println("Stock must be greater than 10 ! Please try again.");
                    }else {
                        return stockInt;
                    }
                }catch (NumberFormatException e){
                    System.err.println("Stock is integer number and greater than 10. Please try again.");
                }
            }
        }while(true);
    }
//● catalog - Catalog (không được để null)

    private Catalog inputCate(Scanner sc) {
        CatalogService catalogService = new CatalogService();
        catalogList.forEach(Catalog::displayCatalog);
        System.out.println("Enter product catalog ID: ");

        do {
            int catalogId = inputNum(sc);
            if(catalogService.findById(catalogId) == null){
                System.err.println("Catalog does not exist. Please try again.");
            }else{
                return catalogService.findById(catalogId);
            }
        }while(true);
    }


    public void displayProduct(){
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| Id: %-5s | Name: %-15s | Price:%10.1f | Description: %-20s | Stock: %-10d | Catalog: %-10s | Status:%-10s |\n",
                productId, productName,productPrice, description,stock,catalog.getCatalogName(), status?"Bán":"Không bán");
    }

    @Override
    public String toString() {
        return "Product{" +
                "catalog=" + catalog +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", status=" + (status?"ban":"khong ban") +
                '}';
    }
}

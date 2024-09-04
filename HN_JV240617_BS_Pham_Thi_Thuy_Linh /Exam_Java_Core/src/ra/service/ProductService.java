package ra.service;

import ra.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static ra.run.BookManagement.productList;
import static ra.run.BookManagement.productS;

public class ProductService implements IGenericService<Product, String>{
    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public Product findById(String id) {

        if(productList.stream().anyMatch(p-> p.getProductId().equalsIgnoreCase(id))){
            return productList.stream().filter(p-> p.getProductId().equalsIgnoreCase(id)).findFirst().get();
        }else {
            return null;
        }
    }

    @Override
    public void delete(String id) {
        if(findById(id) == null){
            System.err.println("Product not found");
        }else{
            productList.remove(productList.indexOf(findById(id)));
            System.out.println("Deleted successfully !");
        }
    }
    public static void productMenu(Scanner sc){
        boolean flag = true;
        do {
            System.out.println("***************************PRODUCT-MANAGEMENT***************************");
            System.out.println("*                                                                      *");
            System.out.println("*       1. Nhập số sản sản phẩm và nhập thông tin sản phẩm             *");
            System.out.println("*       2. Hiển thị thông tin các sản phẩm                             *");
            System.out.println("*       3. Sắp xếp sản phẩm theo giá giảm dần                          *");
            System.out.println("*       4. Xóa sản phẩm theo mã                                        *");
            System.out.println("*       5. Tìm kiếm sách theo tên sách                                 *");
            System.out.println("*       6. Thay đổi thông tin của sách theo mã sách                    *");
            System.out.println("*       7. Quay lại                                                    *");
            System.out.println("*                                                                      *");
            System.out.println("************************************************************************");
            System.out.println("Mời nhập lựa chọn:");
            String choice = sc.nextLine();
            switch (choice) {
                case "1": {
                    addProduct(sc);
                    break;
                }
                case "2": {
                    showAllProduct();
                    break;
                }
                case "3": {
                    sortByPrice();
                    break;
                }
                case "4": {
                    deleteProductById(sc);
                    break;
                }
                case "5": {
                    findByName(sc);
                    break;
                }
                case "6": {
                    editProductById(sc);
                    break;
                }
                case "7": {
                    flag = false;
                    break;
                }
                default: {
                    System.out.println("Please enter choice form 1 to 7");
                    break;
                }
            }
        } while (flag);
    }

    private static void addProduct(Scanner sc) {
        System.out.println("Enter the id of the product you want to add:");
        int number = inputNum(sc);
        for (int i = 0; i < number; i++) {
            System.out.println("Product #" + (i + 1) + ":");
            Product productNew = new Product();
            productNew.inputProduct(sc,true);
            productList.add(productNew);
        }
        System.out.println("Add product successful");
        showAllProduct();
    }

    private static void showAllProduct() {
        if (productList.size() <= 0) {
            System.err.println("Product List is empty");
        }else {
            productList.forEach(Product::displayProduct);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void sortByPrice() {
        productList.stream().sorted(Comparator.comparingDouble(Product::getProductPrice)).toList().reversed().forEach(Product::displayProduct);
    }

    private static void deleteProductById(Scanner sc) {
        showAllProduct();
        System.out.println("Enter the id of the product you want to delete:");
       do{
           String id = sc.nextLine();
           if(id.isBlank())
               System.err.println("Data cannot be empty");
           else{
               productS.delete(id);
               showAllProduct();
           }
       }while (true);
    }

    private static void findByName(Scanner sc) {
        System.out.println("Enter the name of the product you want to find:");
        String name = sc.nextLine();
        if (name.isBlank()) {
            System.err.println("Data can not be blank");
        } else {
            System.out.println("Result");
            if(productList.stream().filter(p -> p.getProductName().contains(name)).toList().isEmpty()){
                System.err.println("Product not found");
            }else{
                productList.stream().filter(p -> p.getProductName().contains(name)).forEach(Product::displayProduct);
            }
        }
    }

    private static void editProductById(Scanner sc) {
        System.out.println("Enter the id of the product you want to edit:");
        String id = sc.nextLine();
        if(productS.findById(id) == null) {
            System.err.println("Product #" + id + " does not exist");
        }else {
            Product productOld = productS.findById(id);
            productList.remove(productOld);
            Product productNew = new Product();
            productNew.inputProduct(sc,false);
            productNew.setProductId(productOld.getProductId());
            productList.add(productNew);
            System.out.println("Product #" + id + " has been updated");
            showAllProduct();
        }
    }

    public static int inputNum(Scanner sc) {
        do {
            String number = sc.nextLine();
            if (number.isBlank()) {
                System.err.println("Data cannot be empty");
            }else {
                try{
                    int numberInt = Integer.parseInt(number);
                    if(numberInt< 0){
                        System.err.println("Please enter a positive integer");
                    }else {
                        return numberInt;
                    }
                }catch (NumberFormatException e){
                    System.err.println("Please enter a positive integer");
                }
            }
        }while (true);
    }
}

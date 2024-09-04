package ra.service;

import ra.business.entity.Catalog;

import java.util.List;
import java.util.Scanner;

import static ra.run.BookManagement.*;
import static ra.run.BookManagement.catalogS;
import static ra.service.ProductService.inputNum;

public class CatalogService implements IGenericService<Catalog, Integer>{
    @Override
    public List<Catalog> getAll() {
        return List.of();
    }

    @Override
    public void save(Catalog catalog) {

    }

    @Override
    public Catalog findById(Integer id) {
        if(catalogList.stream().anyMatch(c-> c.getCatalogId() == id)){
            return catalogList.stream().filter(c-> c.getCatalogId() == id).findFirst().get();
        }else {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        if(findById(id) == null){
            System.err.println("Catalog not found");
        }else{
            if(productList.stream().anyMatch(p-> p.getCatalog().equals(findById(id)))){
                System.err.println("This catalog has products. Can't delete");
            }else{
                catalogList.remove(findById(id));
                System.err.println("Deleted successfully !");
            }
        }
    }
    public static void catalogMenu(Scanner sc){
        boolean flag = true;
        do {
            System.out.println("***************************CATALOG-MANAGEMENT***************************");
            System.out.println("*                                                                      *");
            System.out.println("*  1. Nhập số danh mục thêm mới và nhập thông tin cho từng danh mục    *");
            System.out.println("*  2. Hiển thị thông tin tất cả các danh mục                           *");
            System.out.println("*  3. Sửa tên danh mục theo mã danh mục                                *");
            System.out.println("*  4. Xóa danh muc theo mã danh mục (lưu ý ko xóa khi có sản phẩm)     *");
            System.out.println("*  5. Quay lại                                                         *");
            System.out.println("*                                                                      *");
            System.out.println("************************************************************************");
            System.out.println("Mời nhập lựa chọn:");
            String choice = sc.nextLine();
            switch (choice) {
                case "1": {
                    addCatalog(sc);
                    break;
                }
                case "2": {
                    showAllCatalog();
                    break;
                }
                case "3": {
                    editCatalogById(sc);
                    break;
                }
                case "4": {
                    deleteCatalogById(sc);
                    break;
                }
                case "5": {
                    flag = false;
                    break;
                }
                default: {
                    System.out.println("Please enter choice form 1 to 5");
                    break;
                }
            }
        } while (flag);
    }

    private static void addCatalog(Scanner sc) {
        System.out.println("Enter the number of Catalog you want to add:");
        int number = inputNum(sc);
        for (int i = 0; i < number; i++) {
            System.out.println("Catalog #" + (i + 1) + ":");
            Catalog catalogNew = new Catalog();
            catalogNew.inputCatalog(sc);
            catalogList.add(catalogNew);
        }
        showAllCatalog();
    }

    private static void showAllCatalog() {
        if (catalogList.size() <= 0) {
            System.err.println("Catalog List is empty");
        }else {
            catalogList.forEach(Catalog::displayCatalog);
            System.out.println("---------------------------------------------------------------------------------------");
        }
    }

    private static void editCatalogById(Scanner sc) {
        showAllCatalog();
        System.out.println("Enter the id of the catalog you want to edit:");
        int id = inputNum(sc);
        if (catalogS.findById(id) != null) {
            Catalog catalogOld = catalogS.findById(id);
            catalogList.remove(catalogOld);
            Catalog catalogNew = new Catalog();
            catalogNew.inputCatalogUpdate(sc);
            catalogNew.setCatalogId(catalogOld.getCatalogId());
            catalogList.add(catalogNew);
            System.out.println("Catalog #" + id + " has been updated");
        }else {
            System.err.println("Catalog #" + id + " does not exist");
        }
        showAllCatalog();
    }

    private static void deleteCatalogById(Scanner sc) {
        showAllCatalog();
        System.out.println("Enter the id of the catalog ID you want to delete:");
        int id = inputNum(sc);
        catalogS.delete(id);
        showAllCatalog();
    }
}

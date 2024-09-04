package ra.business.entity;

import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

import static ra.run.BookManagement.catalogList;

public class Catalog {
    private int catalogId;
    private String catalogName;
    private String descriptions;

    public Catalog() {
    }

    public Catalog(int catalogId, String catalogName, String descriptions) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.descriptions = descriptions;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    //    -------------------------------
    public void inputCatalog(Scanner sc){
        this.catalogId = autoCatalogId();
        this.catalogName = inputCatalogName(sc);
        this.descriptions = inputDescriptions(sc);
    }
    public void inputCatalogUpdate(Scanner sc){
        this.catalogName = inputCatalogName(sc);
        this.descriptions = inputDescriptions(sc);
    }

    private int autoCatalogId() {
        Optional<Integer> maxId =  catalogList.stream().map(Catalog::getCatalogId).toList().stream().max(Comparator.comparingInt(Integer::intValue));
        return maxId.map(integer -> integer + 1).orElse(1);
    }

    private String inputCatalogName(Scanner sc) {
        System.out.println("Enter Catalog Name: ");
        do {
            String catalogName = sc.nextLine();
            if (catalogName.isEmpty()) {
                System.err.println("Catalog name cannot be empty");
            }else{
                if(catalogList.stream().anyMatch(c-> c.getCatalogName().equals(catalogName))){
                    System.err.println("Catalog already exists");
                }else
                    return catalogName;
            }
        }while(true);
    }

    private String inputDescriptions(Scanner sc) {
        System.out.println("Enter Descriptions: ");
        do{
            String descriptions = sc.nextLine();
            if(descriptions.isEmpty()){
                System.err.println("Descriptions cannot be empty");
            }else {
                return descriptions;
            }
        }while (true);
    }

    public void displayCatalog(){
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("| Catalog ID: %-5d  | Name: %-20s | Description: %-20s |\n",catalogId,catalogName,descriptions);
    }


}

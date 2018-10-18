package com.mungurean.productApp.view;

import com.mungurean.productApp.module.Category;
import com.mungurean.productApp.module.Description;
import com.mungurean.productApp.module.Price;
import com.mungurean.productApp.module.Product;
import com.mungurean.productApp.service.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class View {
    private Scanner input;
    private Service service;


    public View(Service service) {
        this.service = service;
        input = new Scanner(System.in);
    }

    private void menu() {
        System.out.println();
        System.out.println("1. Show all products");
        System.out.println("2. Show all descriptions");
        System.out.println("3. Show all categories");
        System.out.println("4. Add new product");
        System.out.println("5. Add new category");
        System.out.println("6. Update a product");
        System.out.println("7. Delete a product");
        System.out.println("8. Exit");
        System.out.println();
    }

    private void updateMenu(final long id) {
        System.out.println("Enter the new name , description , category and the new prices all separated by coma " +
                "(if you do not with to update a field use 2 comas with no space in between and enter the rest)");
        input.nextLine();
        String[] updateData = input.nextLine().split(",");
        Set<Price> prices = new HashSet<>();
        for (int i = 3; i < updateData.length; i++) {
            prices.add(new Price(Double.valueOf(updateData[i]), LocalDateTime.now().toString()));
        }
        service.updateProduct(id, updateData[0], updateData[1], new Category(updateData[2]), prices);

    }

    public void run() {
        while (true) {
            menu();
            switch (input.nextLine()) {
                case "1":
                    service.showAllProducts();
                    break;
                case "2":
                    service.showAllDescriptions();
                    break;
                case "3":
                    service.showAllCategories();
                    break;
                case "4":
                    Product product = new Product();
                    System.out.println("Introduce data about the product");
                    System.out.println("Product name :");
                    product.setName(input.nextLine());
                    System.out.println("Product description :");
                    product.setDescription(new Description(input.nextLine()));
                    System.out.println("Product category :");
                    product.setCategory(new Category(input.nextLine()));
                    System.out.println("Initial price of the product :");
                    Price firstPrice = new Price(input.nextDouble(), LocalDateTime.now().toString());
                    System.out.println("Do you want to add more prices? (Y/N)");
                    String prices = "";
                    if (input.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Add all the prices separated by coma :");
                        prices = input.nextLine();
                    } else if (input.nextLine().equalsIgnoreCase("n")) {
                        System.out.println("All the data added");
                    }
                    product.setPrices(service.pricesFromStringWithComaSeparator(prices, firstPrice));
                    service.addProduct(product);
                    input.nextLine();
                    break;
                case "5":
                    System.out.println("Enter the name of the category ");
                    Category category = new Category(input.nextLine());
                    service.addCategory(category);
                    break;
                case "6":
                    System.out.println("Enter the id of the product");
                    updateMenu(input.nextLong());
                    break;
                case "7":
                    System.out.println("Enter the id of the product to delete :");
                    service.deleteProduct(input.nextLong());
                    input.nextLine();
                    break;
                case "8":
                    System.exit(0);
            }
        }
    }
}

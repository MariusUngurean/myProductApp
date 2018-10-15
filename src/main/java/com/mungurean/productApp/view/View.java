package com.mungurean.productApp.view;

import java.util.Scanner;

public class View {
    private Scanner input;


    public View() {
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

    public void updateMenu(long id) {
        System.out.println("1. Update product name");
        System.out.println("2. Update product description");
        System.out.println("3. Update product category");
        System.out.println("4. Update product price list");
        System.out.println("5. Update product description and category");
        System.out.println("6. Update product description and price list");
        System.out.println("7. Update product category and price list");
        System.out.println("8. Update all fields of a product");
    }

}

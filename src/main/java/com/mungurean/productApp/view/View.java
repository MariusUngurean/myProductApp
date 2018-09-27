package com.mungurean.productApp.view;

import com.mungurean.productApp.service.Service;

import java.util.Scanner;

public class View {
    private Service service;
    private Scanner input;


    public View(Service service) {
        this.service = service;
        input = new Scanner(System.in);
    }

    private void menu() {
        System.out.println();
        System.out.println("1. Show all products");
        System.out.println("2. Add new product");
        System.out.println("3. Update a product");
        System.out.println("4. Delete a product");
        System.out.println("5. Exit");
        System.out.println();
    }
}

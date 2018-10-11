package com.mungurean.productApp.service;


import com.mungurean.productApp.module.*;

import javax.persistence.EntityTransaction;
import java.util.List;

public class Service {
    private DAOImpl dao;
    private EntityTransaction transaction;

    public Service() {

    }

    public Service(DAOImpl dao) {
        this.dao = dao;
        transaction = dao.getEntityManager().getTransaction();
    }

    public void showAllProducts() {
        try {
            transaction.begin();
            System.out.println("Here is a list of all the products");
            System.out.println(dao.getAllProducts());
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void showAllDescriptions() {
        try {
            transaction.begin();
            System.out.println("Here is a list of all the products");
            System.out.println(dao.getAllDescriptions());
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void showAllCategories() {
        try {
            transaction.begin();
            System.out.println("Here is a list of all the products");
            System.out.println(dao.getAllCategories());
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void showAllPrices() {
        try {
            transaction.begin();
            System.out.println("Here is a list of all the products");
            System.out.println(dao.getAllPrices());
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void addProduct(Product product) {
        try {
            transaction.begin();
            dao.addProduct(product);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        System.out.println("New product added");
    }

    public void addCategory(Category category) {
        try {
            transaction.begin();
            dao.addCategory(category);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        System.out.println("New category added");
    }

    public void updateProduct(long id, String name, Description description, List<Price> prices, Category category) {
        try {
            transaction.begin();
            dao.updateProduct(id, name, description, prices, category);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        System.out.println("Product updated");
    }

    public void updateCategory(long id, String name) {
        try {
            transaction.begin();
            dao.updateCategory(id, name);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        System.out.println("Product updated");
    }

    public void updateDescription(long id, String name, Description description, List<Price> prices, Category category) {
        try {
            transaction.begin();
            dao.updateProduct(id, name, description, prices, category);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        System.out.println("Product updated");
    }


}

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
        long descriptionId;
        long categoryId;
        long[] pricesIds = new long[prices.size()];
        try {
            transaction.begin();
            descriptionId = (dao.findDescriptionByText(description.getFlavorText()).isPresent())
                    ? description.getId()
                    : dao.addDescription(description);
            categoryId = (dao.findCategoryByName(category.getName()).isPresent())
                    ? category.getId()
                    : dao.addCategory(category);

            for (int i = 0; i < prices.size(); i++) {
                pricesIds[i] = (dao.findPriceByValueAndDate(prices.get(i).getPrice(), prices.get(i).getDate()).isPresent())
                        ? prices.get(i).getId()
                        : dao.addPrice(prices.get(i));
            }
            dao.updateProduct(id, name, descriptionId, categoryId, pricesIds);
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
        System.out.println("Category updated");
    }

    public void updateDescription(long id, String newFlavorText, Product newProduct) {
        try {
            transaction.begin();
            dao.updateDescription(id, newFlavorText, newProduct);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        System.out.println("Description updated");
    }

    public void updatePrice(long id, double newValue, String newDate) {
        try {
            transaction.begin();
            dao.updatePrice(id, newValue, newDate);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        System.out.println("Price updated");
    }


}

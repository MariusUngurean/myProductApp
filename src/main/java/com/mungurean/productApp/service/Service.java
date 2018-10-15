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
            List<Product> productList = dao.getAllProducts();
            for (Product product : productList
            ) {
                System.out.println(product);
            }
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
            System.out.println("Here is a list of all the descriptions");
            List<Description> descriptionList = dao.getAllDescriptions();
            for (Description description : descriptionList
            ) {
                System.out.println(description);
            }
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
            System.out.println("Here is a list of all the categories");
            List<Category> categoryList = dao.getAllCategories();
            for (Category category : categoryList
            ) {
                System.out.println(category);
            }
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
            System.out.println("New product added");
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public void addCategory(Category category) {
        try {
            transaction.begin();
            dao.addCategory(category);
            transaction.commit();
            System.out.println("New category added");
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public void updateProduct(long id, String name, String descriptionFlavorText, List<Price> prices, Category category) {
        long categoryId;
        long[] pricesIds = new long[prices.size()];
        try {
            transaction.begin();
            categoryId = (dao.findCategoryByName(category.getName()).isPresent())
                    ? category.getId()
                    : dao.addCategory(category);

            for (int i = 0; i < prices.size(); i++) {
                pricesIds[i] = (dao.findPriceByValueAndDate(prices.get(i).getPrice(), prices.get(i).getDate()).isPresent())
                        ? prices.get(i).getId()
                        : dao.addPrice(prices.get(i), id);
            }
            dao.updateProduct(id, name, descriptionFlavorText, categoryId, pricesIds);
            transaction.commit();
            System.out.println("Product updated");
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public void updateCategory(long id, String name) {
        try {
            transaction.begin();
            dao.updateCategory(id, name);
            transaction.commit();
            System.out.println("Category updated");
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public void updateDescription(long id, String newFlavorText, Product newProduct) {
        try {
            transaction.begin();
            dao.updateDescription(id, newFlavorText, newProduct);
            transaction.commit();
            System.out.println("Description updated");
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public void updatePrice(long id, double newValue, String newDate) {
        try {
            transaction.begin();
            dao.updatePrice(id, newValue, newDate);
            transaction.commit();
            System.out.println("Price updated");
        } catch (Exception e) {
            e.getStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public void deleteProduct(long id) {
        try {
            transaction.begin();
            dao.deleteProduct(id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void deleteDescription(long id) {
        try {
            transaction.begin();
            dao.deleteDescription(id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void deleteCategory(long id) {
        try {
            transaction.begin();
            dao.deleteCategory(id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void deletePrice(long id) {
        try {
            transaction.begin();
            dao.deletePrice(id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }


}

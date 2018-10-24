package com.mungurean.productApp.service;


import com.mungurean.productApp.module.dao.DAOImpl;
import com.mungurean.productApp.module.model.Category;
import com.mungurean.productApp.module.model.Description;
import com.mungurean.productApp.module.model.Price;
import com.mungurean.productApp.module.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class Service {
    private DAOImpl dao;

    @Autowired
    private EntityTransaction transaction;

    public Service() {

    }

    public Service(DAOImpl dao, EntityManager entityManager) {
        this.dao = dao;
//        transaction = entityManager.getTransaction();
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
//        try {
//            transaction.begin();
        dao.addCategory(category);
//        transaction.commit();
        System.out.println("New category added");
//        } catch (Exception e) {
//            e.getStackTrace();
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//        }

    }

    public void updateProduct(long id, String name, String descriptionFlavorText, Category category, Set<Price> prices) {
        try {
            transaction.begin();
            dao.updateProduct(id, name, descriptionFlavorText, category, prices);
            transaction.commit();
            System.out.println("Product has been updated");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive())
                transaction.rollback();
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

    public void updateDescription(long id, String newFlavorText) {
        try {
            transaction.begin();
            dao.updateDescription(id, newFlavorText);
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

    public Set<Price> pricesFromStringWithComaSeparator(String prices, Price firstPrice) {
        Set<Price> priceSet = new LinkedHashSet<>();
        priceSet.add(firstPrice);
        if (!prices.equals("")) {
            String[] priceArray = prices.split(",");
            for (String string : priceArray
            ) {
                priceSet.add(new Price(Double.valueOf(string), LocalDateTime.now().toString()));
            }
        }
        return priceSet;
    }
}

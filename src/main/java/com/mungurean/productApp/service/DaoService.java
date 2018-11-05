package com.mungurean.productApp.service;


import com.mungurean.productApp.module.DAOImpl;
import com.mungurean.productApp.module.Category;
import com.mungurean.productApp.module.Description;
import com.mungurean.productApp.module.Price;
import com.mungurean.productApp.module.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class DaoService {
    @Autowired
    private DAOImpl dao;

    public DaoService() {

    }

//    public DaoService(DAOImpl dao) {
//        this.dao = dao;
//    }

    public void showAllProducts() {
        System.out.println("Here is a list of all the products");
        List<Product> productList = dao.getAllProducts();
        for (Product product : productList
        ) {
            System.out.println(product);
        }
    }

    public void showAllDescriptions() {
        System.out.println("Here is a list of all the descriptions");
        List<Description> descriptionList = dao.getAllDescriptions();
        for (Description description : descriptionList
        ) {
            System.out.println(description);
        }
    }

    public void showAllCategories() {
        System.out.println("Here is a list of all the categories");
        List<Category> categoryList = dao.getAllCategories();
        for (Category category : categoryList
        ) {
            System.out.println(category);
        }
    }

    public void showAllPrices() {
        System.out.println("Here is a list of all the products");
        System.out.println(dao.getAllPrices());
    }

    public void addProduct(Product product) {
        dao.addProduct(product);
        System.out.println("New product added");
    }

    public void addCategory(Category category) {
        dao.addCategory(category);
        System.out.println("New category added");
    }

    public void updateProduct(long id, String name, String descriptionFlavorText, Category category, Set<Price> prices) {
        dao.updateProduct(id, name, descriptionFlavorText, category, prices);
        System.out.println("Product has been updated");
    }

    public void updateCategory(long id, String name) {
        dao.updateCategory(id, name);
        System.out.println("Category updated");
    }

    public void updateDescription(long id, String newFlavorText) {
        dao.updateDescription(id, newFlavorText);
        System.out.println("Description updated");
    }

    public void updatePrice(long id, double newValue, String newDate) {
        dao.updatePrice(id, newValue, newDate);
        System.out.println("Price updated");
    }

    public void deleteProduct(long id) {
        dao.deleteProduct(id);
    }


    public void deleteCategory(long id) {
        dao.deleteCategory(id);

    }

    public void deletePrice(long id) {
        dao.deletePrice(id);
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

    public void deleteAllProducts() {
        dao.deleteFromTable("product");
    }
}

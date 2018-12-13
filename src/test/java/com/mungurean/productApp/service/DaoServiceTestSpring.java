package com.mungurean.productApp.service;

import com.mungurean.productApp.module.Category;
import com.mungurean.productApp.module.Description;
import com.mungurean.productApp.module.Price;
import com.mungurean.productApp.module.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/spring.xml")
public class DaoServiceTestSpring {

    @Autowired
    private DaoService service;

    @Before
    public void setUp() throws Exception {
        service = new DaoService();
        Product product = new Product("testName",
                new Description("testDescription"),
                new HashSet<Price>(Arrays.asList(new Price(123, "10/10/2018"),
                        new Price(456, "12/12/2018"))),
                new Category("testCategory"));
        service.addProduct(product);
    }

    @After
    public void tearDown() throws Exception {
        service.deleteAllProducts();
    }

    @Test
    public void showAllProducts() {
        service.showAllProducts();
    }

    @Test
    public void showAllDescriptions() {
    }

    @Test
    public void showAllCategories() {
    }

    @Test
    public void showAllPrices() {
    }

    @Test
    public void addProduct() {
    }

    @Test
    public void addCategory() {
    }

    @Test
    public void updateProduct() {
    }

    @Test
    public void updateCategory() {
    }

    @Test
    public void updateDescription() {
    }

    @Test
    public void updatePrice() {
    }

    @Test
    public void deleteProduct() {
    }

    @Test
    public void deleteCategory() {
    }

    @Test
    public void deletePrice() {
    }

    @Test
    public void pricesFromStringWithComaSeparator() {
    }
}
package com.mungurean.productApp.service;

import com.mungurean.productApp.module.DAOImpl;
import com.mungurean.productApp.module.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


public class ServiceTest {
    EntityManager entityManager = mock(EntityManager.class);
    private final DAOImpl dao = mock(DAOImpl.class);
    private final Service service = new Service(dao);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addProduct() {
        doNothing().when(dao).addProduct(isA(Product.class));
        Product product = new Product("test", null, null, null);
        service.addProduct(product);
        verify(dao, times(1)).addProduct(product);
    }

    @Test
    public void addCategory() {
    }

    @Test
    public void updateProduct() {
    }

    @Test
    public void deleteProduct() {
    }

    @Test
    public void pricesFromStringWithComaSeparator() {
    }
}
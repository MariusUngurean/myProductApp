package com.mungurean.productApp.service;

import com.mungurean.productApp.module.Category;
import com.mungurean.productApp.module.DAOImpl;
import com.mungurean.productApp.module.Price;
import com.mungurean.productApp.module.Product;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ServiceTest {
    private final EntityManager entityManager =
            Persistence.createEntityManagerFactory("projectDatabase").createEntityManager();
    private final DAOImpl dao = mock(DAOImpl.class);
    private final Service service = new Service(dao, entityManager);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addProduct() {
        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("called with arguments: " + Arrays.toString(args));
            return null;
        }).when(dao).addProduct(isA(Product.class));
        Product product = new Product("test", null, null, null);
        service.addProduct(product);
        verify(dao, times(1)).addProduct(product);
    }

    @Test
    public void addCategory() {
        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("called with arguments: " + Arrays.toString(args));
            return null;
        }).when(dao).addCategory(isA(Category.class));
        Category category = new Category("test");
        service.addCategory(category);
        verify(dao, times(1)).addCategory(category);
    }

    @Test
    public void updateProduct() {
        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("called with arguments: " + Arrays.toString(args));
            return null;
        }).when(dao).addCategory(isA(Category.class));
        Category category = new Category("test");
        service.addCategory(category);
        verify(dao, times(1)).addCategory(category);
    }

    @Test
    public void deleteProduct() {
        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("called with arguments: " + Arrays.toString(args));
            return null;
        }).when(dao).deleteProduct(isA(Long.class));
        long id = 1283793;
        service.deleteProduct(id);
        verify(dao, times(1)).deleteProduct(id);
    }

    @Test
    public void pricesFromStringWithComaSeparator() {
        String prices = "123,456";
        Price firstPrice = new Price(589, LocalDateTime.now().toString());
        Set<Price> priceSet = service.pricesFromStringWithComaSeparator(prices, firstPrice);
        double[] values = new double[]{589, 123, 456};
        int i = 0;
        for (Price price : priceSet
        ) {
            assertThat(priceEquals(price, values[i])).isTrue();
            i++;
        }
    }

    private boolean priceEquals(Price that, Double other) {
        return that.getPrice() == other;
    }
}
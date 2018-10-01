package com.mungurean.productApp;

import com.mungurean.productApp.module.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Unit test for simple App.
 */

public class AppTest {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("projectDatabase");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private DAOImpl dao = new DAOImpl(entityManager);

    @Before
    public void setUp() {
        Category c1 = new Category("testCategory1");
        Category c2 = new Category("testCategory2");
        Price p11 = new Price(123, "12/12/2018");
        Price p12 = new Price(321, "10/12/2018");
        Price p21 = new Price(265, "20/05/2018");
        Price p22 = new Price(756, "06/02/2018");
        List<Price> ps1 = new ArrayList<>();
        List<Price> ps2 = new ArrayList<>();
        ps1.add(p11);
        ps1.add(p12);
        ps2.add(p21);
        ps2.add(p22);
        Description d1 = new Description("testDescription1");
        Description d2 = new Description("testDescription2");
        Product p1 = new Product("testName1", d1, ps1, c1);
        Product p2 = new Product("testName2", d2, ps2, c2);
        dao.addPrice(p11);
        dao.addPrice(p12);
        dao.addPrice(p21);
        dao.addPrice(p22);
        dao.addProduct(p1);
        dao.addProduct(p2);
    }

    @After
    public void tearDown() {
        try {
            entityManager.getTransaction().begin();
            dao.deleteFromTable("Product");
            dao.deleteFromTable("Category");
            dao.deleteFromTable("Description");
            dao.deleteFromTable("Price");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    public void getAllProducts() {
        List<Product> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            list = dao.getAllProducts();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(list).containsExactlyElementsOf(dao.getAllProducts());
    }

    @Test
    public void getAllDescriptions() {
        List<Description> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            list = dao.getAllDescriptions();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(list).containsExactlyElementsOf(dao.getAllDescriptions());
    }

    @Test
    public void getAllCategories() {
        List<Category> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            list = dao.getAllCategories();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(list).containsExactlyElementsOf(dao.getAllCategories());
    }

    @Test
    public void getAllPrices() {
        List<Price> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            list = dao.getAllPrices();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(list).containsExactlyElementsOf(dao.getAllPrices());
    }

    @Test
    public void addProduct() {
        Product p = new Product("name1",
                new Description("descr1"),
                new ArrayList<>(),
                new Category("cat1"));
        try {
            entityManager.getTransaction().begin();
            dao.addProduct(p);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllProducts()).contains(p);
    }

    @Test
    public void addCategory() {
        Category c = new Category("category1");
        try {
            entityManager.getTransaction().begin();
            dao.addCategory(c);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllCategories()).contains(c);
    }

    @Test
    public void addDescriptionThroughProduct() {
        Description d = new Description("description1");
        try {
            entityManager.getTransaction().begin();
            Product p = new Product("name1", d, new ArrayList<>(), new Category("test"));
            d.setProduct(p);
            dao.addProduct(p);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllDescriptions()).contains(d);
    }

    @Test
    public void addPrice() {
        Price p = new Price(123, "02/10/2018");
        try {
            entityManager.getTransaction().begin();
            dao.addPrice(p);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllPrices()).contains(p);
    }

    @Test
    public void deleteAllFromProduct() {
        try {
            entityManager.getTransaction().begin();
            dao.deleteFromTable("Product");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllProducts()).isEmpty();
    }

    @Test
    public void deleteAllFromPrice() {
        try {
            entityManager.getTransaction().begin();
            dao.deleteFromTable("Price");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllPrices()).isEmpty();
    }

    @Test
    public void deleteAllFromCategory() {
        try {
            entityManager.getTransaction().begin();
            dao.deleteFromTable("Category");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllCategories()).isEmpty();
    }

    @Test
    public void deleteAllFromDescription() {
        try {
            entityManager.getTransaction().begin();
            dao.deleteFromTable("Description");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllDescriptions()).isEmpty();
    }

    @Test
    public void updateProduct() {
        Product p = new Product();
        Description d = new Description("newDescription");
        List<Price> pr = Arrays.asList(new Price(456, "22/10/2018"), new Price(789, "10/05/2018"));
        Category c = new Category("newCategory");
        try {
            entityManager.getTransaction().begin();
            p = dao.getAllProducts().get(0);
            dao.updateProduct(p.getId(), "newName", d, pr, c);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(p.getName()).isEqualToIgnoringCase("newName");
        assertThat(p.getDescription().getFlavorText()).isEqualTo(d.getFlavorText());
        for (int i = 0; i < p.getPrices().size(); i++) {
            assertThat(p.getPrices().get(i).getPrice()).isEqualTo(pr.get(i).getPrice());
        }
        assertThat(p.getCategory().getName()).isEqualTo(c.getName());
    }

    @Test
    public void updateCategory() {
        Category c = new Category();
        try {
            entityManager.getTransaction().begin();
            c = dao.getAllCategories().get(0);
            dao.updateCategory(c.getId(), "newCategoryName");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(c.getName()).isEqualTo("newCategoryName");
    }

    @Test
    public void updateDescription() {
        Description d = new Description();
        Product newP = new Product();
        Product p = new Product();
        try {
            entityManager.getTransaction().begin();
            newP = dao.getAllProducts().get(dao.getAllProducts().size() - 1);
            d = dao.getAllDescriptions().get(0);
            p = d.getProduct();
            dao.updateDescription(d.getId(), "newDescriptionText", newP);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(d.getFlavorText()).isEqualTo("newDescriptionText");
        assertThat(d.getProduct()).isEqualTo(newP);
        assertThat(newP.getDescription()).isEqualTo(d);
        assertThat(p.getDescription()).isNull();
    }

    @Test
    public void updatePrice() {
        Price p = new Price();
        try {
            entityManager.getTransaction().begin();
            p = dao.getAllPrices().get(0);
            dao.updatePrice(p.getId(), 456.2, "10/10/2200");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(p.getPrice()).isEqualTo(456.2);
        assertThat(p.getDate()).isEqualTo("10/10/2200");
    }

    @Test
    public void deleteProduct() {
        Product p = new Product();
        Description d = new Description();
        List<Price> pr = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            p = dao.getAllProducts().get(0);
            d = p.getDescription();
            pr = p.getPrices();
            dao.deleteProduct(p.getId());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllProducts()).doesNotContain(p);
        assertThat(dao.getAllDescriptions()).doesNotContain(d);
        for (Price price : pr) {
            assertThat(dao.getAllPrices()).doesNotContain(price);
        }
    }

    @Test
    public void deleteDescription() {

        // Product p = new Product();
        try {
            entityManager.getTransaction().begin();
            Description description = dao.getAllDescriptions().get(0);
            //  p = dao.getAllProducts().get(0);
            //  dao.deleteDescription(description.getId());
            entityManager.remove(description);
            entityManager.getTransaction().commit();
            //  assertThat(dao.getAllDescriptions()).doesNotContain(description);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

        System.out.println(dao.getAllProducts());
        //  assertThat(p.getDescription()).isNull();
    }

    @Test
    public void deleteCategory() {
        Category c = new Category();
        Product p = new Product();
        try {
            entityManager.getTransaction().begin();
            p = dao.getAllProducts().get(0);
            c = p.getCategory();
            dao.deleteCategory(c.getId());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllCategories()).doesNotContain(c);
        assertThat(p.getCategory()).isNull();
    }

    @Test
    public void deletePrice() {
        Price p = new Price();
        Product pr = new Product();
        try {
            entityManager.getTransaction().begin();
            p = dao.getAllPrices().get(0);
            pr = p.getProduct();
            dao.deletePrice(p.getId());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllPrices()).doesNotContain(p);
        assertThat(pr.getPrices()).doesNotContain(p);
    }
}


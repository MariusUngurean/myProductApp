package com.mungurean.productApp;

import com.mungurean.productApp.module.dao.DAOImpl;
import com.mungurean.productApp.module.model.Category;
import com.mungurean.productApp.module.model.Description;
import com.mungurean.productApp.module.model.Price;
import com.mungurean.productApp.module.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class DAOImplTest {

    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("projectDatabase");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private DAOImpl dao = new DAOImpl(entityManager);

    private void initializeTableRows() {
        Category c1 = new Category("testCategory1");
        Category c2 = new Category("testCategory2");
        Price p11 = new Price(123, "12/12/2018");
        Price p12 = new Price(321, "10/12/2018");
        Price p21 = new Price(265, "20/05/2018");
        Price p22 = new Price(756, "06/02/2018");
        Set<Price> ps1 = new LinkedHashSet<>();
        Set<Price> ps2 = new LinkedHashSet<>();
        ps1.add(p11);
        ps1.add(p12);
        ps2.add(p21);
        ps2.add(p22);
        Description d1 = new Description("testDescription1");
        Description d2 = new Description("testDescription2");
        Product p1 = new Product("testName1", d1, ps1, c1);
        Product p2 = new Product("testName2", d2, ps2, c2);
        dao.addPrice(p11, -1);
        dao.addPrice(p12, -1);
        dao.addPrice(p21, -1);
        dao.addPrice(p22, -1);
        dao.addProduct(p1);
        dao.addProduct(p2);
    }

    @Before
    public void setUp() {
        initializeTableRows();
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
            list.addAll(dao.getAllDescriptions());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllDescriptions())
                .containsExactlyElementsOf(list)
                .isNotEmpty();
    }

    @Test
    public void getAllCategories() {
        List<Category> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            list.addAll(dao.getAllCategories());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllCategories())
                .containsExactlyElementsOf(list)
                .isNotEmpty();
    }

    @Test
    public void getAllPrices() {
        List<Price> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            list.addAll(dao.getAllPrices());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(dao.getAllPrices())
                .containsExactlyElementsOf(list)
                .isNotEmpty();
    }

    @Test
    public void addProduct() {
        Product p = new Product("name1",
                new Description("descr1"),
                new LinkedHashSet<>(),
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
        assertThat(dao.findDescriptionByText("descr1")).isNotEmpty();
        assertThat(dao.findCategoryByName("cat1")).isNotEmpty();
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
            Product p = new Product("name1", d, new LinkedHashSet<>(), new Category("test"));
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
            dao.addPrice(p, -1);
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
    public void updateProductWithNewEntities() {
        Product product = new Product();
        String flavorText = "newDescription";
        String newName = "newName";
        Price price1 = new Price(456, "22/10/2018");
        Price price2 = new Price(789, "10/05/2018");
        Set<Price> priceHashSet = new HashSet<>();
        priceHashSet.add(price1);
        priceHashSet.add(price2);
        Category category = new Category("newCategory");
        Set<Price> oldPrices = new HashSet<>();

        try {
            entityManager.getTransaction().begin();
            product = dao.getAllProducts().get(0);
            oldPrices.addAll(product.getPrices());
            dao.updateProduct(product.getId(), newName, flavorText, category, priceHashSet);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(product.getName()).isEqualToIgnoringCase("newName");
        assertThat(product.getDescription().getFlavorText()).isEqualTo(flavorText);
        assertThat(product.getPrices()).contains(price1, price2);
        assertThat(product.getCategory().getName()).isEqualTo(category.getName());
        assertThat(dao.getAllPrices()).doesNotContainAnyElementsOf(oldPrices);
    }

    @Test
    public void updateProductCategoryAndPriceSet() {
        Product p = new Product();
        String flavorText = "";
        String newName = "";
        Price price1 = new Price(456, "22/10/2018");
        Price price2 = new Price(789, "10/05/2018");
        Set<Price> pr = new HashSet<>();
        pr.add(price1);
        pr.add(price2);
        Category c = new Category("newCategory");
        Set<Price> oldPrices = new HashSet<>();

        try {
            entityManager.getTransaction().begin();
            p = dao.getAllProducts().get(0);
            oldPrices.addAll(p.getPrices());
            dao.updateProduct(p.getId(), newName, flavorText, c, pr);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(p.getName()).isNotEqualToIgnoringCase("newName");
        assertThat(p.getDescription().getFlavorText()).isNotEqualTo(flavorText);
        assertThat(p.getPrices()).contains(price1, price2);
        assertThat(p.getCategory().getName()).isEqualTo(c.getName());
        assertThat(dao.getAllPrices()).doesNotContainAnyElementsOf(oldPrices);
    }

    @Test
    public void updateProductNameAndDescription() {
        Product p = new Product();
        String flavorText = "newDescription";
        String newName = "newName";
        Set<Price> oldPrices = new HashSet<>();

        try {
            entityManager.getTransaction().begin();
            p = dao.getAllProducts().get(0);
            oldPrices.addAll(p.getPrices());
            dao.updateProduct(p.getId(), newName, flavorText, null, null);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(p.getName()).isEqualToIgnoringCase("newName");
        assertThat(p.getDescription().getFlavorText()).isEqualTo(flavorText);
        assertThat(p.getPrices()).doesNotContain(null, null);
        assertThat(p.getCategory()).isNotNull();
        assertThat(dao.getAllPrices()).containsAll(oldPrices);
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
        Description description = new Description();
        Product product = new Product();
        try {
            entityManager.getTransaction().begin();
            description = dao.getAllDescriptions().get(0);
            product = description.getProduct();
            dao.updateDescription(description.getId(), "newDescriptionText");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(description.getFlavorText()).isEqualTo("newDescriptionText");
        assertThat(product.getDescription()).isEqualTo(description);
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
        Set<Price> pr = new HashSet<>();
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

        Product p = new Product();
        Description d = new Description();
        try {
            entityManager.getTransaction().begin();
            p = dao.getAllProducts().get(0);
            d = p.getDescription();
            dao.deleteDescription(d.getId());
            entityManager.remove(d);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        assertThat(p.getDescription()).isNull();
        assertThat(dao.getAllDescriptions()).doesNotContain(d);
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


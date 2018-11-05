package com.mungurean.productApp;

import com.mungurean.productApp.module.DAOImpl;
import com.mungurean.productApp.module.Category;
import com.mungurean.productApp.module.Description;
import com.mungurean.productApp.module.Price;
import com.mungurean.productApp.module.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/spring.xml")
@Transactional

public class DAOImplTest {

    //private final ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceConfig.class);
    // private final EntityManagerFactory emf = (EntityManagerFactory) ctx.getBean("entityManagerFactory");

    private final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
    @Autowired
    private DAOImpl dao;


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
        dao.deleteFromTable("Product");
        dao.deleteFromTable("Category");
        dao.deleteFromTable("Description");
        dao.deleteFromTable("Price");
    }

    @Test
    public void getAllProducts() {
        List<Product> list = dao.getAllProducts();
        assertThat(list).containsExactlyElementsOf(dao.getAllProducts());
    }

    @Test
    public void getAllDescriptions() {
        List<Description> list = new ArrayList<>(dao.getAllDescriptions());
        assertThat(dao.getAllDescriptions())
                .containsExactlyElementsOf(list)
                .isNotEmpty();
    }

    @Test
    public void getAllCategories() {

        List<Category> list = new ArrayList<>(dao.getAllCategories());

        assertThat(dao.getAllCategories())
                .containsExactlyElementsOf(list)
                .isNotEmpty();
    }

    @Test
    public void getAllPrices() {
        List<Price> list = new ArrayList<>(dao.getAllPrices());
        assertThat(dao.getAllPrices())
                .containsExactlyElementsOf(list)
                .isNotEmpty();
    }

    @Test
    public void addProduct() {
        Product product = new Product("name1",
                new Description("descr1"),
                new LinkedHashSet<>(),
                new Category("cat1"));
        dao.addProduct(product);
        dao.flushAndClear();
        assertThat(dao.getAllProducts()).contains(product);
        assertThat(dao.findDescriptionByText("descr1")).isNotEmpty();
        assertThat(dao.findCategoryByName("cat1")).isNotEmpty();
    }

    @Test
    public void addCategory() {
        Category c = new Category("category1");
        dao.addCategory(c);
        assertThat(dao.getAllCategories()).contains(c);
    }

    @Test
    public void addDescriptionThroughProduct() {
        Description description = new Description("description1");
        Product product = new Product("name1", description, new LinkedHashSet<>(), new Category("test"));
        description.setProduct(product);
        dao.addProduct(product);
        assertThat(dao.getAllDescriptions()).contains(description);
    }

    @Test
    public void addPrice() {
        Price price = new Price(123, "02/10/2018");
        dao.addPrice(price, -1);
        assertThat(dao.getAllPrices()).contains(price);
    }

    @Test
    public void deleteAllFromProduct() {
        dao.deleteFromTable("Product");
        assertThat(dao.getAllProducts()).isEmpty();
    }

    @Test
    public void deleteAllFromPrice() {
        dao.deleteFromTable("Price");
        assertThat(dao.getAllPrices()).isEmpty();
    }

    @Test
    public void deleteAllFromCategory() {
        dao.deleteFromTable("Category");
        assertThat(dao.getAllCategories()).isEmpty();
    }

    @Test
    public void deleteAllFromDescription() {
        dao.deleteFromTable("Description");
        assertThat(dao.getAllDescriptions()).isEmpty();
    }

    @Test
    public void updateProductWithNewEntities() {
        String flavorText = "newDescription";
        String newName = "newName";
        Price price1 = new Price(456, "22/10/2018");
        Price price2 = new Price(789, "10/05/2018");
        Set<Price> priceHashSet = new HashSet<>();
        priceHashSet.add(price1);
        priceHashSet.add(price2);
        Category category = new Category("newCategory");
        Product product = dao.getAllProducts().get(0);
        Set<Price> oldPrices = new HashSet<>(product.getPrices());
        dao.updateProduct(product.getId(), newName, flavorText, category, priceHashSet);
        assertThat(product.getName()).isEqualToIgnoringCase("newName");
        assertThat(product.getDescription().getFlavorText()).isEqualTo(flavorText);
        assertThat(product.getPrices()).contains(price1, price2);
        assertThat(product.getCategory().getName()).isEqualTo(category.getName());
        assertThat(dao.getAllPrices()).doesNotContainAnyElementsOf(oldPrices);
    }

    @Test
    public void updateProductCategoryAndPriceSet() {
        String flavorText = "";
        String newName = "";
        Price price1 = new Price(456, "22/10/2018");
        Price price2 = new Price(789, "10/05/2018");
        Set<Price> prices = new HashSet<>();
        prices.add(price1);
        prices.add(price2);
        Category category = new Category("newCategory");
        Product product = dao.getAllProducts().get(0);
        Set<Price> oldPrices = new HashSet<>(product.getPrices());
        dao.updateProduct(product.getId(), newName, flavorText, category, prices);
        assertThat(product.getName()).isNotEqualToIgnoringCase("newName");
        assertThat(product.getDescription().getFlavorText()).isNotEqualTo(flavorText);
        assertThat(product.getPrices()).contains(price1, price2);
        assertThat(product.getCategory().getName()).isEqualTo(category.getName());
        assertThat(dao.getAllPrices()).doesNotContainAnyElementsOf(oldPrices);
    }

    @Test
    public void updateProductNameAndDescription() {
        String flavorText = "newDescription";
        String newName = "newName";
        Product p = dao.getAllProducts().get(0);
        Set<Price> oldPrices = new HashSet<>(p.getPrices());
        dao.updateProduct(p.getId(), newName, flavorText, null, null);
        assertThat(p.getName()).isEqualToIgnoringCase("newName");
        assertThat(p.getDescription().getFlavorText()).isEqualTo(flavorText);
        assertThat(p.getPrices()).doesNotContain(null, null);
        assertThat(p.getCategory()).isNotNull();
        assertThat(dao.getAllPrices()).containsAll(oldPrices);
    }

    @Test
    public void updateCategory() {
        Category category = dao.getAllCategories().get(0);
        dao.updateCategory(category.getId(), "newCategoryName");
        assertThat(category.getName()).isEqualTo("newCategoryName");
    }

    @Test
    public void updateDescription() {
        Description description = dao.getAllDescriptions().get(0);
        Product product = description.getProduct();
        dao.updateDescription(description.getId(), "newDescriptionText");
        assertThat(description.getFlavorText()).isEqualTo("newDescriptionText");
        assertThat(product.getDescription()).isEqualTo(description);
    }

    @Test
    public void updatePrice() {
        Price p = dao.getAllPrices().get(0);
        dao.updatePrice(p.getId(), 456.2, "10/10/2200");
        assertThat(p.getPrice()).isEqualTo(456.2);
        assertThat(p.getDate()).isEqualTo("10/10/2200");
    }

    @Test
    public void deleteProduct() {
        Product p = dao.getAllProducts().get(0);
        Description d = p.getDescription();
        Set<Price> pr = p.getPrices();
        dao.deleteProduct(p.getId());
        assertThat(dao.getAllProducts()).doesNotContain(p);
        assertThat(dao.getAllDescriptions()).doesNotContain(d);
        for (Price price : pr) {
            assertThat(dao.getAllPrices()).doesNotContain(price);
        }
    }

    @Test
    public void deleteDescription() {
        Product p = dao.getAllProducts().get(0);
        Description d = p.getDescription();
        dao.deleteDescription(d.getId());
        assertThat(p.getDescription()).isNull();
        assertThat(dao.getAllDescriptions()).doesNotContain(d);
    }

    @Test
    public void deleteCategory() {
        Product p = dao.getAllProducts().get(0);
        Category c = p.getCategory();
        dao.deleteCategory(c.getId());
        assertThat(dao.getAllCategories()).doesNotContain(c);
        assertThat(p.getCategory()).isNull();
    }

    @Test
    public void deletePrice() {
        Price p = dao.getAllPrices().get(0);
        Product pr = p.getProduct();
        dao.deletePrice(p.getId());
        assertThat(dao.getAllPrices()).doesNotContain(p);
        assertThat(pr.getPrices()).doesNotContain(p);
    }
}


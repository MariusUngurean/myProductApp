package com.mungurean.productApp.module;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


public class DAOImpl {
    private EntityManager entityManager;

    public DAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //getAll methods
    public List<Product> getAllProducts() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p ORDER BY id ASC", Product.class);
        return query.getResultList();
    }

    public List<Category> getAllCategories() {
        TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c ORDER BY id ASC", Category.class);
        return query.getResultList();
    }

    public List<Description> getAllDescriptions() {
        TypedQuery<Description> query = entityManager.createQuery("SELECT d FROM Description d ORDER BY id ASC", Description.class);
        return query.getResultList();
    }

    public List<Price> getAllPrices() {
        TypedQuery<Price> query = entityManager.createQuery("SELECT p FROM Price p ORDER BY id ASC", Price.class);
        return query.getResultList();
    }

    //find methods
    public Optional<Product> findProduct(long id) {
        try {
            return Optional.ofNullable(entityManager.find(Product.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Category> findCategory(long id) {
        try {
            return Optional.ofNullable(entityManager.find(Category.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Category> findCategory(String name) {
        return getAllCategories()
                .stream()
                .filter((category) -> category.getName().equals(name))
                .findFirst();
    }

    public Optional<Description> findDescription(long id) {
        try {
            return Optional.ofNullable(entityManager.find(Description.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Price> findPrice(long id) {
        try {
            return Optional.of(entityManager.find(Price.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    //add methods
    public long addProduct(Product product) {
        if (!findCategory(product.getCategory().getId()).isPresent()) {
            addCategory(product.getCategory());
        }
        product.getDescription().setProduct(product);
        for (Price p : product.getPrices()
        ) {
            p.setProduct(product);
        }
        entityManager.persist(product);
        return product.getId();
    }

    public long addCategory(Category category) {
        entityManager.persist(category);
        return category.getId();
    }

    public long addPrice(Price price) {
        entityManager.persist(price);
        return price.getId();
    }

    //update methods
    public void updateProduct(long id, String name, Description description, List<Price> prices, Category category) {
        findProduct(id).ifPresent(product -> {
            product.setName(name);
            findCategory(product.getCategory().getId()).ifPresent(category1 -> updateCategory(category1.getId(), category.getName()));
            findDescription(product.getDescription().getId()).ifPresent(description1 -> updateDescription(description1.getId(), description.getFlavorText(), product));
            updatePrices(id, prices);
        });
    }

    public void updateCategory(long id, String name) {
        findCategory(id).ifPresent(category -> category.setName(name));
    }

    public void updateDescription(long id, String text, Product product) {
        findDescription(id).ifPresent(description -> {
            description.setFlavorText(text);
            description.getProduct().setDescription(null);
            description.setProduct(product);
            product.setDescription(description);
        });
    }

    public void updatePrice(long id, Double price, String date) {
        findPrice(id).ifPresent(price1 -> {
            price1.setPrice(price);
            price1.setDate(date);
        });

    }

    public void updatePrices(long id, List<Price> prices) {
        findProduct(id).ifPresent(product -> {
            if (prices.size() == 0 || product.getPrices().size() == 0) {
                return;
            }
            List<Price> productPrices = product.getPrices();
            for (int i = 0; i < productPrices.size(); i++) {
                Price p = prices.get(i);
                updatePrice(productPrices.get(i).getId(), p.getPrice(), p.getDate());
            }
        });

    }

    //delete methods
    public void deleteProduct(long id) {
        findProduct(id).ifPresent((product) -> {
            deleteDescription(product.getDescription().getId());
            deletePrices(product.getPrices());
            entityManager.remove(product);
        });
    }

    public void deleteCategory(long id) {
        findCategory(id).ifPresent((category) -> {
            for (Product p : getAllProducts()
            ) {
                if (p.getCategory().equals(category)) {
                    p.setCategory(null);
                }
            }
            entityManager.remove(category);
        });
    }

    public void deleteDescription(long id) {
        findDescription(id).ifPresent((description) -> {
          //  description.getProduct().setDescription(null);
            entityManager.remove(description);
        });
    }

    public void deletePrice(long id) {
        findPrice(id).ifPresent((price) -> {
            price.getProduct().removePrice(price);
            entityManager.remove(price);
        });
    }

    public void deletePrices(List<Price> prices) {
        int batch = 0;
        for (Price p : prices
        ) {
            entityManager.remove(p);
            p.setProduct(null);
            batch++;
            if (batch % 100 == 0) {
                flushAndClear();
            }
        }
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    //deleteFromTable method

    public void deleteFromTable(String table) {
        Query query = entityManager.createQuery("DELETE FROM " + table);
        query.executeUpdate();
    }


}

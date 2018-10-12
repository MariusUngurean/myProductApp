package com.mungurean.productApp.module;


import javax.persistence.EntityManager;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DAOImpl {
    private EntityManager entityManager;

    public DAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    //getAll methods

    public List<Product> getAllProducts() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p ORDER BY id ASC", Product.class);
        return query.getResultList();
    }

    public List<Category> getAllCategories() {
        TypedQuery<Category> query =
                entityManager
                        .createQuery("SELECT c FROM Category c ORDER BY id ASC", Category.class);
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

    private Optional<Product> findProductById(long id) {
        try {
            return Optional.ofNullable(entityManager.find(Product.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<Category> findCategoryById(long id) {
        try {
            return Optional.ofNullable(entityManager.find(Category.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Category> findCategoryByName(String name) {
        return getAllCategories()
                .stream()
                .filter((category) -> category.getName().equals(name))
                .findFirst();
    }

    private Optional<Description> findDescriptionById(long id) {
        try {
            return Optional.ofNullable(entityManager.find(Description.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<Price> findPriceById(long id) {
        try {
            return Optional.of(entityManager.find(Price.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Description> findDescriptionByText(String flavorText) {
        return getAllDescriptions()
                .stream()
                .filter(description -> description.getFlavorText().equals(flavorText))
                .findFirst();
    }

    public Optional<Price> findPriceByValueAndDate(double value, String date) {
        return getAllPrices()
                .stream()
                .filter(price -> price.getPrice() == value && price.getDate().equals(date))
                .findFirst();
    }

    //add methods

    public long addProduct(Product product) {
        if (!findCategoryById(product.getCategory().getId()).isPresent()) {
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

    public long addDescription(Description description) {
        entityManager.persist(description);
        return description.getId();
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

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void updateProduct(long prodId, String newProductName, long newDescriptionId, long newCategoryId, long[] newPricesIds) {
        findProductById(prodId).ifPresent(product -> {
            product.setName(newProductName);
            product.setDescription(findDescriptionById(newDescriptionId).get());
            product.setCategory(findCategoryById(newCategoryId).get());
            List<Price> prices = new LinkedList<>();
            for (long priceId : newPricesIds
            ) {
                prices.add(findPriceById(priceId).get());
            }
            product.setPrices(prices);
        });
    }

    public void updateCategory(long id, String newName) {
        findCategoryById(id).ifPresent(category -> category.setName(newName));
    }

    public void updateDescription(long id, String newFlavorText, Product newProduct) {
        findDescriptionById(id).ifPresent(description -> {
            description.setFlavorText(newFlavorText);
            description.getProduct().setDescription(null);
            description.setProduct(newProduct);
            newProduct.setDescription(description);
        });
    }

    public void updatePrice(long id, Double newPrice, String newDate) {
        findPriceById(id).ifPresent(price1 -> {
            price1.setPrice(newPrice);
            price1.setDate(newDate);
        });

    }

    public void updatePricesList(long id, List<Price> newPricesList) {
        findProductById(id).ifPresent(product -> {
            if (newPricesList.size() == 0 || product.getPrices().size() == 0) {
                return;
            }
            List<Price> productPrices = product.getPrices();
            for (int i = 0; i < productPrices.size(); i++) {
                Price p = newPricesList.get(i);
                updatePrice(productPrices.get(i).getId(), p.getPrice(), p.getDate());
            }
        });

    }

    //delete methods

    public void deleteProduct(long id) {
        findProductById(id).ifPresent((product) -> {
            deleteDescription(product.getDescription().getId());
            deletePrices(product.getPrices());
            entityManager.remove(product);
        });
    }

    public void deleteCategory(long id) {
        findCategoryById(id).ifPresent((category) -> {
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
        findDescriptionById(id).ifPresent((description) -> {
            description.getProduct().setDescription(null);
            entityManager.remove(description);
        });
    }

    public void deletePrice(long id) {
        findPriceById(id).ifPresent((price) -> {
            price.getProduct().removePrice(price);
            entityManager.remove(price);
        });
    }

    private void deletePrices(List<Price> prices) {
        int batch = 0;
        for (Price price : prices
        ) {
            entityManager.remove(price);
            price.setProduct(null);
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

    public void deleteFromTable(String tableName) {
        Query query = entityManager.createQuery("DELETE FROM " + tableName);
        query.executeUpdate();
    }


}

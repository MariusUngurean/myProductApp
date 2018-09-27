package com.mungurean.productApp.module;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;


    // one to many prices-time
    @OneToMany(mappedBy = "product")
    private List<Price> prices;
    //many to one product-category
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    //one to one product-description
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Description description;

    public Product() {
    }


    public Product(String name, Description description, List<Price> prices, Category category) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.prices = prices;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public List<Price> getPrices() {
        return prices;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ",description=" + description + ",prices=" + prices + "," + category + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product p = (Product) obj;
            return p.getName().equals(name) && p.getDescription().equals(description) && p.getPrices() == prices;
        }
        return false;
    }

    public void removePrice(Price price) {
        for (Price p : prices
        ) {
            if (p.equals(price)) {
                prices.remove(p);
            }
        }
    }
}

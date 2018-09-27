package com.mungurean.productApp.module;

import javax.persistence.*;

@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_id_seq")
    @SequenceGenerator(name = "price_id_seq", sequenceName = "price_id_seq")
    @Column(name = "id")
    private long id;

    @Column(name = "price")
    private double price;

    @Column(name = "time")
    private String date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Price() {
    }

    public Price(double price, String date) {
        this.price = price;
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price=" + price +
                ", date='" + date + '\'' +
                '}';
    }
}

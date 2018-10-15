package com.mungurean.productApp.module;


import javax.persistence.*;

@Entity
@Table(name = "description")
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "description_id_seq")
    @SequenceGenerator(name = "description_id_seq", sequenceName = "description_id_seq")
    @Column(name = "id")
    private long id;

    @Column(name = "text")
    private String flavorText;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;


    public Description() {

    }

    public Description(String flavorText) {
        this.flavorText = flavorText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFlavorText() {
        return flavorText;
    }

    void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Description{" +
                "id=" + id +
                ", flavorText='" + flavorText + '\'' +
                '}';
    }

}

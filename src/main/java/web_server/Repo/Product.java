package web_server.Repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)   
    private Long product_type_id;

    @Column(nullable = false, unique = false)
    private String product_type;

    @Column(nullable = false, unique = false)
    private float price;

    public Product() {
    }

    public Product(Long id, String name, float price) {
        this.product_type_id = id;
        this.product_type = name;
        this.price = price;
    }

}

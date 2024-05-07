package web_server.Repo;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;


@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = false)
    private String name;
    @Column(nullable = false, unique = false)
    private String hash;
    @Column(nullable = false, unique = true)
    private String email;

    public Client() {
    }

    public Client(Long id, String name, String hash, String email) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.email = email;
    }

    public Long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public String getEmail() {
        return email;
    }
}

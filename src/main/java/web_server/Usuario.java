package web_server;

import org.springframework.data.annotation.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String hash;
    private String email;

    public Usuario() {
    }

    public Usuario(Long id, String name, String hash, String email) {
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

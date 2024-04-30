package web_server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/sebas")
public class Controler {


    private final Repositorio repo;

    public Controler(Repositorio repo){
        this.repo = repo;
    }

    @PreAuthorize("permitAll()")  
    @GetMapping("/{id}")
    private ResponseEntity<String> userExistence(@PathVariable Long id){
        Optional<Usuario> user = repo.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok("Usuario encontrado");
        } else return ResponseEntity.notFound().build();
    }

    @PostMapping("/createuser/name={name}&password={password}&email={email}")
    private ResponseEntity<String> createUser(@PathVariable String name, @PathVariable String password, @PathVariable String email){
        
        long id = 1;
        while(repo.findById(id).isPresent()){
            id++;
        }

        String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());

        Usuario user = new Usuario(id, name, hashPass, email);
        repo.save(user);
        return ResponseEntity.ok("Usuario creado");
    }

}

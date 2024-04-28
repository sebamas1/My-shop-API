package web_server.prueba.Web_server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/sebas")
public class Controler {


    private final Repositorio repo;

    public Controler(Repositorio repo){
        this.repo = repo;
    }

    @GetMapping("/{id}")
    private ResponseEntity<String> userExistence(@PathVariable Long id){
        Optional<Usuario> user = repo.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok("Usuario encontrado");
        } else return ResponseEntity.notFound().build();
    }

}

package web_server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/sebas")
public class Controler {


    private final Repositorio repo;

    public Controler(Repositorio repo){
        this.repo = repo;
    }

    //@PreAuthorize("permitAll()")  
    @GetMapping("/{id}")
    private ResponseEntity<String> userExistence(@PathVariable Long id){
        Optional<Usuario> user = repo.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok("Usuario encontrado");
        } else return ResponseEntity.notFound().build();
    }

    @PostMapping("/createuser")
    private ResponseEntity<String> createUser(@RequestBody String request){
        
        //serialice el json en un objeto usuario
        ObjectMapper objectMapper = new ObjectMapper();
        Usuario tempUser = null;
        try {
            tempUser = objectMapper.readValue(request, Usuario.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        if(tempUser == null){
            return ResponseEntity.badRequest().build();
        }
        
        String hashPass = BCrypt.hashpw(tempUser.hash(), BCrypt.gensalt());

        Usuario user;

        try {
            user = repo.save(new Usuario((null), tempUser.name(), hashPass, tempUser.email()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Usuario creado: " + user.name() + "#" + user.id());
    }
}

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
            return ResponseEntity.ok("User found");
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
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Bad Json");
        }

        if(tempUser == null){
            return ResponseEntity.badRequest().build();
        }

        if(repo.findIdByEmail(tempUser.getEmail()) != null){
            return ResponseEntity.ok().body("Email already exists");
        }
        
        String hashPass = BCrypt.hashpw(tempUser.getHash(), BCrypt.gensalt());

        Usuario user;

        try {
            user = repo.save(new Usuario((null), tempUser.getName(), hashPass, tempUser.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok("Created user: " + user.getName() + "#" + user.getID());
    }

    @GetMapping("/delete/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<Usuario> user = repo.findById(id);
        if(user.isPresent()){
            repo.deleteById(id);
            return ResponseEntity.ok("User deleted: " + user.get().getName() + "#" + user.get().getID());
        } else return ResponseEntity.notFound().build();
    }


}

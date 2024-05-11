package web_server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import web_server.Repo.*;

import java.util.Optional;

// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/miShop")
public class Controler {


    private final ClientRepo clientRepo;
    private final TicketRepo ticketRepo;
    private final ProductRepo productRepo;
    private final ProductTicketRepo productTicketRepo;

    public Controler(ClientRepo clientRepo, TicketRepo ticketRepo, ProductRepo productRepo, ProductTicketRepo productTicketRepo){
        this.clientRepo = clientRepo;
        this.ticketRepo = ticketRepo;
        this.productRepo = productRepo;
        this.productTicketRepo = productTicketRepo;
    }

    //@PreAuthorize("permitAll()")  
    @GetMapping("/{id}")
    private ResponseEntity<String> userExistence(@PathVariable Long id){
        Optional<Client> user = clientRepo.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok("Client found");
        } else return ResponseEntity.notFound().build();
    }

    @PostMapping("/createclient")
    private ResponseEntity<String> createUser(@RequestBody String request){
        
        //serialice el json en un objeto usuario
        ObjectMapper objectMapper = new ObjectMapper();
        Client tempUser = null;
        try {
            tempUser = objectMapper.readValue(request, Client.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Bad Json");
        }

        if(tempUser == null){
            return ResponseEntity.badRequest().build();
        }

        if(clientRepo.findIdByEmail(tempUser.getEmail()) != null){
            return ResponseEntity.ok().body("Email already exists");
        }
        
        String hashPass = BCrypt.hashpw(tempUser.getHash(), BCrypt.gensalt());

        Client user;

        try {
            user = clientRepo.save(new Client((null), tempUser.getName(), hashPass, tempUser.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Client already exists");
        }
        return ResponseEntity.ok("Created client: " + user.getName() + "#" + user.getID());
    }

    @GetMapping("/delete/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable Long id){
        if(id == null){
            return ResponseEntity.badRequest().body("Bad request, id is null");
        }

        Optional<Client> user = clientRepo.findById(id);
        if(user.isPresent()){
            clientRepo.deleteById(id);
            return ResponseEntity.ok("Client deleted: " + user.get().getName() + "#" + user.get().getID());
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/getTicketList/{id}")
    private ResponseEntity<String> getTickets(@PathVariable Long id){
        List<Long> tickets = ticketRepo.findAllTicketsFromClient(id);
        ObjectMapper objectMapper = new ObjectMapper();
        if(tickets == null){
            return ResponseEntity.notFound().build();
        }
        try {
            String jsonResult = objectMapper.writeValueAsString(tickets);
            return ResponseEntity.ok(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error serializing tickets");
        }
    }       

    @GetMapping("/getProductListFromTicket/{id}")
    private ResponseEntity<String> getProducts(@PathVariable Long id){
        List<String> products = productTicketRepo.findAllProductsFromTicket(id);
        ObjectMapper objectMapper = new ObjectMapper();
        if(products == null){
            return ResponseEntity.notFound().build();
        }
        try {
            String jsonResult = objectMapper.writeValueAsString(products);
            return ResponseEntity.ok(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error serializing products");
        }

    }


}

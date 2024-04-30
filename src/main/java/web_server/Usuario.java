package web_server;

import org.springframework.data.annotation.Id;

record Usuario(@Id Long id, String name, String hash, String email) {
    
}

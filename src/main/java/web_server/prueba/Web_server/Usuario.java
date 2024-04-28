package web_server.prueba.Web_server;

import org.springframework.data.annotation.Id;

record Usuario(@Id Long id, String name, String email) {
    
}

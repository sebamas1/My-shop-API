package web_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebServerApplicationTests {

	@Test
	void userExistenceEndpointTest() {
	
		ResponseEntity<String> response = WebClient //crea un cliente web
			.create("http://localhost:8080/sebas/1") //crea un cliente web con la url del endpoint
			.get() //hace una peticion get
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block(); //bloquea la ejecucion hasta que se reciba la respuesta

			assert (response.getBody().equals("Usuario encontrado"));


	}

}

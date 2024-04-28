package web_server.prueba.Web_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class WebServerApplicationTests {

	@Test
	void userExistenceEndpointTest() {
		ResponseEntity<String> response = WebClient //crea un cliente web
			.create("/sebas/1") //crea un cliente web con la url del endpoint
			.get() //hace una peticion get
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block(); //bloquea la ejecucion hasta que se reciba la respuesta

			assert response.getBody().equals("Usuario encontrado");
	}

}

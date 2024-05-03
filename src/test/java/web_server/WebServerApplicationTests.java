package web_server;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebServerApplicationTests {

	@SuppressWarnings("null")
	@Test
	void userExistenceEndpointTest() {
	
		ResponseEntity<String> response = WebClient //crea un cliente web
			.create("http://localhost:8080/sebas/3") //crea un cliente web con la url del endpoint
			.get() //hace una peticion get
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block(); //bloquea la ejecucion hasta que se reciba la respuesta

			assertThat(response.getBody().equals("Usuario encontrado"));


	}

	@SuppressWarnings("null")	
	@Test
	void createUserEndpointTest() throws Exception{

		String juliaInfo = """
		{
			"name": "julia",
			"hash": "perritos",
			"email": "julia@gmail.com"
		}
				""";

		ResponseEntity<String> response = WebClient //crea un cliente web
			.create("http://localhost:8080/sebas/createuser") //crea un cliente web con la url del endpoint
			.post()
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.bodyValue(juliaInfo)
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block();

			assertThat(response.getBody()).isNotNull();
			assertThat(response.getBody().equals("Usuario creado: julia#2"));
	}
}

package web_server;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


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

			assertEquals("User found", response.getBody());


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

		ResponseEntity<String> response = WebClient 
			.create("http://localhost:8080/sebas/createuser")
			.post()
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.bodyValue(juliaInfo)
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block();

			assertThat(response.getBody()).isNotNull();
			assertEquals("Created user: julia#1", response.getBody());
	}

	@SuppressWarnings("null")
	@Test
	void deleteUserEndpointTest() {
	
		ResponseEntity<String> response = WebClient
			.create("http://localhost:8080/sebas/delete/2") 
			.get() //hace una peticion get
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block(); //bloquea la ejecucion hasta que se reciba la respuesta

			assertEquals("User deleted: Nehemias M#2", response.getBody());
	}

	@Test
	void shouldNotCreateUserWithExistingEmail() throws Exception{

		String randomInfo = """
		{
			"name": "Pedro",
			"hash": "buldog",
			"email": "sebamas1@hotmail.com"
		}
				""";

			ResponseEntity<String> response = WebClient
				.create("http://localhost:8080/sebas/createuser")
				.post()
				.headers(headers -> headers.setBasicAuth("seba", "abc123"))
				.bodyValue(randomInfo)
				.retrieve() //recupera la respuesta
				.toEntity(String.class) //la convierte a un objeto de tipo string
				.block();

			assertEquals("Email already exists", response.getBody());
	}
}

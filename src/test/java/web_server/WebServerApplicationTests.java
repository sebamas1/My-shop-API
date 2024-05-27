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
	void clientExistenceEndpointTest() {
	
		ResponseEntity<String> response = WebClient //crea un cliente web
			.create("http://localhost:3000/miShop/3") //crea un cliente web con la url del endpoint
			.get() //hace una peticion get
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block(); //bloquea la ejecucion hasta que se reciba la respuesta

			assertEquals("Client found", response.getBody());


	}

	@SuppressWarnings("null")	
	@Test
	void createClientEndpointTest() throws Exception{

		String juliaInfo = """
		{
			"name": "julia",
			"hash": "perritos",
			"email": "julia@gmail.com"
		}
				""";

		ResponseEntity<String> response = WebClient 
			.create("http://localhost:3000/miShop/createclient")
			.post()
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.bodyValue(juliaInfo)
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block();

			assertThat(response.getBody()).isNotNull();
			assertEquals("Created client: julia#1", response.getBody());
	}

	@SuppressWarnings("null")
	@Test
	void deleteClientEndpointTest() {
	
		ResponseEntity<String> response = WebClient
			.create("http://localhost:3000/miShop/delete/4") 
			.get() //hace una peticion get
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.retrieve() //recupera la respuesta
			.toEntity(String.class) //la convierte a un objeto de tipo string
			.block(); //bloquea la ejecucion hasta que se reciba la respuesta

			assertEquals("Client deleted: Pedro#4", response.getBody());
	}

	@Test
	void shouldNotCreateClientWithExistingEmail() throws Exception{

		String randomInfo = """
		{
			"name": "Pedro",
			"hash": "buldog",
			"email": "sebamas1@hotmail.com"
		}
				""";

			ResponseEntity<String> response = WebClient
				.create("http://localhost:3000/miShop/createclient")
				.post()
				.headers(headers -> headers.setBasicAuth("seba", "abc123"))
				.bodyValue(randomInfo)
				.retrieve() //recupera la respuesta
				.toEntity(String.class) //la convierte a un objeto de tipo string
				.block();

			assertEquals("Email already exists", response.getBody());
	}

	@Test
	void shouldReturnListOfTicketsFromClient() {
	
		ResponseEntity<String> response = WebClient
			.create("http://localhost:3000/miShop/getTicketList/2")
			.get()
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.retrieve()
			.toEntity(String.class)
			.block();

			assertEquals("[1,3]", response.getBody());
		}

	@Test
	void shouldReturnListOfProducts() {
	
		ResponseEntity<String> response = WebClient
			.create("http://localhost:3000/miShop/getProductListFromTicket/1")
			.get()
			.headers(headers -> headers.setBasicAuth("seba", "abc123"))
			.retrieve()
			.toEntity(String.class)
			.block();

			assertEquals("[\"Coca Cola\",\"Papas Fritas\",\"Hamburguesa\"]", response.getBody());
		}
}

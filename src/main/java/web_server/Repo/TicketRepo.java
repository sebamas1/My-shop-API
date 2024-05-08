package web_server.Repo;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TicketRepo extends CrudRepository<Ticket, Long>{

    @Query("SELECT t.id FROM Ticket t JOIN Client c ON c.id = t.client_id WHERE c.id = :id")
    public List<Long> findAllTicketsFromClient(@Param("id") Long id);
    
}

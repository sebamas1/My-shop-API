package web_server.Repo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductTicketRepo extends CrudRepository<ProductTicket, Long>{
    
    @Query("SELECT Product.product_type \n" + //
    "FROM Product INNER JOIN ProductTicket \n" + //
    "ON ProductTicket.product_type_id = Product.product_type_id \n" + //
    "WHERE ticket_id = :ticket_id")
        public List<String> findAllProductsFromTicket(@Param("ticket_id") Long ticket_id);
}

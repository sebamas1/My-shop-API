package web_server.Repo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClientRepo extends CrudRepository<Client, Long>{

    @Query("SELECT ID FROM Client WHERE email = :email")
    public Long findIdByEmail(@Param("email") String email);
    // podria hacerse inyeccion de SQl en esta query????

    
}
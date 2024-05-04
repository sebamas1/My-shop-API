package web_server;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface Repositorio extends CrudRepository<Usuario, Long>{

    @Query("SELECT ID FROM Usuario WHERE email = :email")
    public Long findIdByEmail(@Param("email") String email);
    // podria hacerse inyeccion de SQl en esta query????

    
}
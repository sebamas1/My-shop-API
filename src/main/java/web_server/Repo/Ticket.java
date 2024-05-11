package web_server.Repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.sql.Time;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    @ManyToOne(targetEntity = Client.class)
    @Column(nullable = false, unique = false)
    private Long client_id;

    @Column(nullable = false, unique = false)
    private Date ticket_date;
    
    @Column(nullable = false, unique = false)
    private Time ticket_time;

    @Column(nullable = false, unique = false)
    private float ticket_price;

    public Ticket() {
    }

    public Ticket(Long id, Long client_id, Date ticket_date, Time ticket_time, float ticket_price) {
        this.id = id;
        this.client_id = client_id;
        this.ticket_date = ticket_date;
        this.ticket_time = ticket_time;
        this.ticket_price = ticket_price;
    }

    public Long getID() {
        return id;
    }
}

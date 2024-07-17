package eu.sibs.ordersibs.model.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity(name = "TB_ORDER")
@Getter
@Setter
@ToString
public class Order {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private Timestamp creationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId",referencedColumnName = "id")
	private Item itemId;
	
	private Long quantity;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",referencedColumnName = "id")
	private User userId;


	private String status;
}

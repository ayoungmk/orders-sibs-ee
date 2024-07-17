package eu.sibs.ordersibs.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementDTO {

	private Timestamp creationDate;
	private String itemName;
	private Long quantity;
}

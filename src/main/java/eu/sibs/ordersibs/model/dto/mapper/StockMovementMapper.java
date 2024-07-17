package eu.sibs.ordersibs.model.dto.mapper;


import eu.sibs.ordersibs.model.dto.StockMovementDTO;
import eu.sibs.ordersibs.model.entity.StockMovement;
import org.modelmapper.ModelMapper;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class StockMovementMapper {

	private ModelMapper mapper = new ModelMapper();

	public StockMovementMapper (ModelMapper mapper) {
		this.mapper = mapper;
	}
	
	public StockMovement toEntity(StockMovementDTO dto) {
		StockMovement entity = mapper.map(dto, StockMovement.class);
		return entity;
	}
	
	public StockMovementDTO toDTO(StockMovement entity) {
		StockMovementDTO dto = mapper.map(entity, StockMovementDTO.class);
		return dto;
	}
	
	public List<StockMovementDTO> toDTO(List<StockMovement> stockMovements){
		return stockMovements.stream()
				.map(stockMovement -> toDTO(stockMovement))
				.collect(Collectors.toList());
	}
	
}

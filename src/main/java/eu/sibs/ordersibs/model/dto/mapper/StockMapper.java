package eu.sibs.ordersibs.model.dto.mapper;

import eu.sibs.ordersibs.model.dto.StockDTO;
import eu.sibs.ordersibs.model.entity.Stock;
import org.modelmapper.ModelMapper;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class StockMapper {

	private ModelMapper mapper = new ModelMapper();

	public StockMapper (ModelMapper mapper) {
		this.mapper = mapper;
	}
	
	public Stock toEntity(StockDTO dto) {
		Stock entity = mapper.map(dto, Stock.class);
		return entity;
	}
	
	public StockDTO toDTO(Stock entity) {
		StockDTO dto = mapper.map(entity, StockDTO.class);
		return dto;
	}
	
	public List<StockDTO> toDTO(List<Stock> stocks){
		return stocks.stream()
				.map(stock -> toDTO(stock))
				.collect(Collectors.toList());
	}
	
}

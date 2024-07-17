package eu.sibs.ordersibs.service;



import eu.sibs.ordersibs.exceptions.StockNotFoundException;
import eu.sibs.ordersibs.model.dto.StockDTO;
import eu.sibs.ordersibs.model.entity.Item;

import java.util.List;

public interface StockService {
	
	public List<StockDTO> findAll();
	public StockDTO findById(Long id) throws StockNotFoundException;
	public StockDTO save(StockDTO stockDto);
	public StockDTO updateStock(Long id, StockDTO stockDtoDetails) throws StockNotFoundException;
	public void deleteById(Long id) throws StockNotFoundException;
	public Long findIdbyItens(Item item);
}

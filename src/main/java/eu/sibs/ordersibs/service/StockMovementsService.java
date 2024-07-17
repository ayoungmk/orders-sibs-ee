package eu.sibs.ordersibs.service;

import eu.sibs.ordersibs.exceptions.StockMovementsNotFoundException;
import eu.sibs.ordersibs.model.dto.StockMovementDTO;
import java.util.List;

public interface StockMovementsService {
	
	public List<StockMovementDTO> findAll();
	public StockMovementDTO findById(Long id) throws StockMovementsNotFoundException;
	public StockMovementDTO save(StockMovementDTO stockMovementDto);
	public StockMovementDTO updateStockMovements(Long id, StockMovementDTO stockMovementDtoDetails) throws StockMovementsNotFoundException;
	public void deleteById(Long id) throws StockMovementsNotFoundException;
}

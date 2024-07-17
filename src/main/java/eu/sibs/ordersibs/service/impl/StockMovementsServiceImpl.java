package eu.sibs.ordersibs.service.impl;



import eu.sibs.ordersibs.exceptions.ItensNotFoundException;
import eu.sibs.ordersibs.exceptions.StockMovementsNotFoundException;
import eu.sibs.ordersibs.model.dto.StockDTO;
import eu.sibs.ordersibs.model.dto.StockMovementDTO;
import eu.sibs.ordersibs.model.dto.mapper.StockMovementMapper;
import eu.sibs.ordersibs.model.entity.Order;
import eu.sibs.ordersibs.model.entity.Stock;
import eu.sibs.ordersibs.model.entity.StockMovement;
import eu.sibs.ordersibs.model.enums.StatusOrder;
import eu.sibs.ordersibs.repository.jpa.ItemRepository;
import eu.sibs.ordersibs.repository.jpa.OrdersRepository;
import eu.sibs.ordersibs.repository.jpa.StockMovementsRepository;
import eu.sibs.ordersibs.repository.jpa.StockRepository;
import eu.sibs.ordersibs.service.StockMovementsService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Stateless
public class StockMovementsServiceImpl implements StockMovementsService {

	@EJB
	private StockMovementsRepository stockMovementsRepository;
	@EJB 
	private StockRepository stockRepository;
	@EJB 
	private ItemRepository itemRepository;
	@EJB 
	private OrdersRepository orderRepository;
	@EJB 
	private StockMovementMapper stockMovementsMapper;
	@EJB 
	private OrdersServiceImpl ordersServiceImpl;
	@EJB 
	private StockServiceImpl stockServiceImpl;

    public StockMovementsServiceImpl () {
    }

	 private final static Logger logger = Logger.getLogger(StockMovementsServiceImpl.class);
	
	public List<StockMovementDTO> findAll(){
		return stockMovementsMapper.toDTO(stockMovementsRepository.findAll());
	}

	public StockMovementDTO findById(Long id) throws StockMovementsNotFoundException {
		Optional<StockMovement> stockMovements = stockMovementsRepository.findById(id);
		if(stockMovements.isPresent()) {
			StockMovementDTO stockMovementDto = stockMovementsMapper.toDTO(stockMovements.get());
			return stockMovementDto;
		}else {
			logger.error("Stock Movement with id " + id + " not found!");
			throw new ItensNotFoundException("Stock Movement with id " + id + " not found!");
		}
	}

	@Override
	public StockMovementDTO save(StockMovementDTO stockMovementDto) {
		StockMovement stockMovement = new StockMovement();
		stockMovement.setItem(itemRepository.findByName(stockMovementDto.getItemName()));
		stockMovement.setCreationDate(new Timestamp(System.currentTimeMillis()));
		stockMovement.setQuantity(stockMovementDto.getQuantity());
		stockMovementsRepository.save(stockMovement);
//		logger.info("Stock Movement created: " + stockMovement.toString());
		Stock stock = stockRepository.findByItem(stockMovement.getItem());
		stockServiceImpl.updateStock(stock.getId(), StockDTO.builder().itemName(stock.getItem().getName()).quantity(stock.getQuantity()+stockMovementDto.getQuantity()).build());
//		logger.info("Stock before trying to complete orders: " + stock.toString());
		
		List<Order> incompleteOrders = ordersServiceImpl.getIncompleteOrders(stockMovement.getItem());
		for (Order order : incompleteOrders) {
			ordersServiceImpl.assignStatusOrder(order,stock, order.getQuantity(),stock.getItem(), order.getUserId());
		}
		return stockMovementDto;
	}


	public void deleteById(Long id) throws StockMovementsNotFoundException {
		Optional<StockMovement> stockMovementsOpt = stockMovementsRepository.findById(id);
		if(stockMovementsOpt.isPresent()) {
			StockMovement stockMovement = stockMovementsOpt.get();
			stockMovementsRepository.delete(stockMovement);
		}else {
			logger.error("Stock Movement with id " + id + " not found!");
			throw new ItensNotFoundException("Stock Movement with id " + id + " not found!");
		}
	}

	public StockMovementDTO updateStockMovements(Long id, StockMovementDTO stockMovementsDtoDetails) throws StockMovementsNotFoundException {
		Optional<StockMovement> stockMovementsOpt = stockMovementsRepository.findById(id);
		if(stockMovementsOpt.isPresent()) {
			StockMovement stockMovement = stockMovementsOpt.get();
//			stockMovement.setItem(stockMovementsDtoDetails.getItem());
			stockMovement.setQuantity(stockMovementsDtoDetails.getQuantity());
			stockMovementsRepository.save(stockMovement);
			StockMovementDTO stockMovementDto = stockMovementsMapper.toDTO(stockMovement);
			return stockMovementDto;
		}else {
			logger.error("Stock Movement with id " + id + " not found!");
			throw new ItensNotFoundException("Stock Movement with id " + id + " not found!");
		}
	}

	public List<StockMovementDTO> getStockMovementsOfOrder(Long idOrder) {
		List<Order> order = orderRepository.findAllByStatus(StatusOrder.COMPLETE.toString());
		
		return null;
	}
}

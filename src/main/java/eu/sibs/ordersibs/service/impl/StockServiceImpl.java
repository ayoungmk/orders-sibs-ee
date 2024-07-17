package eu.sibs.ordersibs.service.impl;


import eu.sibs.ordersibs.exceptions.ItensNotFoundException;
import eu.sibs.ordersibs.exceptions.StockNotFoundException;
import eu.sibs.ordersibs.model.dto.StockDTO;
import eu.sibs.ordersibs.model.dto.mapper.StockMapper;
import eu.sibs.ordersibs.model.entity.Item;
import eu.sibs.ordersibs.model.entity.Order;
import eu.sibs.ordersibs.model.entity.Stock;
import eu.sibs.ordersibs.repository.jpa.StockRepository;
import eu.sibs.ordersibs.service.StockService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class StockServiceImpl implements StockService {
	@EJB
	private StockRepository stockRepository;
	@EJB 
	private StockMapper stockMapper;
	@EJB 
	private OrdersServiceImpl ordersServiceImpl;
	

    public StockServiceImpl() {
    }

	 private final static Logger logger = Logger.getLogger(StockServiceImpl.class);
	
	public List<StockDTO> findAll(){
		return stockMapper.toDTO(stockRepository.findAll());
	}
	
	public StockDTO findById(Long id) throws StockNotFoundException {
		Optional<Stock> stock = stockRepository.findById(id);
		if(stock.isPresent()) {
			StockDTO stockDto = stockMapper.toDTO(stock.get());
			return stockDto;
		}else {
			logger.error("Stock with id " + id + " not found!");
			throw new ItensNotFoundException("Stock with id " + id + " not found!");
		}
	}
	
	public StockDTO save(StockDTO stockDto) {
		Stock stock = stockMapper.toEntity(stockDto);
		stockRepository.save(stock);
		return stockDto;
	}
	
	public void deleteById(Long id) throws StockNotFoundException {
		Optional<Stock> stockOpt = stockRepository.findById(id);
		if(stockOpt.isPresent()) {
			Stock stock = stockOpt.get();
			stockRepository.delete(stock);
		}else {
			logger.error("Stock with id " + id + " not found!");
			throw new ItensNotFoundException("Stock with id " + id + " not found!");
		}
	}

	@Override
	public Long findIdbyItens(Item item) {
		return null;
	}

	public StockDTO updateStock(Long id, StockDTO stockDtoDetails) throws StockNotFoundException {
		Optional<Stock> stockOpt = stockRepository.findById(id);
		if(stockOpt.isPresent()) {
			Stock stock = stockOpt.get();
			stock.setQuantity(stockDtoDetails.getQuantity());
			stockRepository.save(stock);
			
			List<Order> incompleteOrders = ordersServiceImpl.getIncompleteOrders(stock.getItem());
			for (Order order : incompleteOrders) {
				ordersServiceImpl.assignStatusOrder(order,stock, order.getQuantity(),stock.getItem(), order.getUserId());
			}
			
			StockDTO stockDto = stockMapper.toDTO(stock);
			return stockDto;
		}else {
			logger.error("Stock with id " + id + " not found!");
			throw new ItensNotFoundException("Stock with id " + id + " not found!");
		}
	}

//	@Override
//	public Long findIdbyItens(Item item) {
//		return stockRepository.findIdbyIten(item);
//	}
//
}

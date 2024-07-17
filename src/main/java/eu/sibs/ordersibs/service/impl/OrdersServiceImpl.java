package eu.sibs.ordersibs.service.impl;


import eu.sibs.ordersibs.exceptions.ItensNotFoundException;
import eu.sibs.ordersibs.exceptions.OrdersNotFoundException;
import eu.sibs.ordersibs.model.dto.OrderDTO;
import eu.sibs.ordersibs.model.dto.mapper.OrderMapper;
import eu.sibs.ordersibs.model.entity.Item;
import eu.sibs.ordersibs.model.entity.Order;
import eu.sibs.ordersibs.model.entity.Stock;
import eu.sibs.ordersibs.model.entity.StockMovement;
import eu.sibs.ordersibs.model.entity.User;
import eu.sibs.ordersibs.model.enums.StatusOrder;
import eu.sibs.ordersibs.repository.jpa.ItemRepository;
import eu.sibs.ordersibs.repository.jpa.OrdersRepository;
import eu.sibs.ordersibs.repository.jpa.StockMovementsRepository;
import eu.sibs.ordersibs.repository.jpa.StockRepository;
import eu.sibs.ordersibs.repository.jpa.UsersRepository;
import eu.sibs.ordersibs.service.EmailService;
import eu.sibs.ordersibs.service.OrdersService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Stateless
public class OrdersServiceImpl implements OrdersService {

	@EJB
	private OrdersRepository ordersRepository;
	@EJB 
	private UsersRepository usersRepository;
	@EJB 
	private StockRepository stockRepository;
	@EJB 
	private ItemRepository itemRepository;
	@EJB 
	private OrderMapper ordersMapper;
	@EJB 
	private StockMovementsRepository stockMovementsServiceImpl;
	@EJB 
	private EmailService emailServiceImpl;

    public OrdersServiceImpl(){

    }
	private final static Logger logger = Logger.getLogger(OrdersServiceImpl.class);

	public List<OrderDTO> findAll(){
		return ordersMapper.toDTO( ordersRepository.findAll());
	}

	public OrderDTO findById(Long id) throws OrdersNotFoundException {
		Optional<Order> order = ordersRepository.findById(id);
		if(order.isPresent()) {
			return ordersMapper.toDTO(order.get());
		}else {
			logger.error("Order with id " + id + " not found!");
			throw new ItensNotFoundException("Order with id " + id + " not found!");
		}
	}

	public void deleteById(Long id) throws OrdersNotFoundException {
		ordersRepository.deleteById(id);
	}

	public OrderDTO updateOrders(Long id, OrderDTO orderDtoDetails) throws OrdersNotFoundException{
		Optional<Order> orderOpt = ordersRepository.findById(id);
		if(orderOpt.isPresent()) {
			Order order = orderOpt.get();
			order.getItemId().setName(orderDtoDetails.getItemName());
			order.setQuantity(orderDtoDetails.getQuantity());
			ordersRepository.save(order);
			return orderDtoDetails;
		}else {
			logger.error("Order with id " + id + " not found!");
			throw new ItensNotFoundException("Order with id " + id + " not found!");
		}
	}

	public OrderDTO createOrder(OrderDTO orderDto) {
		Item item = itemRepository.findByName(orderDto.getItemName());
		Stock stock = stockRepository.findByItem(itemRepository.findByName(orderDto.getItemName()));
		User user = usersRepository.findByName(orderDto.getUserName());
		Order order = new Order();
		order.setCreationDate(new Timestamp(System.currentTimeMillis()));
		order.setItemId(item);
		order.setUserId(user);
		order.setQuantity(orderDto.getQuantity());
		this.assignStatusOrder(order,stock,orderDto.getQuantity(),item, user);
		ordersRepository.save(order);
		orderDto.setStatus(order.getStatus());
		return orderDto;
	}

	public void assignStatusOrder(Order order, Stock stock,Long quantity,Item item, User user) {
		if (quantity <= stock.getQuantity()) {
			stockMovementsServiceImpl.save(StockMovement.builder().creationDate(new Timestamp(System.currentTimeMillis())).item(item).quantity(-order.getQuantity()).build());
			stock.setQuantity(stock.getQuantity() - order.getQuantity());
			stockRepository.save(stock);
			logger.info("Stock after completing order: " + stock.toString());
			this.emailServiceImpl.sendSimpleMessage(user.getEmail(), "Order Complete", "Your order was successfully completed!");
			order.setStatus(StatusOrder.COMPLETE.toString());
			logger.info("Order Completed: " + order.toString());
		} else {
			order.setStatus(StatusOrder.INCOMPLETE.toString());
		}
	}
	
	public List<Order> getIncompleteOrders(Item item) {
		List<Order> incompleteOrders = ordersRepository.findByStatusAndItemIdOrderByCreationDateAsc(StatusOrder.INCOMPLETE.toString(), item);
		return incompleteOrders;
	}
}

package eu.sibs.ordersibs.service;

import eu.sibs.ordersibs.exceptions.OrdersNotFoundException;
import eu.sibs.ordersibs.model.dto.OrderDTO;
import java.util.List;

public interface OrdersService {
	
	public List<OrderDTO> findAll();
	public OrderDTO findById(Long id) throws OrdersNotFoundException;

	public OrderDTO createOrder(OrderDTO orderDto);
	public OrderDTO updateOrders(Long id, OrderDTO orderDtoDetails) throws OrdersNotFoundException;
	public void deleteById(Long id) throws OrdersNotFoundException;
}

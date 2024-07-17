package eu.sibs.ordersibs.model.dto.mapper;


import eu.sibs.ordersibs.model.dto.OrderDTO;
import eu.sibs.ordersibs.model.entity.Order;
import org.modelmapper.ModelMapper;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;
@Stateless
public class OrderMapper {

	private ModelMapper mapper = new ModelMapper();
	

	public OrderMapper (ModelMapper mapper) {
		this.mapper = mapper;
	}
	
	public Order toEntity(OrderDTO dto) {
		Order entity = mapper.map(dto, Order.class);
		return entity;
	}
	
	public OrderDTO toDTO(Order entity) {
		OrderDTO dto = mapper.map(entity, OrderDTO.class);
		return dto;
	}
	
	public List<OrderDTO> toDTO(List<Order> orders){
		return orders.stream()
				.map(order -> toDTO(order))
				.collect(Collectors.toList());
	}
	
}

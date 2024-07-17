package eu.sibs.ordersibs.model.dto.mapper;

import eu.sibs.ordersibs.model.dto.ItemDTO;
import eu.sibs.ordersibs.model.entity.Item;
import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ItemMapper {
	
	private ModelMapper mapper = new ModelMapper();
	

	public ItemMapper (ModelMapper mapper) {
		this.mapper = mapper;
	}
	
	public Item toEntity(ItemDTO dto) {
		Item entity = mapper.map(dto, Item.class);
		return entity;
	}
	
	public ItemDTO toDTO(Item entity) {
		ItemDTO dto = mapper.map(entity, ItemDTO.class);
		return dto;
	}
	
	public List<ItemDTO> toDTO(List<Item> itens){
		return itens.stream()
				.map(item -> toDTO(item))
				.collect(Collectors.toList());
	}
	
}

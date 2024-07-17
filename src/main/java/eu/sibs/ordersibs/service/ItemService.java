package eu.sibs.ordersibs.service;

import eu.sibs.ordersibs.exceptions.ItensNotFoundException;
import eu.sibs.ordersibs.model.dto.ItemDTO;
import java.util.List;

public interface ItemService {

	public List<ItemDTO> findAll();
	public ItemDTO findById(Long id) throws ItensNotFoundException;
	public ItemDTO save(ItemDTO itemDto);
	public ItemDTO updateItens(Long id, ItemDTO itemDtoDetails) throws ItensNotFoundException;
	public void deleteById(Long id) throws ItensNotFoundException;
}

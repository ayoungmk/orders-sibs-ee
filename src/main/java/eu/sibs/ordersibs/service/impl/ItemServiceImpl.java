package eu.sibs.ordersibs.service.impl;


import eu.sibs.ordersibs.exceptions.ItensNotFoundException;
import eu.sibs.ordersibs.model.dto.ItemDTO;
import eu.sibs.ordersibs.model.dto.mapper.ItemMapper;
import eu.sibs.ordersibs.model.entity.Item;
import eu.sibs.ordersibs.repository.jpa.ItemRepository;
import eu.sibs.ordersibs.service.ItemService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class ItemServiceImpl implements ItemService {
	@EJB
	private ItemRepository itemRepository;
	@EJB
	private ItemMapper itensMapper;

	 private final static Logger LOGGER = Logger.getLogger(ItemServiceImpl.class);

    public ItemServiceImpl() {

    }
	
	public List<ItemDTO> findAll(){
		return itensMapper.toDTO(itemRepository.findAll());
	}
	
	public ItemDTO findById(Long id) throws ItensNotFoundException {
		Optional<Item> item = itemRepository.findById(id);
		if(item.isPresent()) {
			return itensMapper.toDTO(item.get());
		}else {
			LOGGER.error("Item with id " + id + " not found!");
			throw new ItensNotFoundException("Item with id " + id + " not found!");
		}
	}

	public ItemDTO save(ItemDTO itemDto) {
		itemRepository.save(Item.builder().name(itemDto.getName()).build());
		return itemDto;
	}

	public void deleteById(Long id) throws ItensNotFoundException{
		itemRepository.deleteById(id);
	}
	public ItemDTO updateItens(Long id, ItemDTO itemDtoDetails) throws ItensNotFoundException{
		Optional<Item> itemOpt = itemRepository.findById(id);
		if(itemOpt.isPresent()) {
			itemOpt.get().setName(itemDtoDetails.getName());
			itemRepository.save(itemOpt.get());
			return itemDtoDetails;
		}else {
			LOGGER.error("Item with id " + id + " not found!");
			throw new ItensNotFoundException("Item with id " + id + " not found!");
		}
	}
}
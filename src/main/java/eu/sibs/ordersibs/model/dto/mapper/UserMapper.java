package eu.sibs.ordersibs.model.dto.mapper;

import eu.sibs.ordersibs.model.dto.UserDTO;
import eu.sibs.ordersibs.model.entity.User;
import org.modelmapper.ModelMapper;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserMapper {

	private ModelMapper mapper = new ModelMapper();
	

	public UserMapper (ModelMapper mapper) {
		this.mapper = mapper;
	}
	
	public User toEntity(UserDTO dto) {
		User entity = mapper.map(dto, User.class);
		return entity;
	}
	
	public UserDTO toDTO(User entity) {
		UserDTO dto = mapper.map(entity, UserDTO.class);
		return dto;
	}
	
	public List<UserDTO> toDTO(List<User> users){
		return users.stream()
				.map(user -> toDTO(user))
				.collect(Collectors.toList());
	}
	
}

package eu.sibs.ordersibs.service;

import eu.sibs.ordersibs.exceptions.UsersNotFoundException;
import eu.sibs.ordersibs.model.dto.UserDTO;
import java.util.List;
public interface UsersService {
	
	public List<UserDTO> findAll();
	public UserDTO findById(Long id) throws UsersNotFoundException;
	public UserDTO save(UserDTO usersDto);
	public UserDTO updateUsers(Long id, UserDTO usersDtoDetails) throws UsersNotFoundException;
	public void deleteById(Long id) throws UsersNotFoundException;
}

package eu.sibs.ordersibs.service.impl;


import eu.sibs.ordersibs.exceptions.ItensNotFoundException;
import eu.sibs.ordersibs.exceptions.UsersNotFoundException;
import eu.sibs.ordersibs.model.dto.UserDTO;
import eu.sibs.ordersibs.model.dto.mapper.UserMapper;
import eu.sibs.ordersibs.model.entity.User;
import eu.sibs.ordersibs.repository.jpa.UsersRepository;
import eu.sibs.ordersibs.service.UsersService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class UsersServiceImpl implements UsersService {

	@EJB
	private UsersRepository usersRepository;
	@EJB 
	private UserMapper usersMapper;
	

    public UsersServiceImpl() {
    }

	 private final static Logger logger = Logger.getLogger(UsersServiceImpl.class);
	
	public List<UserDTO> findAll(){
		return usersMapper.toDTO(usersRepository.findAll());
	}
	
	public UserDTO findById(Long id) throws UsersNotFoundException {
		Optional<User> user = usersRepository.findById(id);
		if(user.isPresent()) {
			UserDTO userDto = usersMapper.toDTO(user.get());
			return userDto;
		}else {
			logger.error("User with id " + id + " not found!");
			throw new ItensNotFoundException("User with id " + id + " not found!");
		}
	}
	
	public UserDTO save(UserDTO userDto) {
		User user = usersMapper.toEntity(userDto);
		usersRepository.save(user);
		return userDto;
	}
	
	public void deleteById(Long id) throws UsersNotFoundException{
		Optional<User> userOpt = usersRepository.findById(id);
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			usersRepository.delete(user);
		}else {
			logger.error("User with id " + id + " not found!");
			throw new ItensNotFoundException("User with id " + id + " not found!");
		}
	}

	public UserDTO updateUsers(Long id, UserDTO userDtoDetails) throws UsersNotFoundException{
		Optional<User> userOpt = usersRepository.findById(id);
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			user.setName(userDtoDetails.getName());
			user.setEmail(userDtoDetails.getEmail());
			usersRepository.save(user);
			UserDTO userDto = usersMapper.toDTO(user);
			return userDto;
		}else {
			logger.error("User with id " + id + " not found!");
			throw new ItensNotFoundException("User with id " + id + " not found!");
		}
	}
}
package com.dvsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dvsuperior.dscatalog.dto.RoleDTO;
import com.dvsuperior.dscatalog.dto.UserDTO;
import com.dvsuperior.dscatalog.dto.UserInsertDTO;
import com.dvsuperior.dscatalog.dto.UserUpdateDTO;
import com.dvsuperior.dscatalog.entities.Role;
import com.dvsuperior.dscatalog.entities.User;
import com.dvsuperior.dscatalog.repositories.RoleRepository;
import com.dvsuperior.dscatalog.repositories.UserRepository;
import com.dvsuperior.dscatalog.services.exceptions.DataBaseException;
import com.dvsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable){
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDTO(x));		
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id){
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Entidade não encontrada!"));
		return new UserDTO(entity);		
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToentity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}
	
	

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			User entity = repository.getOne(id);
			copyDtoToentity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
			
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
		
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
		
	}
	
	private void copyDtoToentity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		
		entity.getRoles().clear();
		
		for(RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}
		
		
	}

}

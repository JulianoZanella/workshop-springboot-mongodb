package com.julianozanella.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.julianozanella.workshopmongo.domain.User;
import com.julianozanella.workshopmongo.dto.UserDTO;
import com.julianozanella.workshopmongo.repository.UserRepository;
import com.julianozanella.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User insert(User obj) {
		obj.setId(null);
		return repo.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(User newObj, User oldObj) {
		newObj.setName(oldObj.getName() == null ? newObj.getName() : oldObj.getName());
		newObj.setEmail(oldObj.getEmail() == null ? newObj.getEmail() : oldObj.getEmail());
	}

	public User fromDto(UserDTO obj) {
		return new User(obj.getId(), obj.getName(), obj.getEmail());
	}
}

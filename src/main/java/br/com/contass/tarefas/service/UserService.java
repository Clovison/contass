package br.com.contass.tarefas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contass.tarefas.modelos.User;
import br.com.contass.tarefas.repository.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository repository; 
	
	
	public Iterable<User> all(){
		return  repository.findAll();
	}
	
	public void add(User perfil) {
		repository.save(perfil);
	}
	
	public void remove(User perfil) {
		
		repository.delete(perfil);
	}

	public User find(Long id) {
		
		return repository.findOne(id);
	}
	
    public User search(String email) {
		
		return repository.search(email);
	}
	

}

package br.com.contass.tarefas.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.contass.tarefas.modelos.User;

public interface UserRepository extends CrudRepository<User, Serializable>{
	
	@Query("select u from User u where u.email = :pEmail")
	public User search( @Param("pEmail") String email);
}

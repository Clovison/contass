package br.com.contass.tarefas.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.contass.tarefas.modelos.Task;


public interface TaskRepository extends CrudRepository<Task, Serializable>{
	@Query("select max(a.id) from Task a")
	public int lastId();
}

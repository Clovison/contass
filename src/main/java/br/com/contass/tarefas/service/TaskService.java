package br.com.contass.tarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contass.tarefas.modelos.Task;
import br.com.contass.tarefas.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository repository;

	public Iterable<Task> all() {
		return repository.findAll();
	}

	public void add(Task activity) {
		repository.save(activity);
	}

	public void remove(Task activity) {
		repository.delete(activity);
	}

	public Task find(Long id) {
		return repository.findOne(id);
	}


	
	public int lastId() {
		return repository.lastId();
	}
}

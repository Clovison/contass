package br.com.contass.tarefas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contass.tarefas.modelos.Task;
import br.com.contass.tarefas.modelos.User;
import br.com.contass.tarefas.service.TaskService;
import br.com.contass.tarefas.service.UserService;

@Controller
@RequestMapping("contass/task")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public ModelAndView list(ModelMap model) {
		model.addAttribute("taskList", taskService.all());
		
		return new ModelAndView("task/list", model);
	}

	@GetMapping("/form")
	public String form(@ModelAttribute("task") Task task, ModelMap model) {
		model.addAttribute("users", userService.all());
		model.addAttribute("taskList", taskService.all());
		return "task/add";
	}

	@PostMapping("/save")
	public String saveTask(@Valid @ModelAttribute("task") Task task,  ModelMap model,BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			return  "redirect:/contass/task/add";
		}
		taskService.add(task);
		model.addAttribute("users", userService.all());
		model.addAttribute("taskList", taskService.all());
		 attr.addFlashAttribute("mensagem", "["+ task.getDescription()+ "] adicionada com sucesso!");
		return "redirect:/contass/task/list";
	}

	@GetMapping("/{taskId}/remove")
	public String remover(@PathVariable("taskId") long taskId, @ModelAttribute("task") Task task, ModelMap model,
			RedirectAttributes attr ) {
		Task t = taskService.find(taskId);
		taskService.remove(t);
		model.addAttribute("users", userService.all());
		model.addAttribute("taskList", taskService.all());
		 attr.addFlashAttribute("mensagem", "["+ t.getDescription()+ "] removida com sucesso!");
		 return "redirect:/contass/task/list";
	}

	@GetMapping("/{taskId}/edit")
	public ModelAndView preEdit(@PathVariable("taskId") long taskId, ModelMap model, RedirectAttributes attr) {
		Task task = taskService.find(taskId);
		List<User> all = (List<User>) userService.all();
		//all.remove(task.getUser());
		model.addAttribute("users", all);
		model.addAttribute("task", task);
		attr.addFlashAttribute("mensagem", task.getDescription() + " atualizado com sucesso.");
		return new ModelAndView("task/add", model);
	}
	

}

package br.com.contass.tarefas.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contass.tarefas.modelos.User;
import br.com.contass.tarefas.service.UserService;



@Controller
@RequestMapping("contass/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/form")
    public String preSalvar(@ModelAttribute("user") User user, ModelMap model) {
    	System.err.println("Chamando...");
        return "user/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes attr, ModelMap model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        
        User search = userService.search(user.getEmail());
        
        if(search!=null) {
        	model.addAttribute("messageUsed", "Não salvo, Email informado está em uso.");
        	return "user/add";
        }
        	
        userService.add(user);
        
        attr.addFlashAttribute("mensagem", "Cadastro realizado com sucesso.");
        return "redirect:/contass/user/form";
    }

    @PutMapping("/save")
    public String atualizar(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.add(user);
        attr.addFlashAttribute("mensagem", "Registro realizado com sucesso.");
        return "redirect:/user/user/register";
    }

    @GetMapping("/{collaboratorId}/remove")
    public String remover(@PathVariable("collaboratorId") long collaboratorId,  RedirectAttributes attr) {
    	User perfil = userService.find(collaboratorId);
    	userService.remove(perfil);
        attr.addFlashAttribute("mensagem", "Perfil excluída com sucesso.");
        return "redirect:/contass/user/register";
    }
    
    @GetMapping("/{userId}/edit")
    public  ModelAndView preEdit(@PathVariable("user") long userId,  ModelMap model, RedirectAttributes attr) {
    	User user = userService.find(userId);
    	
    	model.addAttribute("collaborator", user);
    	model.addAttribute("contributors", userService.all());
        attr.addFlashAttribute("mensagem", user.getName()+" atualizado com sucesso.");
        return new ModelAndView("user/add",model);
    }
    
    

}

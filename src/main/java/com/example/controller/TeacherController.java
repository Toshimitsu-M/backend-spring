package com.example.controller;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.web.PageableDefault;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.Teacher;
import com.example.service.TeacherService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class TeacherController {
	
	//DI
	private final TeacherService service;
	
	@GetMapping("/home")
	public String getAllTeachers(Model model, @PageableDefault(size = 5) Pageable pageable) {
		model.addAttribute("page", service.selectAll());
		return "home";
	}
	
	@GetMapping("/add")
	public String addTeacher(@ModelAttribute Teacher teacher) {
		return "form";
	}
	
	@PostMapping("/process")
	public String process(@Validated @ModelAttribute Teacher teacher, BindingResult result) {
		if (result.hasErrors()) {
			return "form";
		}
		service.save(teacher);
		
		return "redirect:/home";
	}
	
	@GetMapping("/edit/{id}")
	public String editTeacher(@PathVariable Long id, Model model) {
		model.addAttribute("teacher", service.selectByPrimaryKey(id));
		return "form";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTeacher(@PathVariable Long id) {
		service.deleteByPrimaryKey(id);
		return "redirect:/home";
	}
}
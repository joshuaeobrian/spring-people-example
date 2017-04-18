package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by josh on 4/13/17.
 */
@Controller
public class PersonController {
	@Autowired
	private PersonRepository repository;

	@RequestMapping("/")
	public String index(Model model, @RequestParam(defaultValue = "") String search){

			List<Person> people = repository.getPersonBySearch(search);
			model.addAttribute("search", search);
			model.addAttribute("person", people);
			model.addAttribute("count", people.size());

		return "index";
	}

	@GetMapping("/personForm")
	public String personForm(Model model, Integer personId){
		if(personId !=null) {
			model.addAttribute("person", repository.getPersonById(personId));
		}else{
			model.addAttribute("person", new Person());
		}

		return "personForm";
	}
	@PostMapping("/save")
	public String save(Person person){

		if(person.getId() != null){
			repository.updatePerson(person);
			System.out.println("updated");
		}else{
			repository.createPerson(person);
			System.out.println("created");
		}

		return "redirect:/";
	}

	@RequestMapping("/sortbyfirstname")
	public String sortByFirstName(Model model, @RequestParam(defaultValue = "") String search){

		List<Person> people = repository.getPeopleWithFirstNameInOrder(search);
		model.addAttribute("search", search);
		model.addAttribute("person", people);
		model.addAttribute("count", people.size());
		return "index";
	}
	@RequestMapping("/sortbylastname")
	public String sortLastName(Model model, @RequestParam(defaultValue = "") String search){

		List<Person> people = repository.getPeopleWithLastNameInOrder(search);
		model.addAttribute("search", search);
		model.addAttribute("person", people);
		model.addAttribute("count", people.size());
		return "index";
	}


}

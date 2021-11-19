package nl.com.yacht.jspring.javaquiz.controller;

import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nl.com.yacht.jspring.javaquiz.models.Question;
import nl.com.yacht.jspring.javaquiz.repository.QuizQuestionRepository;

@Controller
@RequestMapping("/questions")
public class QuizQuestionController {

	@Autowired
	private QuizQuestionRepository repository;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/addQuestion")
	public String showForm(@RequestParam(name = "id", required = false) ObjectId id, Model model) {
		Question question = new Question();
		
		if (id != null) {
			question = repository.getQuestion(id);
		}
		
		if(question == null) {
			question = new Question();
		}
		
		List<String> quizTypes = repository.getQuizTypes();
		model.addAttribute("question", question);
		model.addAttribute("quizTypes", quizTypes);
		return "quiz/add-question";
	}

	@PostMapping("/saveQuestion")
	public String saveQuestion(@Valid @ModelAttribute("question") Question question, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "quiz/add-question";
		}

		repository.save(question);
		return "redirect:/questions/list";
	}

	@GetMapping("/list")
	public String listQuestions(@RequestParam(defaultValue = "all") String quizType, Model model) {
		List<Question> questions = quizType.equals("all") ? repository.getAll() : repository.findBy(quizType);
		List<String> quizTypes = repository.getQuizTypes();
		model.addAttribute("questions", questions);
		model.addAttribute("quizTypes", quizTypes);
		model.addAttribute("filter", quizType);
		return "quiz/list-questions";
	}

	@GetMapping("/delete")
	public String deleteQuestion(@RequestParam("id") ObjectId id) {
		repository.delete(id);
		return "redirect:/questions/list";
	}

}

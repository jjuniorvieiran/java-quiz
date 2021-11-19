package nl.com.yacht.jspring.javaquiz.controller;

import static nl.com.yacht.jspring.javaquiz.utils.QuizUtils.getHandleQuestion;
import static nl.com.yacht.jspring.javaquiz.utils.QuizUtils.validateAnswer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nl.com.yacht.jspring.javaquiz.models.Question;
import nl.com.yacht.jspring.javaquiz.models.User;
import nl.com.yacht.jspring.javaquiz.repository.QuizQuestionRepository;
import nl.com.yacht.jspring.javaquiz.repository.UserRepository;

@Controller
public class QuizController implements WebMvcConfigurer{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private QuizQuestionRepository quizRepository;
	
	@Autowired
	private Environment env;

	
	@GetMapping("/register")
	public String register(@RequestParam String quiztype, Model model, HttpServletRequest request) {
		
		System.out.println("QuizController.register quizType" + quiztype);
		
		model.addAttribute("quiztype", quiztype);
		
		request.getSession().setAttribute("quiztype", quiztype);
		
		model.addAttribute("homeUrl", env.getProperty("address.home"));
		model.addAttribute("user", new User());
		return "quiz/register";
	}
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/quiz/question").setViewName("quiz/question");
        registry.addViewController("/login").setViewName("login");
    }
    
    @GetMapping("/quiz/saveUser")
    public String showForm(User user) {
        return "quiz/register";
    }
	
	//TODO REFACTORING 
	@PostMapping("saveUser")
	public String saveUser(@Valid User user, BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		
		model.addAttribute("homeUrl", env.getProperty("address.home"));
		
        if (bindingResult.hasErrors()) {
        	return "quiz/register";
        }
        
        String quizType = (String)request.getSession().getAttribute("quiztype");
        List<User> users = repository.findBy(user.getEmail());
        
        if(users.size() > 0) {
        	for(User oldUser : users) {
        		if((oldUser.getQuizType()).equals(quizType)) {
        			return "quiz/register";
        		}
        	}   	
        }
        
        user.setQuizType(quizType);

		repository.save(user);
		
		//prepare first question
		List<Question> randomList = new ArrayList<>();
		try {
			randomList = getHandleQuestion(quizRepository.findBy(quizType));
		}catch (Exception e) {
			return "quiz/register";
		}
		
		Question question = randomList.get(0);
		randomList.remove(0);
		
		request.getSession().setAttribute("questionsTemp", randomList);
		request.getSession().setAttribute("user",user);
		request.getSession().setAttribute("actualPage", 1);
		
		model.addAttribute("question", question);
		model.addAttribute("user", user);
		model.addAttribute("actualPage", 1);
		
		return "quiz/question";
	}
	
	//TODO REFACTORING
	@PostMapping("nextQuestion")
	public String nextQuestion(@ModelAttribute Question question, Model model, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		user.setUserAnswers(user.getUserAnswers() + 1);
		
		validateAnswer(question, user);
		
		//handle next question
		List<Question> questions = (List<Question>) request.getSession().getAttribute("questionsTemp");
		if (questions != null && questions.size() > 0) { //this if is for after last questions was replied
			Question nextQuestion = questions.get(0);
			questions.remove(0);
			model.addAttribute("question", nextQuestion);
		}
		
		//handle page behavior
		Integer actualPage = (Integer) request.getSession().getAttribute("actualPage");
		
		request.getSession().setAttribute("actualPage", ++actualPage);
		request.getSession().setAttribute("questionsTemp", questions);
		request.getSession().setAttribute("user",user);

		model.addAttribute("user", user);
		model.addAttribute("actualPage", actualPage);
		
		return user.getUserAnswers() < 5 ? "quiz/question" : lastQuestion(question, model, request, user);
	}


	
	//TODO REFACTORING
	public String lastQuestion(@ModelAttribute Question question, Model model, HttpServletRequest request, User user) {
		
		repository.save(user);
		
		List<User> users = repository.findUserByQuizType(user.getQuizType());

		model.addAttribute("users", users);
		model.addAttribute("homeUrl", env.getProperty("address.home"));
		
		request.getSession().removeAttribute("questionsTemp");
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("actualPage");
		
		return  "quiz/list";
	}
	
	@PostMapping("/quiz/list")
	public String list(Model model) {

		List<User> user = new ArrayList<User>();
		model.addAttribute("user", user);
		return "quiz/list";
	}
	

	//TODO Refactory me 
	@GetMapping("/score")
	public String listFromIndex(@RequestParam String quiztype, Model model, HttpServletRequest request) {
		
		request.getSession().setAttribute("quiztype", quiztype);

		List<User> users = repository.findUserByQuizType(quiztype);

		model.addAttribute("users", users);
		model.addAttribute("homeUrl", env.getProperty("address.home"));
		
		return "quiz/list";
	}
}

package kube.demo.spring.data.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kube.demo.spring.data.mongodb.model.Log;
import kube.demo.spring.data.mongodb.repository.LogRepository;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/")
public class LogController {

	@Autowired
	LogRepository logRepository;

	@GetMapping("/")
	public String index() {
		return "index";
	}
		
	@GetMapping("/logs")
	public String getAllTutorials(@RequestParam(required = false) String hostname, Model model) {
		List<Log> logs = new ArrayList<Log>();
		try {
			if (hostname == null)
				logRepository.findAll().forEach(logs::add);
			else
				logRepository.findByHostname(hostname).forEach(logs::add);

			if (logs.isEmpty()) {
				model.addAttribute("logs", "");
			}

		} catch (Exception e) {
			return e.getMessage();
		}
		model.addAttribute("logs", logs);
		return "logs";
	}

	@GetMapping("/logs/{id}")
	public String getTutorialById(@PathVariable("id") String id, Model model) {
		Optional<Log> logData = logRepository.findById(id);

		if (logData.isPresent()) {
			model.addAttribute("log", logData.get());
		} else {
			model.addAttribute("log", "");
		}
		return "log";
	}
	/*
	 * @PostMapping("/logs") public ResponseEntity<Tutorial>
	 * createTutorial(@RequestBody Tutorial tutorial) { try { Tutorial _tutorial
	 * = tutorialRepository.save(new Tutorial(tutorial.getTitle(),
	 * tutorial.getDescription(), false)); return new
	 * ResponseEntity<>(_tutorial, HttpStatus.CREATED); } catch (Exception e) {
	 * return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @PutMapping("/tutorials/{id}") public ResponseEntity<Tutorial>
	 * updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial
	 * tutorial) { Optional<Tutorial> tutorialData =
	 * tutorialRepository.findById(id);
	 * 
	 * if (tutorialData.isPresent()) { Tutorial _tutorial = tutorialData.get();
	 * _tutorial.setTitle(tutorial.getTitle());
	 * _tutorial.setDescription(tutorial.getDescription());
	 * _tutorial.setPublished(tutorial.isPublished()); return new
	 * ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK); }
	 * else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); } }
	 * 
	 * @DeleteMapping("/tutorials/{id}") public ResponseEntity<HttpStatus>
	 * deleteTutorial(@PathVariable("id") String id) { try {
	 * tutorialRepository.deleteById(id); return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); } catch (Exception e) { return
	 * new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @DeleteMapping("/tutorials") public ResponseEntity<HttpStatus>
	 * deleteAllTutorials() { try { tutorialRepository.deleteAll(); return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); } catch (Exception e) { return
	 * new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @GetMapping("/tutorials/published") public ResponseEntity<List<Tutorial>>
	 * findByPublished() { try { List<Tutorial> tutorials =
	 * tutorialRepository.findByPublished(true);
	 * 
	 * if (tutorials.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); } return new
	 * ResponseEntity<>(tutorials, HttpStatus.OK); } catch (Exception e) {
	 * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */
}

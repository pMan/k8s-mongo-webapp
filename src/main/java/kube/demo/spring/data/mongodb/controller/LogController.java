package kube.demo.spring.data.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
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

	private String db = System.getenv("MONGO_DBNAME");
	private String user = System.getenv("MONGO_USER");
	private String pass = System.getenv("MONGO_PASS");
	private String collection = System.getenv("MONGO_COLLECTION");
	private String host = System.getenv("MONGO_HOST");
	private String port = System.getenv("MONGO_PORT");
	
	private String getConnString() {
		return "mongodb://" + user + ":" + pass + "@" + host + ":" + port + "/" + db + "?authSource=admin";
	}
	
	@Override
	public String toString() {
		return "[db=" + db + ", user=" + user + ", pass=" + pass + ", collection=" + collection
				+ ", host=" + host + ", port=" + port + "]";
	}

	@Autowired
	LogRepository logRepository;

	@GetMapping("/")
	public String index() {
		System.out.println(this.toString());
		return "index";
	}
		
	@GetMapping("/logs")
	public String getAllTutorials(@RequestParam(required = false) String hostname, Model model) {
		List<Log> logs = new ArrayList<Log>();
		
		try {
			MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(getConnString()));
			logs = mongoOps.findAll(Log.class, collection);
    
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
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(getConnString()));
		Log logData = mongoOps.findById(id, Log.class);
		
		if (logData != null) {
			model.addAttribute("log", logData);
		} else {
			model.addAttribute("log", "");
		}
		return "log";
	}
}

package kube.demo.spring.data.mongodb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kube.demo.spring.data.mongodb.model.Log;
import kube.demo.spring.data.mongodb.model.StockData;

@Controller
@RequestMapping("/")
public class LogController {

	/*private String db = System.getenv("MONGO_DBNAME");
	private String user = System.getenv("MONGO_USER");
	private String pass = System.getenv("MONGO_PASS");
	private String collection = System.getenv("MONGO_COLLECTION");
	private String host = System.getenv("MONGO_HOST");
	private String port = System.getenv("MONGO_PORT");
	*/
	
	private String db = "local";
	private String user = "adminuser";
	private String pass = "password123";
	private String collection = "yf-stock-minutes";
	private String host = "a22437652ec75464b9aec75355b895fe-699670675.us-east-2.elb.amazonaws.com";
	//private String host = "localhost";
	private String port = "27017";
	
	
	private String getConnString() {
		return "mongodb://" + user + ":" + pass + "@" + host + ":" + port + "/" + db + "?authSource=admin";
	}
	
	@Override
	public String toString() {
		return "[db=" + db + ", user=" + user + ", pass=" + pass + ", collection=" + collection
				+ ", host=" + host + ", port=" + port + "]";
	}

	@GetMapping("/")
	public String index() {
		System.out.println(this.toString());
		return "index";
	}
	
	@GetMapping("/logs")
	public String getAllLogs(Model model) {
		List<Log> logs = new ArrayList<Log>();
		
		try {
			MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(getConnString()));
			logs = mongoOps.findAll(Log.class, "startup_log");
    
			if (logs.isEmpty()) {
				model.addAttribute("logs", "");
			}

		} catch (Exception e) {
			return e.getMessage();
		}
		model.addAttribute("logs", logs);
		return "logs";
	}


	@GetMapping("/stock/{count}")
	public String getAllStockData(@PathVariable String count, Model model) {
		List<StockData> stockData = new ArrayList<StockData>();
		
		try {
			MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(getConnString()));
			Sort s = Sort.by(Sort.Direction.DESC, "Recorded_time");
			Query q = new Query().with(s).limit(Integer.parseInt(count));
			stockData = mongoOps.find(q, StockData.class, "yf_nse_data");
    
			if (stockData.isEmpty()) {
				model.addAttribute("datalist", "");
			}

		} catch (Exception e) {
			return e.getMessage();
		}
		
		List<Object> infylist = new ArrayList<>();
		List<Object> tcslist = new ArrayList<>();
		Map<String, Double[]> data = new TreeMap<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy M dd HH mm");
		for (StockData stockDoc : stockData) {
			String currentMinuteKey = sdf.format(stockDoc.getId().getDate());
			Double[] list = data.get(currentMinuteKey);
			if (list == null )
				list = new Double[2];  
			
			//list.add(s.getCurrentTime());
			if ("INFY.NS".equals(stockDoc.getStockName())) {
				list[1] = stockDoc.getClose();
			} else if ("TCS.NS".equals(stockDoc.getStockName())) {
				list[0] = stockDoc.getClose();
			}
			//System.out.println(list[0] + "," + list[1]);
			data.put(currentMinuteKey, list);
		}
		
		data.entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + ": " + entry.getValue()[0] + ", " + entry.getValue()[1]);
		    if (entry.getValue()[0] != null &&  entry.getValue()[1] != null) {
		    	tcslist.add(Arrays.asList(entry.getKey(), entry.getValue()[0]));
		    	infylist.add(Arrays.asList(entry.getKey(), entry.getValue()[1]));
		    }
		});
		
		//model.addAttribute("data", stockData);
		model.addAttribute("infylist", infylist);
		model.addAttribute("tcslist", tcslist);
		return "stockdata";
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

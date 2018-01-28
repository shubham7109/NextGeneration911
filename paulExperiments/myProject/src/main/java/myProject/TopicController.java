package myProject;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
	
	@Autowired //marked as "Needs dependency injection"
	private TopicService topicService;
	
	//return type automatically converted to json
	@RequestMapping("/topics")
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}
	
	//curly brackets indicate variable portion
	//PathVariable maps {id} to id
	@RequestMapping("/topics/{id}")
	public Topic getTopic(@PathVariable("id") String id) {
		return topicService.getTopic(id);
	}
	
	//Get request is the default; we have to specify a post request
	@RequestMapping(method=RequestMethod.POST, value="/topics")
	public void addTopic(@RequestBody Topic topic) {
		topicService.addTopic(topic);
	}
}

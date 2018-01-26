package myProject;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
	
	//return type automatically converted to json
	@RequestMapping("/topics")
	public List<Topic> getAllTopics() {
		return Arrays.asList(
				new Topic("Spring", "SpringFramework", "SpringFrameworkDescription"),
				new Topic("Example", "exa", "exa"),
				new Topic("Test", "test", "TEST")
				);
	}
}

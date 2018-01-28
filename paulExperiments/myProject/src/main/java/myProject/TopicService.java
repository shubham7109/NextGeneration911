package myProject;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service //Stereotype annotation
public class TopicService {
	
	private List<Topic> topics = Arrays.asList(
			new Topic("Spring", "SpringFramework", "SpringFrameworkDescription"),
			new Topic("Example", "exa", "exa"),
			new Topic("Test", "test", "TEST")
			);
	
	public List<Topic> getAllTopics() {
		return topics;
	}
	
	public Topic getTopic(String id) {
		return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}
	
}

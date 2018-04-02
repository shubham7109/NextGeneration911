package person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

	@Autowired
	private DeployService deployService; 
	
	@RequestMapping("/deploy")
	public Iterable<Deploy> getAllDeploys()
	{
		return deployService.getAllDeploys();
	}
	
	@RequestMapping("/deploy/{id}")
	public Deploy getDeploy(@PathVariable("id") String id) {
		return deployService.getDeploy(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/deploy")
	public void addDeploy(@RequestBody Deploy dep) 
	{
		deployService.addDeploy(dep);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/deploy/{id}")
	public void updateDeploy(@RequestBody Deploy deploy, @PathVariable("id") String id) 
	{
		deployService.updateDeploy(id, deploy);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/deploy/{id}")
	public void deleteDeploy(@PathVariable("id") String id)
	{
		deployService.deleteDeploy(id);
	}
}

package app.deploy;

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
	
	@Autowired
	private DeployWanderer deployWanderer;
	
	
	/**
	 * @return list of all Deploys
	 */
	@RequestMapping("/deploy")
	public Iterable<Deploy> getAllDeploys()
	{
		return deployService.getAllDeploys();
	}
	
	
	/**
	 * @param id of deploy
	 * @return deploy with the given id or null if the id doesn't exist
	 */
	@RequestMapping("/deploy/{id}")
	public Deploy getDeploy(@PathVariable("id") String id) {
		return deployService.getDeploy(id);
	}
	
	
	/**
	 * adds a deploy to the database
	 * @param dep
	 */
	@RequestMapping(method=RequestMethod.POST, value="/deploy")
	public void addDeploy(@RequestBody Deploy dep) 
	{
		deployService.addDeploy(dep);
	}
	
	/**
	 * Updates a deploy in the database
	 * @param deploy deploy object
	 * @param id of deploy
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/deploy/{id}")
	public void updateDeploy(@RequestBody Deploy deploy, @PathVariable("id") String id) 
	{
		deployService.updateDeploy(id, deploy);
	}
	
	/**
	 * Deletes a deploy in the database
	 * @param id of deploy
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/deploy/{id}")
	public void deleteDeploy(@PathVariable("id") String id)
	{
		deployService.deleteDeploy(id);
	}
	
	@RequestMapping("/deploy/initialize")
	public void initializeDeploys() {
		deployWanderer.initializeCoord();
	}
}

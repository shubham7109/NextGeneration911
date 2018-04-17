package app.deploy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeployService {

	@Autowired
	private DeployRepository deployRepository; 
	
	/**
	 * @return list of deploys
	 */
	public List<Deploy> getAllDeploys() 
	{
		List<Deploy> deploys = new ArrayList<>();
		deployRepository.findAll().forEach(deploys::add);
		return deploys;
	}
	
	/**
	 * @param id of deploy
	 * @return deploy
	 */
	public Deploy getDeploy(String id) 
	{
		return deployRepository.findOne(id);
	}
	
	/**
	 * adds a deploy to the database
	 * @param dep deploy
	 */
	public void addDeploy(Deploy dep) {
		deployRepository.save(dep);
	}
	
	/**
	 * updates a deploy in the database
	 * @param id of deploy
	 * @param dep the deploy
	 */
	public void updateDeploy(String id, Deploy dep) 
	{
		deployRepository.save(dep);
	}
	
	/**
	 * deletes a deploy
	 * @param id of deploy
	 */
	public void deleteDeploy(String id) 
	{
		deployRepository.delete(id);
	}
}

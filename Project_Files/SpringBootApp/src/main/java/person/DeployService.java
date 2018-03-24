package person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeployService {

	@Autowired
	private DeployRepository deployRepository; 
	
	public List<Deploy> getAllDeploys() 
	{
		List<Deploy> deploys = new ArrayList<>();
		deployRepository.findAll().forEach(deploys::add);
		return deploys;
	}
	
	public Deploy getDeploy(String id) 
	{
		return deployRepository.findOne(id);
	}
	
	public void addDeploy(Deploy dep) {
		deployRepository.save(dep);
	}
	
	public void updateDeploy(String id, Deploy dep) 
	{
		deployRepository.save(dep);
	}
	
	public void deleteDeploy(String id) 
	{
		deployRepository.delete(id);
	}
}

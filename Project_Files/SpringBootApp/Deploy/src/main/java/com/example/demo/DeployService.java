package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DeployService {

	private List<Deploy> deploy = new ArrayList<>(Arrays.asList(
			new Deploy("1", "Ambulance", "-38.16157", "-61.21903"),
			new Deploy("2", "Fire Brigade", "1.70208", "-30.65703"),
			new Deploy("3", "First Responders", "-57.92470", "-52.92323"),
			new Deploy("4", "State Troopers", "-3.32945", "-166.53464"),
			new Deploy("5", "County Officers", "13.21004", "162.12095"),
			new Deploy("6", "Swat Team", "10.72418", "35.40650")
			));
	
	public List<Deploy> getAllDeploys() 
	{
		return deploy;
	}
	
	public Deploy getDeploy(String id) 
	{
		for(int i = 0; i < deploy.size(); i++)
		{
			if(deploy.get(i).getID().equals(id))
				return deploy.get(i); 
		}
		return null;
	}
	
	public void addDeploy(Deploy dep) {
		deploy.add(dep);
	}
	
	public void updateDeploy(String id, Deploy dep) 
	{
		for (int i = 0; i < deploy.size(); i++) 
		{
			Deploy o = deploy.get(i);
			if (o.getID() == id) 
			{
				deploy.set(i, dep);
				return;
			}
		}
	}
	
	public void deleteDeploy(String id) 
	{
		for (int i = 0; i < deploy.size(); i++) 
		{
			Deploy o = deploy.get(i);
			if (o.getID() == id) 
			{
				deploy.remove(i);
			}
		}
	}
}

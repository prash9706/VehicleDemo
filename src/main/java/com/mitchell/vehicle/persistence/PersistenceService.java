package com.mitchell.vehicle.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mitchell.vehicle.entity.model.Vehicle;
import com.mitchell.vehicle.util.Message;

@Service
public class PersistenceService {
	
	private Map<Integer, Vehicle> repository;
	public PersistenceService(){
		this.repository = new HashMap<>();
	}
	
	public List<Vehicle> getAllVehicle(){
		List<Vehicle> vehicleList = new ArrayList<>();
		for(Map.Entry<Integer, Vehicle> entry:repository.entrySet()){
			vehicleList.add(entry.getValue());
		}
		return vehicleList;
	}
	
	public Vehicle getVehicleById(int id){
		return repository.get(id);
	}
	
	public Message deleteVehicle(int id){
		if(repository.containsKey(id)){
			repository.remove(id);
			return Message.SUCCESS;
		}
		return Message.NOT_FOUND;
	}
	
	public Message saveVehicle(Vehicle vehicle){
		if(repository.containsKey(vehicle.getId())){
			return Message.DUPLICATE;
		}
		repository.put(vehicle.getId(), vehicle);
		return Message.SUCCESS;
	}
	
	public Message updateVehicle(Vehicle vehicle){
		if(!repository.containsKey(vehicle.getId())){
			return Message.NOT_FOUND;
		}
		repository.put(vehicle.getId(), vehicle);
		return Message.SUCCESS;
	}
}

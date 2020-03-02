package com.mitchell.vehicle.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitchell.vehicle.entity.model.Vehicle;
import com.mitchell.vehicle.persistence.PersistenceService;
import com.mitchell.vehicle.util.Message;

@RestController
@Description("Controller to handle all operations related to Vehicle")
public class VehicleController {
	
	@Autowired
	PersistenceService persistenceService;
	
	@RequestMapping("/status")
	public String getHealth(){
		return "Running";
	}
	
	@RequestMapping(value="/")
    public String homepage(){
        return "/static/index.html";
    }
	
	@GetMapping("/get")
	public ResponseEntity<List<Vehicle>> getAllVehicles(){
		List<Vehicle> vehicleList = persistenceService.getAllVehicle();
		return new ResponseEntity<List<Vehicle>>(vehicleList, HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Vehicle> getVehicle(@PathVariable int id){
		Vehicle vehicle = persistenceService.getVehicleById(id);
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Message> createVehicle(HttpServletRequest request){
		Message message = null;
		String id = request.getParameter("id");
		String model = request.getParameter("model");
		String make = request.getParameter("make");
		String year = request.getParameter("year");
		if(id==null){
			message = Message.ID_REQUIRED;
		}else if(model==null){
			message = Message.MODEL_REQUIRED;
		}else if(make==null){
			message = Message.MAKE_REQUIRED;
		}else if(year!=null && (1950>Integer.parseInt(year)
				|| Integer.parseInt(year)>2050)){
			message = Message.INVALID_YEAR_1950_TO_2050;
		}else{
			Vehicle vehicle = new Vehicle(Integer.parseInt(id));
			vehicle.setMake(make);
			vehicle.setModel(model);
			if(year!=null)
				vehicle.setYear(Integer.parseInt(year));
			message = persistenceService.saveVehicle(vehicle);
		}
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Message> deleteVehicle(HttpServletRequest request){
		int id = Integer.parseInt(request.getParameter("id"));
		Message message = persistenceService.deleteVehicle(id);
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<Message> updateVehicle(HttpServletRequest request){
		Message message = null;
		String id = request.getParameter("id");
		String model = request.getParameter("model");
		String make = request.getParameter("make");
		String year = request.getParameter("year");
		if(model==null){
			message = Message.MODEL_REQUIRED;
		}else if(make==null){
			message = Message.MAKE_REQUIRED;
		}else if(year!=null && (1950>Integer.parseInt(year)
				|| Integer.parseInt(year)>2050)){
			message = Message.INVALID_YEAR_1950_TO_2050;
		}else{
			Vehicle vehicle = new Vehicle(Integer.parseInt(id));
			vehicle.setMake(make);
			vehicle.setModel(model);
			if(year!=null)
				vehicle.setYear(Integer.parseInt(year));
			message = persistenceService.updateVehicle(vehicle);
		}
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
}

package com.mitchell.vehicle;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mitchell.vehicle.entity.model.Vehicle;
import com.mitchell.vehicle.persistence.PersistenceService;
import com.mitchell.vehicle.util.Message;

public class PersistenceServiceTest {
	
	
	PersistenceService persistenceService;
	
	@BeforeTest
	public void beforeTest(){
		persistenceService = new PersistenceService();
	}
	
	@Test(enabled=true)
	public void test_getAllVehicle(){
		Vehicle vehicle = new Vehicle(1);
		vehicle.setMake("toyota");
		vehicle.setModel("Camry");
		vehicle.setYear(2019);
		persistenceService.saveVehicle(vehicle);
		Assert.assertEquals(1, persistenceService.getAllVehicle().size());
	}
	
	@Test(enabled=true)
	public void test_getVehicleById(){
		Vehicle vehicle = new Vehicle(2);
		vehicle.setMake("toyota");
		vehicle.setModel("Camry");
		vehicle.setYear(2019);
		persistenceService.saveVehicle(vehicle);
		Assert.assertEquals(vehicle, persistenceService.getVehicleById(2));
	}
	
	@Test(enabled=true)
	public void test_deleteVehicle(){
		Vehicle vehicle = new Vehicle(3);
		vehicle.setMake("toyota");
		vehicle.setModel("Camry");
		vehicle.setYear(2019);
		persistenceService.saveVehicle(vehicle);
		Assert.assertEquals(Message.SUCCESS, persistenceService.deleteVehicle(3));
	}
	
	@Test(enabled=true)
	public void test_updateVehicle(){
		Vehicle vehicle = new Vehicle(4);
		vehicle.setMake("toyota");
		vehicle.setModel("Camry");
		vehicle.setYear(2019);
		persistenceService.saveVehicle(vehicle);
		Vehicle vehicle2 = new Vehicle(5);
		vehicle2.setMake("toyota");
		vehicle2.setModel("Yaris");
		vehicle2.setYear(2019);
		Assert.assertEquals(Message.NOT_FOUND, persistenceService.updateVehicle(vehicle2));
		vehicle2.setId(4);
		Assert.assertEquals(Message.SUCCESS, persistenceService.updateVehicle(vehicle2));
	}
}

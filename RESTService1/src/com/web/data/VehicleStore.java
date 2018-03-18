package com.web.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.web.filter.UserFilter;

public class VehicleStore {
	private static final Logger LOGGER = Logger.getLogger(VehicleStore.class);
	static VehicleStore vehicleStore = null;
	List<Vehicle> _listOfVehicles = null;
	
	private VehicleStore() {
		_listOfVehicles = createVehicleList();
	}
	
	public static VehicleStore getStoreInstance() {
		if(vehicleStore == null) {
			vehicleStore = new VehicleStore();
		}
		return vehicleStore;
	}
	
	public void addVehicle(Vehicle vehicle) {
		_listOfVehicles.add(vehicle);
	}
	
	public List<Vehicle> getVehicleList(){
		return _listOfVehicles;
	}
	
	public List<Vehicle> createVehicleList(){
			LOGGER.info("Creating Vehicle List");
			Vehicle suzukiVehicle=new Vehicle(1, "Swif"); 
			Vehicle bmwVehicle=new Vehicle(4, "BMW"); 
			Vehicle volvoVehicle=new Vehicle(3, "Volvo"); 
			Vehicle jeepVehicle=new Vehicle(2, "Jeep"); 
			List<Vehicle> listOfVehicles = new ArrayList<Vehicle>(); 
			listOfVehicles.add(suzukiVehicle); 
			listOfVehicles.add(bmwVehicle); 
			listOfVehicles.add(volvoVehicle);
			listOfVehicles.add(jeepVehicle); 
			return listOfVehicles;		
	}
}

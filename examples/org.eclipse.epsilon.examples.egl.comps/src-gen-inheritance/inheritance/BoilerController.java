package inheritance;

public class BoilerController {
	
	public int execute(int temperature, int targetTemperature, boolean boilerStatus) {
		
		TemperatureController temperatureController = new TemperatureController();
		int temperatureControllerResult = temperatureController.execute(temperature, targetTemperature);
		BoilerActuator boilerActuator = new BoilerActuator();
		int boilerActuatorResult = boilerActuator.execute(temperatureControllerResult, boilerStatus);
		
		return boilerActuatorResult;
		
	}
	
}


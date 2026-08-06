package boilercontroller;

public class TemperatureController {
	
	public int execute(int temperature, int targetTemperature) {
		System.out.println("TemperatureController was invoked");
		System.out.println("The value of temperature was " + temperature);
		System.out.println("The value of targetTemperature was " + targetTemperature);
		// protected region execute on begin
		return temperature - targetTemperature;
		// protected region execute end
	}
	
}


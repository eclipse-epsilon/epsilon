package boilercontroller;

public class BoilerActuator {
	
	public int execute(int temperatureDifference, boolean boilerStatus) {
		// protected region execute on begin
		if (temperatureDifference > 0 && boilerStatus == true) {
			return 1; // turn off boiler
		}
		else if (temperatureDifference < 0 && boilerStatus == false) {
			return 2; // turn on boiler
		}
		else return 0; // do nothing		
		// protected region execute end
	}
	
}
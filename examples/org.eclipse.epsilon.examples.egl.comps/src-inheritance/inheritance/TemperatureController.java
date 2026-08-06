package inheritance;

public class TemperatureController extends TemperatureControllerBase {

	@Override
	public int execute(int temperature, int targetTemperature) {
		return temperature - targetTemperature;
	}

}

package inheritance;

import protectedregions.BoilerController;

public class TestBoilerController {
	
	public static void main(String[] args) {
		
		BoilerController controller = new BoilerController();
		System.out.println(controller.execute(10, 15, false)); // Should print 2 (turn on boiler)
		
	}
	
}

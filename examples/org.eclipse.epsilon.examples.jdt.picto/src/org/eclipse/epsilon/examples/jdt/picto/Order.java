package org.eclipse.epsilon.examples.jdt.picto;

import java.util.List;

public class Order implements Shippable {
	
	protected List<OrderItem> items;
	protected Employee seller;
	protected Customer customer;
	
	@Override
	public void shipTo(Address address) {
		
	}
}

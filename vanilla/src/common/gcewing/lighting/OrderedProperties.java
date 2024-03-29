package gcewing.lighting;

import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;

public class OrderedProperties extends Properties {

	Vector<Object> order = new Vector<Object>();
	public boolean extended = false;

	public Object put(Object key, Object value) {
		//System.out.printf("GregsProspecting: Putting %s->%s in properties\n",
		//	key, value);
		if (!containsKey(key)) {
			//System.out.printf("GregsProspecting: Adding to order\n");
			order.add(key);
			extended = true;
		}
		return super.put(key, value);
	}
	
	public Enumeration<Object> keys() {
		//System.out.printf("GregsProspecting: Ordered property keys: %s\n", order);
		return order.elements();
	}

}

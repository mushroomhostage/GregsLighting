package gcewing.lighting;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class OrderedProperties extends Properties
{
    Vector order = new Vector();
    public boolean extended = false;

    public Object put(Object var1, Object var2)
    {
        if (!this.containsKey(var1))
        {
            this.order.add(var1);
            this.extended = true;
        }

        return super.put(var1, var2);
    }

    public Enumeration keys()
    {
        return this.order.elements();
    }
}

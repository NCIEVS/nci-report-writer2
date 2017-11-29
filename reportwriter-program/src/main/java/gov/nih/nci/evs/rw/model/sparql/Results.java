package gov.nih.nci.evs.rw.model.sparql;

public class Results {
	   private Bindings[] bindings;

	    public Bindings[] getBindings ()
	    {
	        return bindings;
	    }

	    public void setBindings (Bindings[] bindings)
	    {
	        this.bindings = bindings;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [bindings = "+bindings+"]";
	    }
}

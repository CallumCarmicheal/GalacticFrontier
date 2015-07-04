import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Test {
	
	public int getRID(int ID) throws ScriptException {      
	    ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");   
		Object result = engine.eval("R.id.imageButton" + ID);
		
		int value = new BigDecimal(result.toString()).intValue();
		return value;
	}
}

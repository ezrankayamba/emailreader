package tz.co.nezatech.systems.api.emailreader.data;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class Schema {
	String type;
	Map<String, String> parameters;

	public Schema(Class<?> type) {
		super();
		parameters = new LinkedHashMap<>();
		this.type = type.getSimpleName();
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (!f.getName().equalsIgnoreCase("schema")) {
				parameters.put(f.getName(), f.getType().getSimpleName());
			}
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}

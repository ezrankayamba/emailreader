package tz.co.nezatech.systems.api.emailreader.data;



public class BaseData implements IData {

	private Schema schema;
	private Integer id;

	public BaseData() {
		super();
		setSchema();
	}

	public void setSchema() {
		try {
			this.schema = new Schema(this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Schema getSchema() {
		return schema;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	

}

package tz.co.nezatech.systems.api.emailreader.data;

public class Status {
	private int code;
	private String message;
	private int generatedId;

	public Status(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Status(int code, String message, int generatedId) {
		super();
		this.code = code;
		this.message = message;
		this.generatedId = generatedId;
	}

	public int getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(int generatedId) {
		this.generatedId = generatedId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

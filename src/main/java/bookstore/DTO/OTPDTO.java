package bookstore.DTO;

public class OTPDTO {
	private String code;
	private String email;
	private long expiryTime;
	private boolean isUsed; 
	
	public OTPDTO(String code, String email) {
		this.code = code;
		this.email = email;
		this.expiryTime = System.currentTimeMillis() + 900000;
		this.isUsed = false;
	}
	
	public boolean isExpired() {
		System.out.println("System.currentTimeMillis(): " + System.currentTimeMillis());
		System.out.println("this.expiryTime: " + this.expiryTime);
		System.out.print(System.currentTimeMillis() - this.expiryTime);
		return System.currentTimeMillis() > this.expiryTime;
 	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
}

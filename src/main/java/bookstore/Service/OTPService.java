package bookstore.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import bookstore.DTO.OTPDTO;

@Service
public class OTPService {
	private Map<String, OTPDTO> otpStore = new HashMap<>();
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int OTP_LENGTH = 10;
	private SecureRandom random = new SecureRandom();
	
	public String  storeOtp(String email) {
		String otpCode = generateOTP();
		
		OTPDTO otp = new OTPDTO(otpCode, email);
		otpStore.put(otpCode, otp);
		
		return otpCode;
	}
	
	private String generateOTP() {
		StringBuilder otpCode = new StringBuilder(OTP_LENGTH);
		for (int i = 0; i < OTP_LENGTH; i++) {
			int index = random.nextInt(CHARACTERS.length());
			otpCode.append(CHARACTERS.charAt(index));
		}
		
		return otpCode.toString();
	}
	
	public OTPDTO getOTPByCode(String otpCode) {
	    OTPDTO otp = otpStore.get(otpCode);
	    
	    return otp;
	}
	
	public void removeOTPCode(String otpCode) {
		otpStore.remove(otpCode);
	}

}

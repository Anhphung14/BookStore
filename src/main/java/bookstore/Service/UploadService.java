package bookstore.Service;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class UploadService {
	
	@Autowired
	Cloudinary cloudinary;

	
	public String toSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        // Chuẩn hóa chuỗi và loại bỏ dấu
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String noDiacritics = normalized.replaceAll("\\p{M}", ""); // Loại bỏ dấu chính xác

        // Giữ lại chữ cái, số, thay Đ/đ thành d, rồi loại bỏ ký tự đặc biệt
        noDiacritics = noDiacritics.replaceAll("[Đđ]", "d"); // Xử lý chữ Đ và đ
        return Arrays.stream(noDiacritics.split("\\s+")) // Split theo khoảng trắng
                     .map(word -> word.replaceAll("[^a-zA-Z0-9]", "")) // Loại bỏ ký tự đặc biệt
                     .filter(word -> !word.isEmpty()) // Loại bỏ từ rỗng
                     .map(String::toLowerCase) // Chuyển thành chữ thường
                     .collect(Collectors.joining("-")); // Ghép lại bằng dấu gạch ngang
    }
	
	public String uploadByCloudinary(MultipartFile thumbnail, String folderName) throws IOException {
		Map r = cloudinary.uploader().upload(thumbnail.getBytes(), ObjectUtils.asMap("folder", folderName));
		
		String imagePath = (String) r.get("secure_url");
		
		System.out.println(imagePath);
		
		return imagePath;
	}
}

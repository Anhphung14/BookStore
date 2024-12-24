package bookstore.Utils;

import org.owasp.encoder.Encode;

public class EscapeHtmlUtil {
	public static String encodeHtml(String input) {
        if (input == null) {
            return null;
        }
        return Encode.forHtml(input);
    }
}

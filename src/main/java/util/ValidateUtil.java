package util;

import org.springframework.http.HttpHeaders;

public final class ValidateUtil {

	private ValidateUtil() {}

	public static void rateLimitCheck(HttpHeaders headers) {
        String remaining = headers.getFirst("X-RateLimit-Remaining");
        String resetTime = headers.getFirst("X-RateLimit-Reset");

        if (remaining != null && Integer.parseInt(remaining) == 0) {
            long resetTimestamp = Long.parseLong(resetTime);
            long currentTimestamp = System.currentTimeMillis() / 1000;
            long waitTime = resetTimestamp - currentTimestamp;
            if (waitTime > 0) {
            	throw new RuntimeException("Limite de requisições atingido. Tente novamente em " + waitTime + " segundos.");
            }
        }
    }
}

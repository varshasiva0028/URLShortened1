import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class URLShortener {
    private static final Map<String, String> urlMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longURL = scanner.nextLine();
                    try {
                        String shortURL = shortenURL(longURL);
                        System.out.println("Shortened URL: " + shortURL);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter the short URL: ");
                    String shortURL = scanner.nextLine();
                    try {
                        String expandedURL = expandURL(shortURL);
                        System.out.println("Expanded URL: " + expandedURL);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private static String shortenURL(String longURL) throws NoSuchAlgorithmException {
        if (urlMap.containsValue(longURL)) {
            throw new RuntimeException("Duplicate long URL detected.");
        }

        String hash = generateHash(longURL);
        String shortURL = "http://short.url/" + hash;
        urlMap.put(shortURL, longURL);
        return shortURL;
    }

    private static String expandURL(String shortURL) {
        String longURL = urlMap.get(shortURL);
        if (longURL == null) {
            throw new RuntimeException("Invalid short URL.");
        }
        return longURL;
    }

    private static String generateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes).substring(0, 8);
    }
}
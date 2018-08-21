package zhanj;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException() {
        this("");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

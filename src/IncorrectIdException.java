public class IncorrectIdException extends RuntimeException {
    public IncorrectIdException(String message)
    {
        super(message);
    }
}

// If I need to throw exception inside the constructor then it means
// I need to extend RuntimeException instead of Exception class because it should be unchecked exception
package my.tunesProject.exceptions;

public class PetShopException extends Exception{
    public PetShopException() {
        super();
    }

    public PetShopException(String message) {
        super(message);
    }

    public PetShopException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetShopException(Throwable cause) {
        super(cause);
    }

    protected PetShopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

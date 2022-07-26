package exception;

/**
 * @author Iceberry
 * @date 2022/7/26
 * @desc
 * @since 1.0
 */
public class NullOutputPathException extends RuntimeException{
    public NullOutputPathException(String message) {
        super(message);
    }
}

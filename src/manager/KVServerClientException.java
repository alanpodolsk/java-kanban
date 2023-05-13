package manager;

public class KVServerClientException extends RuntimeException {

    public KVServerClientException() {
    }

    public KVServerClientException (String message) {
        super(message);
    }

}
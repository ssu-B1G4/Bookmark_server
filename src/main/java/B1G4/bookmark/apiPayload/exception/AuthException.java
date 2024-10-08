package B1G4.bookmark.apiPayload.exception;
import B1G4.bookmark.apiPayload.code.BaseErrorCode;

public class AuthException extends GeneralException {

    public AuthException(BaseErrorCode code) {
        super(code);
    }
}
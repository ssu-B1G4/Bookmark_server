package B1G4.bookmark.apiPayload.exception;

import B1G4.bookmark.apiPayload.code.BaseErrorCode;

public class UserException extends GeneralException {

    public UserException(BaseErrorCode code) {
        super(code);
    }
}

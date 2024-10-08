package B1G4.bookmark.apiPayload.exception;

import B1G4.bookmark.apiPayload.code.BaseErrorCode;

public class S3Exception extends GeneralException {

    public S3Exception(BaseErrorCode code) {
        super(code);
    }
}
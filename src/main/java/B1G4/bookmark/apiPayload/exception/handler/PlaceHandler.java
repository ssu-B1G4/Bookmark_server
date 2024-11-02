package B1G4.bookmark.apiPayload.exception.handler;

import B1G4.bookmark.apiPayload.code.BaseErrorCode;
import B1G4.bookmark.apiPayload.exception.GeneralException;

public class PlaceHandler extends GeneralException {
    public PlaceHandler(BaseErrorCode errorCode) {super(errorCode);}
}

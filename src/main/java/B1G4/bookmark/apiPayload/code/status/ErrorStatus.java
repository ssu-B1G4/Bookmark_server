package B1G4.bookmark.apiPayload.code.status;

import B1G4.bookmark.apiPayload.code.BaseErrorCode;
import B1G4.bookmark.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 기본 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 공통 에러
    PAGE_UNDER_ZERO(HttpStatus.BAD_REQUEST, "COMM_001", "페이지는 0이상이어야 합니다."),

    // S3 관련
    S3_OBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "S3_001", "S3 오브젝트를 찾을 수 없습니다."),
    S3_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3_002", "S3 업로드 실패"),
    S3_EMPTY_FILE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "S3_003", "파일이 존재하지 않습니다."),
    S3_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3_004", "S3 삭제 실패"),

    // Auth 관련
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "토큰이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다."),
    INVALID_LOGIN_REQUEST(HttpStatus.UNAUTHORIZED, "AUTH_003", "올바른 아이디나 패스워드가 아닙니다."),
    NOT_EQUAL_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_004", "리프레시 토큰이 다릅니다."),
    NOT_CONTAIN_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_005", "해당하는 토큰이 저장되어있지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_006", "비밀번호가 일치하지 않습니다."),
    INVALID_REQUEST_INFO(HttpStatus.UNAUTHORIZED, "AUTH_007", "카카오 정보 불러오기에 실패하였습니다."),
    AUTH_INVALID_CODE(HttpStatus.UNAUTHORIZED, "AUTH_008", "코드가 유효하지 않습니다."),

    // User 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "존재하지 않는 사용자입니다."),
    USER_EXISTS(HttpStatus.BAD_REQUEST, "USER_002", "이미 존재하는 아이디입니다."),
    USER_DELETE_FAILED(HttpStatus.NOT_FOUND, "USER_003", "회원 탈퇴에 실패했습니다."),

    //Search 관련
    SEARCH_CONDITION_INVALID(HttpStatus.BAD_REQUEST, "SEARCH_001", "검색 조건이 하나라도 존재해야 합니다."),
    RECREATION_NOT_FOUND(HttpStatus.NOT_FOUND, "SEARCH_001", "검색 결과가 존재하지 않습니다."),

    //Place 관련
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_001", "필터에 해당하는 카테고리가 없습니다. 실내/야외 중 선택해주세요."),
    MOOD_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_002", "필터에 해당하는 분위기가 없습니다. 편안한/신나는/차분한/즐거운/아늑한/재미있는 중에 선택해주세요."),
    SIZE_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_003", "필터에 해당하는 공간크기가 없습니다. 부족/보통/넉넉 중에 선택해주세요."),
    OUTLET_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_004", "필터에 해당하는 콘센트가 없습니다. 부족/보통/넉넉 중에 선택해주세요."),
    NOISE_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_005", "필터에 해당하는 소음이 없습니다. 조용함/보통/생기있음 중에 선택해주세요."),
    INVALID_DAY(HttpStatus.BAD_REQUEST, "PLACE_006", "잘못된 요일 입니다."),
    WIFI_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_007", "필터에 해당하는 와이파이가 없습니다. 있어요/없어요 중에 선택해주세요."),
    INVALID_TIME_FILTER(HttpStatus.BAD_REQUEST, "PLACE_008", "운영 시간 필터가 잘못되었습니다. 필터를 적용할 시간과 요일을 함께 보내주세요"),
    BOOKMARK_FAILED(HttpStatus.BAD_REQUEST, "PLACE_009", "해당 공간 북마크 등록에 실패하였습니다."),
    UNBOOKMARK_FAILED(HttpStatus.BAD_REQUEST, "PLACE_010", "해당 공간 북마크 해제에 실패하였습니다."),
    USERPLACE_NOT_FOUND(HttpStatus.BAD_REQUEST, "PLACE_011", "해당 유저의 저장 공간이 존재하지 않습니다."),
    PLACE_NOT_FOUND(HttpStatus.BAD_REQUEST, "PLACE_012", "해당 공간이 존재하지 않습니다."),

    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
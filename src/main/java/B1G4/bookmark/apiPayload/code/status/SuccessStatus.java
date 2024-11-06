package B1G4.bookmark.apiPayload.code.status;

import B1G4.bookmark.apiPayload.code.BaseCode;
import B1G4.bookmark.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    _CREATED(HttpStatus.CREATED, "COMMON201", "요청 성공 및 리소스 생성됨"),

    REVIEW_CREATE_OK(HttpStatus.OK, "REVIEW201", "리뷰 등록 성공"),

    // 장소
    PLACE_CREATE_OK(HttpStatus.OK, "PLACE2001", "공간 등록이 완료되었습니다."),
    PLACE_PREVIEW_OK(HttpStatus.OK, "PLACE2002", "공간 미리보기가 완료되었습니다."),
    PLACE_DETAIL_OK(HttpStatus.OK, "PLACE2003", "공간 상세보기가 완료되었습니다."),
    NEARBY_PLACE_OK(HttpStatus.OK, "PLACE2004","근처 공간 조회가 완료되었습니다."),
    SEARCH_PLACE_OK(HttpStatus.OK, "PLACE2005", "공간 검색 결과 조회가 완료되었습니다."),
    BOOKMARK_PLACE_OK(HttpStatus.OK, "PLACE2006", "공간 저장(북마크)가 완료되었습니다."),
    UNBOOKMARK_PLACE_OK(HttpStatus.OK, "PLACE2007", "공간 저장(북마크) 해제가 완료되었습니다."),
    BOOKMARK_PLACE_LIST_OK(HttpStatus.OK, "PLACE2004","공간 저장 리스트 조회가 완료되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder().message(message).code(code).isSuccess(true).build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
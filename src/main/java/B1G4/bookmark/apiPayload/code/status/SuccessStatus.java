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

    // 리뷰
    REVIEW_CREATE_OK(HttpStatus.OK, "REVIEW201", "리뷰 등록 성공"),
    REVIEW_FETCH_OK(HttpStatus.OK, "REVIEW200", "리뷰 목록 조회 성공"),
    // 책
    BOOK_FETCH_OK(HttpStatus.OK, "BOOK200", "도서 목록 조회 성공"),
    BOOK_SEARCH_OK(HttpStatus.OK, "BOOK201", "도서 검색 결과 조회가 완료되었습니다."),
    // 장소
    PLACE_CREATE_OK(HttpStatus.OK, "PLACE2001", "공간 등록이 완료되었습니다."),
    PLACE_PREVIEW_OK(HttpStatus.OK, "PLACE2002", "공간 미리보기가 완료되었습니다."),
    PLACE_DETAIL_OK(HttpStatus.OK, "PLACE2003", "공간 상세보기가 완료되었습니다."),
    NEARBY_PLACE_OK(HttpStatus.OK, "PLACE2004","근처 공간 조회가 완료되었습니다."),
    SEARCH_PLACE_OK(HttpStatus.OK, "PLACE2005", "공간 검색 결과 조회가 완료되었습니다."),
    BOOKMARK_PLACE_OK(HttpStatus.OK, "PLACE2006", "공간 저장(북마크)가 완료되었습니다."),
    UNBOOKMARK_PLACE_OK(HttpStatus.OK, "PLACE2007", "공간 저장(북마크) 해제가 완료되었습니다."),

    // 제보 장소
    REPORT_PLACE_CREATE_OK(HttpStatus.OK, "REPORTPLACE201", "공간 제보 성공"),
    // 혼잡도

    CONGESTION_GRAPH_OK(HttpStatus.OK, "CONGESTION200", "혼잡도 그래프 조회 성공"),
    CONGESTION_STATUS_OK(HttpStatus.OK, "CONGESTION201", "현재 혼잡도 상태 값 조회 성공"),
    CONGESTION_FETCH_OK(HttpStatus.OK, "CONGESTION200", "혼잡도 조회 성공"),

    BOOKMARK_PLACE_LIST_OK(HttpStatus.OK, "PLACE2008","공간 저장 리스트 조회가 완료되었습니다."),
    PLACE_RECOMMEND_OK(HttpStatus.OK, "PLACE2009", "추천 공간 리스트 조회가 완료되었습니다."),

    //회원
    USER_LOGIN_OK(HttpStatus.OK, "AUTH2001", "회원 로그인이 완료되었습니다."),
    USER_DELETE_OK(HttpStatus.OK, "AUTH2002", "회원 탈퇴가 완료되었습니다."),
    USER_REFRESH_OK(HttpStatus.OK, "AUTH2003", "토큰 재발급이 완료되었습니다."),
    MYPAGE_OK(HttpStatus.OK, "AUTH2004", "마이페이지 조회가 완료되었습니다."),
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

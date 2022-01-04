package com.study.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserDTO {
    @NotNull
    private String userId; // 사용자 아이디
    private String userName; // 사용자 이름
    private String userPw; // 사용자 비밀번호
    private String authGroupId; // 권한 그룹 아이디
    @Hidden
    private LocalDateTime lastLoginDatetime; // 마지막 로그인 일시
    @Hidden
    private LocalDateTime lastChangePwDatetime; // 마지막 비밀번호 변경일시
    @Hidden
    private Integer wrongPwCnt; // 비밀번호 오류 횟수
    @Hidden
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime registerDatetime; // 등록일시
    @Hidden
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateDatetime; // 수정일시
    @Hidden
    private Boolean useFlag; // 사용여부
}

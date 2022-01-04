package com.study.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MenuDTO {
	private String menuId; // 메뉴 아이디
	private String menuName; // 메뉴 이름
	private String url; // 경로
	private Integer depth; // 메뉴 깊이
	private Integer sort; // 정렬순서
	@Hidden
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime registerDatetime; // 등록일시
	@Hidden
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDatetime; // 수정일시
	@Hidden
	private Boolean useFlag; // 사용여부
	private String parentMenuId; // 부모 메뉴 아이디
	private List<MenuDTO> childMenus; // 자식 메뉴

}

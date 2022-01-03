package com.study.user.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Menu {
	@Id
	private String menuId; // 메뉴 아이디
	private String menuName; // 메뉴 이름
	private String url; // 경로
	private Integer depth; // 메뉴 깊이
	private Integer sort; // 정렬순서
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime registerDatetime; // 등록일시
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updateDatetime; // 수정일시
	private Boolean useFlag = true; // 사용여부

	@ManyToOne
	@JoinColumn(name = "parent_menu_id")
	@JsonIgnore
	private Menu parentMenu; // 부모 메뉴

	@OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Menu> childMenus; // 자식 메뉴

	public void softDelete(){
		this.setUseFlag(false);
	}
}

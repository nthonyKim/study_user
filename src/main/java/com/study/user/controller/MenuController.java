package com.study.user.controller;

import com.study.user.dto.MenuDTO;
import com.study.user.dto.ResponseDTO;
import com.study.user.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="03. Menu", description = "메뉴")
@RequiredArgsConstructor
@RequestMapping("/menu")
@RestController
public class MenuController {
    private final MenuService menuService;

    @Operation(summary  = "메뉴 리스트 조회")
    @GetMapping("/list")
    public List<MenuDTO> findAll(){
        return menuService.findAll();
    }

    @Operation(summary  = "메뉴 단일 조회")
    @GetMapping("/{menuId}")
    public MenuDTO findById(@PathVariable String menuId){
        return menuService.findById(menuId);
    }

    @Operation(summary  = "메뉴 등록")
    @PostMapping
    public ResponseDTO createMenu(@RequestBody MenuDTO menuDTO){
        return menuService.createMenu(menuDTO);
    }

    @Operation(summary  = "메뉴 수정")
    @PutMapping
    public ResponseDTO updateMenu(@RequestBody MenuDTO menuDTO){
        return menuService.updateMenu(menuDTO);
    }

    @Operation(summary  = "메뉴 삭제")
    @DeleteMapping
    public ResponseDTO deleteMenu(@RequestBody MenuDTO menuDTO){
        return menuService.deleteMenu(menuDTO);
    }
}

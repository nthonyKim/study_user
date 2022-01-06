package com.study.user.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResponseDTO {
    private Boolean result;
    private Integer status;
    private String messageKey;
    private Object data;

    public static ResponseDTO fail(String messageKey){
        return ResponseDTO.builder()
                .result(false)
                .status(HttpStatus.OK.value())
                .messageKey(messageKey)
                .build();
    }

    public static ResponseDTO success(String messageKey, Object data){
        return ResponseDTO.builder()
                .result(true)
                .status(HttpStatus.OK.value())
                .messageKey(messageKey)
                .data(data)
                .build();
    }

    public static ResponseDTO success(){
        return ResponseDTO.builder()
                .result(true)
                .status(HttpStatus.OK.value())
                .build();
    }

    public static ResponseDTO success(String messageKey){
        return ResponseDTO.builder()
                .result(true)
                .status(HttpStatus.OK.value())
                .messageKey(messageKey)
                .build();
    }
}

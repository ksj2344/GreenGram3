package com.green.greengram.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class ResultResponse<T> {
    @Schema(title="결과 메세지")
    private String resultMessage;
    @Schema(title="결과 내용")
    private T resultData;
}

package com.swapandgo.sag.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequest {
    @NotNull
    private Long itemId;

    @NotBlank
    private String reason;

    private String description;
}

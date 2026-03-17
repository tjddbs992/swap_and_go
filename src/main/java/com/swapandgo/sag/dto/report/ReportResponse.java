package com.swapandgo.sag.dto.report;

import com.swapandgo.sag.domain.report.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportResponse {
    private Long reportId;
    private Long itemId;
    private Long reporterId;
    private String reason;
    private String description;
    private ReportStatus status;
    private LocalDateTime createdAt;
}

package com.swapandgo.sag.api.report;

import com.swapandgo.sag.domain.report.ReportStatus;
import com.swapandgo.sag.dto.report.AdminReportResponse;
import com.swapandgo.sag.security.user.CustomUserDetails;
import com.swapandgo.sag.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reports")
public class AdminReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<AdminReportResponse>> getReports(
            @RequestParam(value = "status", required = false) ReportStatus status
    ) {
        return ResponseEntity.ok(reportService.getReports(status));
    }

    @PostMapping("/{reportId}/delete-item")
    public ResponseEntity<String> deleteItemAndResolve(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("reportId") Long reportId
    ) {
        reportService.deleteItemAndResolve(userDetails.getUserId(), reportId);
        return ResponseEntity.ok("신고된 게시글을 삭제했습니다.");
    }
}

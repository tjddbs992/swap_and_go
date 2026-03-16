package com.swapandgo.sag.service.report;

import com.swapandgo.sag.domain.item.Item;
import com.swapandgo.sag.domain.report.Report;
import com.swapandgo.sag.domain.report.ReportStatus;
import com.swapandgo.sag.domain.user.User;
import com.swapandgo.sag.dto.report.AdminReportResponse;
import com.swapandgo.sag.dto.report.ReportRequest;
import com.swapandgo.sag.dto.report.ReportResponse;
import com.swapandgo.sag.repository.ItemRepository;
import com.swapandgo.sag.repository.ReportRepository;
import com.swapandgo.sag.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {
    private final ReportRepository reportRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Value("${admin.emails:}")
    private String adminEmails;

    public ReportResponse createReport(Long reporterId, ReportRequest request) {
        User reporter = userRepository.findById(reporterId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(
                () -> new IllegalArgumentException("아이템을 찾을 수 없습니다.")
        );

        Report report = Report.create(reporter, item, request.getReason(), request.getDescription());
        Report saved = reportRepository.save(report);

        return new ReportResponse(
                saved.getId(),
                saved.getItem().getId(),
                saved.getReporter().getId(),
                saved.getReason(),
                saved.getDescription(),
                saved.getStatus(),
                saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<AdminReportResponse> getReports(ReportStatus status) {
        List<Report> reports = (status == null)
                ? reportRepository.findAll()
                : reportRepository.findByStatusOrderByCreatedAtDesc(status);

        return reports.stream()
                .map(r -> new AdminReportResponse(
                        r.getId(),
                        r.getItem().getId(),
                        r.getReporter().getId(),
                        r.getReporter().getEmail(),
                        r.getReason(),
                        r.getDescription(),
                        r.getStatus(),
                        r.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public void deleteItemAndResolve(Long adminUserId, Long reportId) {
        requireAdmin(adminUserId);

        Report report = reportRepository.findById(reportId).orElseThrow(
                () -> new IllegalArgumentException("신고를 찾을 수 없습니다.")
        );

        Item item = report.getItem();
        itemRepository.delete(item);

        User admin = userRepository.findById(adminUserId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        report.resolve(admin);
    }

    private void requireAdmin(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        Set<String> admins = Arrays.stream(adminEmails.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());

        if (admins.isEmpty() || !admins.contains(user.getEmail())) {
            throw new IllegalArgumentException("관리자 권한이 없습니다.");
        }
    }
}

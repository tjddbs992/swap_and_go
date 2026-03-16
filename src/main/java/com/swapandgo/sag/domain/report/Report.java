package com.swapandgo.sag.domain.report;

import com.swapandgo.sag.domain.item.Item;
import com.swapandgo.sag.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false, length = 100)
    private String reason;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by")
    private User resolvedBy;

    public static Report create(User reporter, Item item, String reason, String description) {
        Report report = new Report();
        report.reporter = reporter;
        report.item = item;
        report.reason = reason;
        report.description = description;
        report.status = ReportStatus.PENDING;
        report.createdAt = LocalDateTime.now();
        return report;
    }

    public void resolve(User admin) {
        this.status = ReportStatus.RESOLVED;
        this.resolvedBy = admin;
        this.resolvedAt = LocalDateTime.now();
    }
}

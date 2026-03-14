package com.swapandgo.sag.api.transaction;

import com.swapandgo.sag.dto.transaction.EarlyReturnRequest;
import com.swapandgo.sag.dto.transaction.EarlyReturnResponse;
import com.swapandgo.sag.security.user.CustomUserDetails;
import com.swapandgo.sag.service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/api/transactions/{transactionId}/early-return")
    public ResponseEntity<EarlyReturnResponse> requestEarlyReturn(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long transactionId,
            @RequestBody @Valid EarlyReturnRequest request) {
        Long userId = userDetails.getUserId();
        EarlyReturnResponse response = new EarlyReturnResponse(
                transactionService.requestEarlyReturn(userId, transactionId, request)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/transactions/{transactionId}/early-return")
    public ResponseEntity<EarlyReturnResponse> cancelEarlyReturn(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long transactionId) {
        Long userId = userDetails.getUserId();
        EarlyReturnResponse response = new EarlyReturnResponse(
                transactionService.cancelEarlyReturn(userId, transactionId)
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/transactions/{transactionId}/early-return/accept")
    public ResponseEntity<EarlyReturnResponse> acceptEarlyReturn(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long transactionId) {
        Long userId = userDetails.getUserId();
        EarlyReturnResponse response = new EarlyReturnResponse(
                transactionService.acceptEarlyReturn(userId, transactionId)
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/transactions/{transactionId}/early-return/reject")
    public ResponseEntity<EarlyReturnResponse> rejectEarlyReturn(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long transactionId) {
        Long userId = userDetails.getUserId();
        EarlyReturnResponse response = new EarlyReturnResponse(
                transactionService.rejectEarlyReturn(userId, transactionId)
        );
        return ResponseEntity.ok(response);
    }
}

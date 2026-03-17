package com.swapandgo.sag.dto.transaction;

import com.swapandgo.sag.domain.transaction.EarlyReturnStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EarlyReturnStatusUpdateRequest {
    @NotNull
    private EarlyReturnStatus status;
}

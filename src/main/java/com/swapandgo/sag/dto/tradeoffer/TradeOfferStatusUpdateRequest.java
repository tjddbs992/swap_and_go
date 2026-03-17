package com.swapandgo.sag.dto.tradeoffer;

import com.swapandgo.sag.domain.tradeoffer.TradeOfferStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradeOfferStatusUpdateRequest {
    @NotNull
    private TradeOfferStatus status;
}

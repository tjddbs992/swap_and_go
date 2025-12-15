package com.swapandgo.sag.api.item;

import com.swapandgo.sag.domain.item.Item;
import com.swapandgo.sag.dto.item.ItemCreateRequest;
import com.swapandgo.sag.dto.item.ItemCreateResponse;
import com.swapandgo.sag.security.user.CustomUserDetails;
import com.swapandgo.sag.service.item.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/resale/items")
    public ResponseEntity<ItemCreateResponse> writeResaleItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ItemCreateRequest request){
        Long userId = userDetails.getUserId();
        Long itemId = itemService.saveUsedItem(userId, request);
        ItemCreateResponse response = new ItemCreateResponse(itemId, "중고거래 물품이 등록 되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/rental/items")
    public ResponseEntity<ItemCreateResponse> writeRentalItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ItemCreateRequest request){
        Long userId = userDetails.getUserId();
        Long itemId = itemService.saveRentalItem(userId, request);
        ItemCreateResponse response = new ItemCreateResponse(itemId, "대여 물품이 등록 되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

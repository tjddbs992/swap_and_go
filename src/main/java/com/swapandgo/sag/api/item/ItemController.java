package com.swapandgo.sag.api.item;

import com.swapandgo.sag.dto.item.ItemRequest;
import com.swapandgo.sag.dto.item.ItemResponse;
import com.swapandgo.sag.security.user.CustomUserDetails;
import com.swapandgo.sag.service.item.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ItemResponse> createResaleItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ItemRequest request){
        Long userId = userDetails.getUserId();
        Long itemId = itemService.saveUsedItem(userId, request);
        ItemResponse response = new ItemResponse(itemId, "중고거래 물품이 등록 되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/rental/items")
    public ResponseEntity<ItemResponse> createRentalItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ItemRequest request){
        Long userId = userDetails.getUserId();
        Long itemId = itemService.saveRentalItem(userId, request);
        ItemResponse response = new ItemResponse(itemId, "대여 물품이 등록 되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/resale/items/{itemId}")
    public ResponseEntity<ItemResponse> updateUsedItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemRequest request
            ){
        Long userId = userDetails.getUserId();

        Long updateItemId = itemService.updateUsedItem(userId, itemId, request);
        ItemResponse response = new ItemResponse(updateItemId, "중고 거래 물품 정보가 수정되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/rental/items/{itemId}")
    public ResponseEntity<ItemResponse> updateRentalItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemRequest request
    ){
        Long userId = userDetails.getUserId();

        Long updateItemId = itemService.updateRentalItem(userId, itemId, request);
        ItemResponse response = new ItemResponse(updateItemId, "대여 물품 정보가 수정되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> deleteItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("itemId") Long itemId
    ){
        Long userId = userDetails.getUserId();
        itemService.deleteItem(userId, itemId);
        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }

}

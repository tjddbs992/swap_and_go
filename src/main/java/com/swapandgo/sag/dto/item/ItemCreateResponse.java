package com.swapandgo.sag.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemCreateResponse {
    private Long itemId;
    private String message;
}

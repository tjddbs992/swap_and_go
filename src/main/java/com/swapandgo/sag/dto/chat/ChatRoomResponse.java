package com.swapandgo.sag.dto.chat;

import com.swapandgo.sag.domain.chat.ChatRoom;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomResponse {
    private final Long roomId;
    private final Long itemId;
    private final Long buyerId;
    private final Long sellerId;
    private final LocalDateTime createdAt;

    public ChatRoomResponse(ChatRoom room) {
        this.roomId = room.getId();
        this.itemId = room.getItem().getId();
        this.buyerId = room.getBuyer().getId();
        this.sellerId = room.getSeller().getId();
        this.createdAt = room.getCreatedAt();
    }
}

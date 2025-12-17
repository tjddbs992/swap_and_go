package com.swapandgo.sag.service.item;

import com.swapandgo.sag.domain.item.Item;
import com.swapandgo.sag.domain.item.ItemType;
import com.swapandgo.sag.domain.user.User;
import com.swapandgo.sag.dto.item.ItemRequest;
import com.swapandgo.sag.repository.ItemRepository;
import com.swapandgo.sag.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Long saveUsedItem(Long userId, ItemRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        Item item = Item.create(
                user,
                request.getTitle(),
                request.getContent(),
                request.getPrice(),
                request.getDeposit(),
                ItemType.USED,
                request.getTradeType(),
                request.getCategory(),
                request.getLocation(),
                request.getImages()
        );

        itemRepository.save(item);

        return item.getId();
    }

    public Long saveRentalItem(Long userId, ItemRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        Item item = Item.create(
                user,
                request.getTitle(),
                request.getContent(),
                request.getPrice(),
                request.getDeposit(),
                ItemType.RENTAL,
                request.getTradeType(),
                request.getCategory(),
                request.getLocation(),
                request.getImages()
        );

        itemRepository.save(item);

        return item.getId();
    }

    public Long updateUsedItem(Long userId, Item item){
        Item findItem = itemRepository.findById(item.getId()).orElseThrow(
                () -> new IllegalArgumentException("아이템을 찾을 수 없습니다 : " + item.getId()));
        userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다 : " + userId)
        );
        findItem.update(
                item.getTitle(),
                item.getContent(),
                item.getPrice(),
                item.getDeposit(),
                item.getCategoty(),
                item.getTradeType()
        );

        return findItem.getId();
    }

    public void deleteItem(Item item){
        itemRepository.delete(item);
    }

}

package com.swapandgo.sag.service.item;

import com.swapandgo.sag.domain.item.Item;
import com.swapandgo.sag.domain.item.ItemType;
import com.swapandgo.sag.domain.user.User;
import com.swapandgo.sag.dto.item.ItemCreateRequest;
import com.swapandgo.sag.repository.ItemRepository;
import com.swapandgo.sag.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long saveUsedItem(Long userId, ItemCreateRequest request){
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

    @Transactional
    public Long saveRentalItem(Long userId, ItemCreateRequest request){
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

    @Transactional
    public Long updateItem(Item item){
        Item findItem = itemRepository.findById(item.getId()).orElseThrow(
                () -> new IllegalArgumentException("아이템을 찾을 수 없습니다 : " + item.getId()));
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

    @Transactional
    public void deleteItem(Item item){
        itemRepository.delete(item);
    }

}

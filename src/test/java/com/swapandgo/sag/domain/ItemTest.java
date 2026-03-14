package com.swapandgo.sag.domain;

import com.swapandgo.sag.domain.item.Category;
import com.swapandgo.sag.domain.item.Item;
import com.swapandgo.sag.domain.item.ItemStatus;
import com.swapandgo.sag.domain.item.ItemType;
import com.swapandgo.sag.domain.item.TradeType;
import com.swapandgo.sag.domain.user.Address;
import com.swapandgo.sag.domain.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ItemTest {

    @Test
    void create_setsMainImageAsThumbnail() {
        User user = User.createUser("u", "u@test.com", "pw", new Address("KR", "Seoul", "street"));
        Item item = Item.create(
                user,
                "title",
                "content",
                new BigDecimal("100.00"),
                null,
                ItemType.RESALE,
                TradeType.SELL,
                Category.전자기기,
                "Seoul",
                List.of("a.jpg", "b.jpg")
        );

        assertThat(item.getImages()).hasSize(2);
        assertThat(item.getImages().get(0).isMain()).isTrue();
        assertThat(item.getThumbnailUrl()).isEqualTo("a.jpg");
    }

    @Test
    void completed_setsStatusByDeposit() {
        User user = User.createUser("u", "u@test.com", "pw", null);
        Item resale = Item.create(
                user, "t", "c", new BigDecimal("10.00"),
                null, ItemType.RESALE, TradeType.SELL, Category.전자기기, "Seoul", List.of()
        );
        resale.completed();
        assertThat(resale.getStatus()).isEqualTo(ItemStatus.COMPLETED);

        Item rental = Item.create(
                user, "t", "c", new BigDecimal("10.00"),
                new BigDecimal("5.00"), ItemType.RENTAL, TradeType.SELL, Category.가구, "Seoul", List.of()
        );
        rental.completed();
        assertThat(rental.getStatus()).isEqualTo(ItemStatus.RENTED);
    }

    @Test
    void addTradeOffer_rejectsSelfRequest() {
        User owner = User.createUser("o", "o@test.com", "pw", null);
        Item item = Item.create(
                owner, "t", "c", new BigDecimal("10.00"),
                null, ItemType.RESALE, TradeType.SELL, Category.미용, "Seoul", List.of()
        );

        assertThatThrownBy(() -> item.addTradeOffer(owner, null, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("본인 글");
    }

    @Test
    void addTradeOffer_rentalRequiresPeriod() {
        User owner = User.createUser("o", "o@test.com", "pw", null);
        User requester = User.createUser("r", "r@test.com", "pw", null);
        Item item = Item.create(
                owner, "t", "c", new BigDecimal("10.00"),
                new BigDecimal("5.00"), ItemType.RENTAL, TradeType.SELL, Category.남성의류, "Seoul", List.of()
        );

        assertThatThrownBy(() -> item.addTradeOffer(requester, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("대여 기간");

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        assertThat(item.addTradeOffer(requester, start, end)).isNotNull();
    }
}

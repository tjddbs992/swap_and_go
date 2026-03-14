package com.swapandgo.sag.domain;

import com.swapandgo.sag.domain.user.Address;
import com.swapandgo.sag.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    void updateProfile_updatesOnlyProvidedFields() {
        User user = User.createUser("old", "u@test.com", "oldpw", new Address("KR", "Seoul", "street"));

        user.updateProfile("new", null, new Address("KR", "Busan", "street2"));

        assertThat(user.getUsername()).isEqualTo("new");
        assertThat(user.getPassword()).isEqualTo("oldpw");
        assertThat(user.getAddress().getRegion()).isEqualTo("Busan");
    }
}

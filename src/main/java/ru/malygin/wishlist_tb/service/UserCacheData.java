package ru.malygin.wishlist_tb.service;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UserCacheData {
    private final Long userId;
    private WishlistBotState state = WishlistBotState.START;
    private final Map<String, Object> data = new HashMap<>();

    public void addValue(Object value) {
        if (value != null)
            data.put(this.state.getPath(), value);
    }
}

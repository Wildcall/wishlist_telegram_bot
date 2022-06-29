package ru.malygin.wishlist_tb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.tmbot.cache.Cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheImpl implements Cache {

    private final Map<Long, UserCacheData> usersBotState = new ConcurrentHashMap<>();

    @Override
    public WishlistBotState getBotState(long userId) {
        UserCacheData data = usersBotState.get(userId);
        if (data != null) return data.getState();
        return addUser(userId);
    }

    public WishlistBotState addUser(long userId) {
        UserCacheData data = new UserCacheData(userId);
        usersBotState.put(userId, data);
        return data.getState();
    }

    public void setUsersBotState(long userId,
                                 WishlistBotState state) {
        this.setUsersBotState(userId, state, null);
    }

    public void setUsersBotState(long userId,
                                 WishlistBotState state,
                                 Object value) {
        UserCacheData data = usersBotState.get(userId);
        if (data == null) {
            addUser(userId);
            return;
        }
        data.addValue(value);
        data.setState(state);
    }

    public Optional<UserCacheData> getUserCacheData(long userId) {
        UserCacheData data = usersBotState.get(userId);
        if (data == null) return Optional.empty();
        return Optional.of(data);
    }
}

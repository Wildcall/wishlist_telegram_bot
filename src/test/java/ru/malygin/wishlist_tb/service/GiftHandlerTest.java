package ru.malygin.wishlist_tb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
class GiftHandlerTest {

    private final CacheImpl cache = new CacheImpl();

    @Test
    public void getMethodsName() {
    }

    @Test
    public void getNotNullProperties() {
        Update update = new Update();
        Message message = new Message();
        CallbackQuery callbackQuery = new CallbackQuery();
        update.setMessage(message);
        update.setCallbackQuery(callbackQuery);

        Optional<BotApiObject> first = Arrays
                .stream(update
                                .getClass()
                                .getDeclaredFields())
                .map(f -> {
                    if (BotApiObject.class.isAssignableFrom(f.getType())) {
                        try {
                            if (f.trySetAccessible()) {
                                BotApiObject o = (BotApiObject) f.get(update);
                                if (o != null)
                                    return o;
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst();
    }
}
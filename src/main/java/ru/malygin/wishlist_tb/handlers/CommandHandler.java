package ru.malygin.wishlist_tb.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.malygin.tmbot.ReplyPayload;
import ru.malygin.tmbot.handler.AbstractHandler;
import ru.malygin.tmbot.handler.ProcessUpdateWebHook;
import ru.malygin.wishlist_tb.service.WishlistBotState;
import ru.malygin.wishlist_tb.service.CacheImpl;

@Slf4j
@RequiredArgsConstructor
@Service
public final class CommandHandler extends AbstractHandler {

    private final CacheImpl cache;

    @ProcessUpdateWebHook(type = Message.class, path = "start")
    public ReplyPayload start(Message message) {
        Long chatId = message.getChatId();
        long userId = message
                .getFrom()
                .getId();

        if ("Добавить подарок \uD83C\uDF81".equalsIgnoreCase(message.getText())) {
            cache.setUsersBotState(userId, WishlistBotState.GIFT_NAME);
            SendMessage msg = new SendMessage(chatId.toString(), "Введите название подарка:");
            return new ReplyPayload(msg);
        }
        return null;
    }
}

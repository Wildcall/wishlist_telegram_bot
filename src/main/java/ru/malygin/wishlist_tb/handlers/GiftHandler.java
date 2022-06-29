package ru.malygin.wishlist_tb.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ru.malygin.tmbot.ReplyPayload;
import ru.malygin.tmbot.handler.AbstractHandler;
import ru.malygin.tmbot.handler.ProcessUpdateWebHook;
import ru.malygin.wishlist_tb.model.Gift;
import ru.malygin.wishlist_tb.service.WishlistBotState;
import ru.malygin.wishlist_tb.service.CacheImpl;
import ru.malygin.wishlist_tb.service.KeyboardService;
import ru.malygin.wishlist_tb.service.UserCacheData;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public final class GiftHandler extends AbstractHandler {

    private final CacheImpl cache;
    private final KeyboardService keyboardService;

    @ProcessUpdateWebHook(type = Message.class, path = "name")
    public ReplyPayload name(Message message) {
        long userId = message
                .getFrom()
                .getId();
        String chatId = message
                .getChatId()
                .toString();
        String text = message.getText();
        if (text == null || message.getEntities() != null) return validationError(chatId);
        cache.setUsersBotState(userId, WishlistBotState.GIFT_LINK, text);
        SendMessage message1 = new SendMessage(chatId, "Введите ссылку:");
        message1.setReplyMarkup(keyboardService.getConfirmInlineKeyboard());
        return new ReplyPayload(message1);
    }

    @ProcessUpdateWebHook(type = Message.class, path = "link")
    public ReplyPayload link(Message message) {
        long userId = message
                .getFrom()
                .getId();
        String chatId = message
                .getChatId()
                .toString();
        String url = validateUrl(message);
        if (url != null) {
            cache.setUsersBotState(userId, WishlistBotState.GIFT_DESC, url);
            return new ReplyPayload(new SendMessage(chatId, "Введите описание:"));
        }
        return validationError(chatId);
    }

    @ProcessUpdateWebHook(type = Message.class, path = "description")
    public ReplyPayload description(Message message) {
        Long userId = message
                .getFrom()
                .getId();
        String chatId = message
                .getChatId()
                .toString();
        String text = message.getText();
        if (text == null || message.getEntities() != null) return validationError(chatId);
        cache.setUsersBotState(userId, WishlistBotState.GIFT_PICK, text);
        return new ReplyPayload(new SendMessage(chatId, "Добавьте картинку:"));
    }

    @ProcessUpdateWebHook(type = Message.class, path = "picture")
    public ReplyPayload picture(Message message) {
        long userId = message
                .getFrom()
                .getId();
        String chatId = message
                .getChatId()
                .toString();
        String photoUrl = null;
        List<PhotoSize> photos = message.getPhoto();
        if (photos != null) {
            photoUrl = photos
                    .stream()
                    .max(Comparator.comparingInt(PhotoSize::getFileSize))
                    .map(PhotoSize::getFileId)
                    .orElse(null);
        }

        if (photoUrl != null) {
            cache.setUsersBotState(userId, WishlistBotState.GIFT_SAVE, photoUrl);
            return createConfirmMessage(message);
        }

        return validationError(chatId);
    }

    @ProcessUpdateWebHook(type = Message.class, path = "save")
    public ReplyPayload save(Message message) {
        Long userId = message
                .getFrom()
                .getId();
        Long chatId = message.getChatId();
        String text = message.getText();

        return new ReplyPayload(new SendMessage(chatId.toString(), "Все готово! Проверьте:"));
    }

    private ReplyPayload createConfirmMessage(Message message) {
        String chatId = message
                .getChatId()
                .toString();
        Long userId = message
                .getFrom()
                .getId();

        Optional<UserCacheData> optionalUserCacheData = cache.getUserCacheData(userId);
        if (optionalUserCacheData.isPresent()) {
            Map<String, Object> map = optionalUserCacheData
                    .get()
                    .getData();
            Gift gift = new Gift(map);
            ReplyPayload payload = new ReplyPayload();
            SendPhoto photo = gift.toSendPhoto();
            photo.setChatId(chatId);
            photo.setReplyMarkup(keyboardService.getConfirmInlineKeyboard());
            payload.addPayload(photo);
            return payload;
        }
        return validationError(chatId, "Что-то пошло не так...");
    }

    private String validateUrl(Message message) {
        if (message.getEntities() != null && !message
                .getEntities()
                .isEmpty()) {
            Optional<MessageEntity> url = message
                    .getEntities()
                    .stream()
                    .filter(e -> "url".equalsIgnoreCase(e.getType()))
                    .findFirst();
            if (url.isPresent() && url
                    .get()
                    .getText() != null) {
                return url
                        .get()
                        .getText();
            }
        }
        return null;
    }

    private ReplyPayload validationError(String chatId) {
        return this.validationError(chatId, null);
    }

    private ReplyPayload validationError(String chatId,
                                         String msg) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String text = "Кажется вы ошиблись, введите корректное значение:";
        message.setText(msg == null ? text : msg);
        return new ReplyPayload(message);
    }
}

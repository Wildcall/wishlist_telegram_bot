package ru.malygin.wishlist_tb.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malygin.tmbot.ReplyPayload;
import ru.malygin.tmbot.filter.AbstractFilter;
import ru.malygin.wishlist_tb.service.CacheImpl;
import ru.malygin.wishlist_tb.service.KeyboardService;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommandFilter extends AbstractFilter {

    private final CacheImpl cache;
    private final KeyboardService keyboardService;

    @Override
    public ReplyPayload filter(Update update) {
        Message message = update.getMessage();
        if (message != null) {
            if ("/start".equalsIgnoreCase(message.getText())) {
                cache.addUser(message
                                      .getFrom()
                                      .getId());
                SendMessage msg = new SendMessage();
                msg.setChatId(message.getChatId());
                msg.setText("Что вы хотите сделать?");
                msg.setReplyMarkup(keyboardService.getMainKeyboard());
                return new ReplyPayload(msg);
            }
        }
        return null;
    }
}

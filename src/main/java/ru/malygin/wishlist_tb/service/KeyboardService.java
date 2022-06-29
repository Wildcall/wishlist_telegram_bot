package ru.malygin.wishlist_tb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeyboardService {

    private ReplyKeyboardMarkup mainKeyboard;
    private ReplyKeyboardMarkup confirmKeyboard;
    private InlineKeyboardMarkup confirmInlineKeyboard;

    public ReplyKeyboardMarkup getMainKeyboard() {
        if (this.mainKeyboard == null) {
            KeyboardRow row1 = new KeyboardRow(1);
            row1.add(new KeyboardButton("Добавить подарок \uD83C\uDF81"));
            KeyboardRow row2 = new KeyboardRow(2);
            row2.add(new KeyboardButton("Список подарков \uD83D\uDCC3"));
            row2.add(new KeyboardButton("Поделится списком \uD83D\uDD17"));
            this.mainKeyboard = ReplyKeyboardMarkup
                    .builder()
                    .keyboardRow(row1)
                    .keyboardRow(row2)
                    .build();
        }
        return this.mainKeyboard;
    }

    public ReplyKeyboardMarkup getConfirmKeyboard() {
        if (this.confirmKeyboard == null) {
            KeyboardRow row1 = new KeyboardRow(2);
            row1.add(new KeyboardButton("Отмена ❌"));
            row1.add(new KeyboardButton("Сохранить ✅"));

            this.confirmKeyboard = ReplyKeyboardMarkup
                    .builder()
                    .keyboardRow(row1)
                    .build();
        }
        return this.confirmKeyboard;
    }

    public InlineKeyboardMarkup getConfirmInlineKeyboard() {
        if (this.confirmInlineKeyboard == null) {
            List<InlineKeyboardButton> row1 = new ArrayList<>(2);
            InlineKeyboardButton e = new InlineKeyboardButton("Отмена ❌");
            e.setCallbackData("cancel");
            e.setSwitchInlineQuery("Switch inline query");
            e.setSwitchInlineQueryCurrentChat("Switch inline query current chat");
            row1.add(e);
            InlineKeyboardButton e1 = new InlineKeyboardButton("Сохранить ✅");
            e1.setCallbackData("save");
            row1.add(e1);

            this.confirmInlineKeyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(row1)
                    .build();
        }
        return this.confirmInlineKeyboard;
    }
}

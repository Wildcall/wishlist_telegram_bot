package ru.malygin.wishlist_tb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gift {

    private Long id;
    private String name;
    private String link;
    private String description;
    private String picture;

    public Gift(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.link = (String) map.get("link");
        this.description =(String) map.get("description");
        this.picture = (String) map.get("picture");
    }

    public SendPhoto toSendPhoto() {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(this.picture));
        String str =
                String.format("*%s*\n\n_%s_\n\n[Купить тут](%s)",
                              this.name,
                              this.description,
                              this.link);
        sendPhoto.setCaption(str);
        sendPhoto.setParseMode("MarkdownV2");
        return sendPhoto;
    }
}

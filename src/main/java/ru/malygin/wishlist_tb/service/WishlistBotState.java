package ru.malygin.wishlist_tb.service;

import lombok.Getter;
import ru.malygin.tmbot.cache.BotState;
import ru.malygin.tmbot.handler.AbstractHandler;
import ru.malygin.wishlist_tb.handlers.CommandHandler;
import ru.malygin.wishlist_tb.handlers.GiftHandler;

@Getter
public enum WishlistBotState implements BotState {
    START {
        @Override
        public Class<? extends AbstractHandler> getHandlerClass() {
            return CommandHandler.class;
        }

        @Override
        public String getPath() {
            return "start";
        }
    },
    GIFT_NAME {
        @Override
        public Class<? extends AbstractHandler> getHandlerClass() {
            return GiftHandler.class;
        }

        @Override
        public String getPath() {
            return "name";
        }
    },
    GIFT_DESC {
        @Override
        public Class<? extends AbstractHandler> getHandlerClass() {
            return GiftHandler.class;
        }

        @Override
        public String getPath() {
            return "description";
        }
    },
    GIFT_LINK {
        @Override
        public Class<? extends AbstractHandler> getHandlerClass() {
            return GiftHandler.class;
        }

        @Override
        public String getPath() {
            return "link";
        }
    },
    GIFT_PICK {
        @Override
        public Class<? extends AbstractHandler> getHandlerClass() {
            return GiftHandler.class;
        }

        @Override
        public String getPath() {
            return "picture";
        }
    },
    GIFT_SAVE {
        @Override
        public Class<? extends AbstractHandler> getHandlerClass() {
            return GiftHandler.class;
        }

        @Override
        public String getPath() {
            return "save";
        }
    };
}

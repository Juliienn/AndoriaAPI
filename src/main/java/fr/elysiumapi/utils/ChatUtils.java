package fr.elysiumapi.utils;

import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

public final class ChatUtils {

    public static String getCenteredText(String base)
    {
        StringBuilder builder = new StringBuilder();
        int startPos = getPosToWriteCentered(ChatColor.stripColor(base));

        for(int i = 0; i < startPos; i++)
        {
            builder.append(" ");
        }

        builder.append(base);

        return builder.toString();
    }
    public static int getPosToWriteCentered(String text)
    {
        return (ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH / 2) - (text.length() / 2);
    }

}

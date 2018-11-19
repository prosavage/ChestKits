package net.prosavage.Util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String captializeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static List<String> color(List<String> messages) {
        List<String> coloredMessages = new ArrayList<String>();
        for (String message : messages) {
            coloredMessages.add(ChatColor.translateAlternateColorCodes('&', message));
        }
        return coloredMessages;
    }

    public static List<String> replaceInList(String toReplace, String replaceWith, List<String> messages) {
        List<String> replacedMessages = new ArrayList<String>();
        for (String message : messages) {
            replacedMessages.add(message.replace(toReplace, replaceWith));
        }
        return replacedMessages;
    }

}

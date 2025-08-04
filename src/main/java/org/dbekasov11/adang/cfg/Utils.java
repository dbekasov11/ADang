package org.dbekasov11.adang.cfg;


import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    private static Utils instance;

    public Utils() {
        instance = this;
    }

    public static Utils getInstance() {
        return instance;
    }

    public String translateColors(String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String color = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : color.toCharArray()) {
                replacement.append('§').append(c);
            }
            matcher.appendReplacement(buffer, replacement.toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString().replace("&", "§");
    }

    public static List<String> translateColorCodes(List<String> messages) {
        return messages.stream()
                .map(message -> ChatColor.translateAlternateColorCodes('&', message))
                .collect(Collectors.toList());
    }

    public static String translateRGB(String input) {
        if (input == null) {
            return "";
        }
        input = input.replace("&x", "§x");
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}

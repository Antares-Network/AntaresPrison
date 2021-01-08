package org.piotrwyrw.antares.prison.utils;

import java.util.ArrayList;

public class StringUtil {

    /**
     * Example input
     * Mine | Mine | Mine | Mine
     * @param str
     * @return
     */
    public static String[] fromString(String str) {
        return str.split("\\|");
    }

    /**
     * @param strings
     * @return
     */
    public static String fromList(ArrayList<String> strings) {
        String str = "";
        for (int i = 0; i < strings.size(); i ++) {
            String s = strings.get(i);
            str += s;
            if (i + 1 < strings.size()) {
                str += "|";
            }
        }
        return str;
    }

}

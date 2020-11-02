package org.piotrwyrw.antares.prison.utils;


import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static String toString(ArrayList<Material> list) {
        String str = "";
        for (int i = 0; i < list.size(); i ++)
            str += list.get(i).toString().toUpperCase() + ((i + 1 < list.size()) ? ", " : "");
        return str;
    }

    public static String stringListToString(ArrayList<String> list) {
        String str = "";
        for (int i = 0; i < list.size(); i ++)
            str += list.get(i) + ((i + 1 < list.size()) ? ", " : "");
        return str;
    }

    public static List<String> empty() {
        List<String> str = new ArrayList<>();
        return str;
    }

}

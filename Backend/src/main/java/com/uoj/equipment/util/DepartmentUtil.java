package com.uoj.equipment.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Normalizes department codes across the system.
 *
 * Business rule requested:
 *  - Treat legacy codes "COM" and "CSE" as "CE".
 */
public final class DepartmentUtil {

    private DepartmentUtil() {}

    public static String normalize(String dept) {
        if (dept == null) return null;
        String d = dept.trim().toUpperCase();
        if (d.isEmpty()) return d;
        // Legacy -> new
        if (d.equals("COM") || d.equals("CSE")) return "CE";
        return d;
    }

    /**
     * Returns a list of acceptable aliases for querying legacy data.
     * Example: "CE" -> ["CE","COM","CSE"]
     */
    public static List<String> aliasesForQuery(String dept) {
        String n = normalize(dept);
        if (n == null) return List.of();
        List<String> list = new ArrayList<>();
        list.add(n);
        if ("CE".equals(n)) {
            list.add("COM");
            list.add("CSE");
        }
        return list;
    }

    public static boolean equalsNormalized(String a, String b) {
        String na = normalize(a);
        String nb = normalize(b);
        if (na == null) return nb == null;
        return na.equalsIgnoreCase(nb);
    }
}

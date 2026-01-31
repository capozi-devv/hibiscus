package com.everest.hibiscus.api.modules.compat.internal;

public final class VersionMatcher {

    public static boolean matches(String modVersion, String constraint) {

        if (constraint == null || constraint.isEmpty())
            return true; // any version allowed

        modVersion = normalize(modVersion);
        constraint = constraint.trim();

        // range: 1.0.0–2.0.0
        if (constraint.contains("–")) {
            String[] parts = constraint.split("–");
            return compare(modVersion, normalize(parts[0])) >= 0 &&
                   compare(modVersion, normalize(parts[1])) <= 0;
        }

        // wildcard: 1.2.*
        if (constraint.endsWith(".*")) {
            String base = constraint.substring(0, constraint.length() - 2);
            return modVersion.startsWith(base);
        }

        // relational operator
        if (constraint.startsWith(">="))
            return compare(modVersion, normalize(constraint.substring(2))) >= 0;
        if (constraint.startsWith("<="))
            return compare(modVersion, normalize(constraint.substring(2))) <= 0;
        if (constraint.startsWith(">"))
            return compare(modVersion, normalize(constraint.substring(1))) > 0;
        if (constraint.startsWith("<"))
            return compare(modVersion, normalize(constraint.substring(1))) < 0;

        // exact version
        return modVersion.equals(normalize(constraint));
    }

    private static String normalize(String ver) {
        return ver.trim().replace("v", "").replace("V", "");
    }

    // Compare version like: 1.10.2 vs 1.9.9
    private static int compare(String a, String b) {
        String[] A = a.split("\\.");
        String[] B = b.split("\\.");
        int len = Math.max(A.length, B.length);

        for (int i = 0; i < len; i++) {
            int ai = (i < A.length ? safeInt(A[i]) : 0);
            int bi = (i < B.length ? safeInt(B[i]) : 0);
            if (ai != bi) return ai - bi;
        }
        return 0;
    }

    private static int safeInt(String s) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return 0; }
    }
}

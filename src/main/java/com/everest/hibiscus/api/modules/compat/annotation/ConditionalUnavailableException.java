package com.everest.hibiscus.api.modules.compat.annotation;

public class ConditionalUnavailableException extends RuntimeException {
    public ConditionalUnavailableException(String modid, String reason) {
        super("Attempted to use conditional content requiring mod '" + modid +
                "', but it is NOT loaded. " +
                (reason.isEmpty() ? "" : "Reason: " + reason));
    }

    public ConditionalUnavailableException(String[] mods, String reason) {
        super("Attempted to use conditional content requiring mods: "
                + String.join(", ", mods)
                + ". " + (reason.isEmpty() ? "" : "Reason: " + reason));
    }
}

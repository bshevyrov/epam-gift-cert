package com.epam.esm.util;

import org.apache.commons.lang3.StringUtils;

public final class InputVerification {
    private InputVerification() {
    }

    public static boolean verifyId(long id) {
        return id <= 0;
    }

    public static boolean verifyName(String tagName) {
        return StringUtils.isAlpha(tagName);
    }
}

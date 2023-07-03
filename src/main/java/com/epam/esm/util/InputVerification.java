package com.epam.esm.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Class util. Verify name and id
 */
public final class InputVerification {
    private InputVerification() {
    }

    /**
     * Verify that id bigger than 0
     *
     * @param id value
     * @return true if id bigger than 0
     */
    public static boolean verifyId(long id) {
        return id > 0;
    }

    /**
     * Verify that name contains only alphabet letters.
     *
     * @param name value
     * @return true if name contain only alphabet letters
     */
    public static boolean verifyName(String name) {
        return StringUtils.isAlpha(name);
    }
}

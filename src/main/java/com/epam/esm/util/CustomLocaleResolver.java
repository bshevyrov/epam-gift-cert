package com.epam.esm.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Class checks and sets locale in header.
 */
@Component
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {
    private final List<Locale> locales = Arrays.asList(new Locale("en"), new Locale("uk"));

    /**
     * Method gets locale from request header.
     * Checks if header parameter is empty
     * - if true return en locale
     * - if false return en or uk locale
     *
     * @param request the request to resolve the locale for
     * @return locale object
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getHeader("Accept-Language"))) {
            return new Locale("en", "GB");
        } else {
            List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
            return Locale.lookup(list, locales);
        }
    }
}


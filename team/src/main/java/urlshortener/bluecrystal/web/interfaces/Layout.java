package urlshortener.bluecrystal.web.interfaces;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String NONE = "none"; // no layout will be used
    String SIMPLE = "layouts/simple";
    String DEFAULT = "layouts/default";

    String value();
}
package urlshortener.bluecrystal.web.annotations;

import java.lang.annotation.Annotation;

public class DynamicLayout implements Layout {
    
    private String value;

    public DynamicLayout(String value) {
        this.value = value;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return DynamicLayout.class;
    }

    @Override
    public String value() {
        return value;
    }

}
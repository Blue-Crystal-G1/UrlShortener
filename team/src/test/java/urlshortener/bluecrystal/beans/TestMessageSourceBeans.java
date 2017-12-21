package urlshortener.bluecrystal.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.config.Messages;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMessageSourceBeans {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    protected Messages messages;

    @Test
    public void testMessagesAreGoodRead() {
        assertNotNull(messageSource);
        assertEquals("Test message", messages.get("message.testing"));

        Locale esLocale = new Locale("es", "ES");
        assertEquals("Test message", messageSource.getMessage("message.testing", null, esLocale));
    }
}
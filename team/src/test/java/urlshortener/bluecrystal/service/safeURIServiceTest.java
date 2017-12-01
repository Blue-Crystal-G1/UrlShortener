package urlshortener.bluecrystal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import urlshortener.bluecrystal.config.GoogleSafeBrowsingConfig;
import urlshortener.bluecrystal.config.ServiceConfig;
import urlshortener.bluecrystal.config.propertiesConfig;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes={ServiceConfig.class,GoogleSafeBrowsingConfig.class, propertiesConfig.class},
//        loader=AnnotationConfigContextLoader.class)
//@TestPropertySource(locations="classpath:application.properties")
//@ContextConfiguration(classes={ServiceConfig.class,GoogleSafeBrowsingConfig.class},
//        loader=AnnotationConfigContextLoader.class)
//@ActiveProfiles("main")
public class safeURIServiceTest {

    @Autowired
    protected SafeURIService safeURIService;
    // example threat MALWARE -> http://ianfette.org/
    // another example        -> http://malware.testing.google.test/testing/malware/

    @Test
    public void thatURLContainsMalware() throws Exception {
        String badUrl = "http://malware.testing.google.test/testing/malware/";

        assertFalse(safeURIService.isSafe(badUrl));
        assertTrue(safeURIService.isSafe("http://google.com"));
    }


}

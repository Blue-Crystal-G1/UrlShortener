package urlshortener.bluecrystal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SafeURIServiceTest {

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

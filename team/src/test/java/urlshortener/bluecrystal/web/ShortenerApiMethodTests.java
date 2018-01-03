package urlshortener.bluecrystal.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortenerApiMethodTests {

    @Test
    public void testExtractReferrer() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.REFERER)).thenReturn("http://localhost:8080");
        String referer = ShortenerApiController.extractReferrer(request);
        assertEquals(referer, "localhost");
    }

    @Test
    public void testExtractReferrerReturnsNullIfNoReferrer() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.REFERER)).thenReturn(null);
        String referer = ShortenerApiController.extractReferrer(request);
        assertNull(referer);
    }

    @Test(expected = URISyntaxException.class)
    public void testExtractReferrerReturnsExceptionIfSyntaxIsBad() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.REFERER)).thenThrow(URISyntaxException.class);
        ShortenerApiController.extractReferrer(request);
    }

    @Test
    public void testExtractBrowserReturnsMicrosoftEdge() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10136");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "Microsoft Edge");
    }

    @Test
    public void testExtractBrowserReturnsInternetExplorer() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "Internet Explorer");
    }

    @Test
    public void testExtractBrowserReturnsSafari() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "Safari");
    }

    @Test
    public void testExtractBrowserReturnsOpera() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Desktop's UA string (on Windows) is Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36 OPR/15.0.1147.100");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "Opera");
    }

    @Test
    public void testExtractBrowserReturnsGoogleChrome() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "Google Chrome");
    }

    @Test
    public void testExtractBrowserReturnsNetscape() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/4.79 [fr] (X11; U; Linux 2.4.18-4 i686)");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "Netscape-?");
    }

    @Test
    public void testExtractBrowserReturnsMozillaFirefox() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "Mozilla Firefox");
    }

    @Test
    public void testExtractBrowserReturnsUnKnown() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("NCSA_Mosaic/2.0 (Windows 3.1)");
        String browser = ShortenerApiController.extractBrowser(request);
        assertEquals(browser, "UnKnown");
    }

    @Test
    public void testExtractBrowserReturnsNullIfNoHeaderPresent() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn(null);
        String browser = ShortenerApiController.extractBrowser(request);
        assertNull(browser);
    }

    @Test
    public void testExtractOSReturnsWindows() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, "Windows");
    }

    @Test
    public void testExtractOSReturnsMac() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7");
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, "Mac");
    }

    @Test
    public void testExtractOSReturnsUnix() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; U; UnixWare) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36 OPR/32.0.1948.25");
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, "Unix");
    }

    @Test
    public void testExtractOSReturnsAndroid() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, "Android");
    }

    @Test
    public void testExtractOSReturnsIPhone() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25");
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, "IPhone");
    }

    @Test
    public void testExtractOSReturnsIPad() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0(iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B314 Safari/531.21.10");
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, "IPad");
    }

    @Test
    public void testExtractOSReturnsUnKnown() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Nintendo Switch; ShareApplet) AppleWebKit/601.6 (KHTML, like Gecko) NF/4.0.0.5.9 NintendoBrowser/5.1.0.13341");
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, "UnKnown");
    }

    @Test
    public void testExtractOSReturnsNullIfHeaderNotDefined() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn(null);
        String browser = ShortenerApiController.extractOS(request);
        assertEquals(browser, null);
    }

}

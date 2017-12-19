package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class UrlInfoDTOFixture {

    public static URLInfoDTO urlInfoExample() {
            return new URLInfoDTO("key0","http://google.es","http://bluecrystal/key0", LocalDateTime.now().toString(),
                    123456, LocalDateTime.now().toString(), 1, LocalDateTime.now().toString(),1);
    }

    public static URLInfoDTO urlInfoExample2() {
        return new URLInfoDTO("key1","http://amazon.es","http://bluecrystal/key1", LocalDateTime.now().toString(),
                666, LocalDateTime.now().toString(), 1, LocalDateTime.now().toString(),1);
    }
}

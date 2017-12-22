package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.web.dto.URLInfoDTO;

public class UrlInfoDTOFixture {

    public static URLInfoDTO urlInfoExample() {
            return new URLInfoDTO("key0","http://google.es","http://bluecrystal/key0", "hace 2 minutos",
                    123456, "hace instantes", 1, "hace instantes",1);
    }

    public static URLInfoDTO urlInfoExample2() {
        return new URLInfoDTO("key1","http://amazon.es","http://bluecrystal/key1", "hace 2 minutos",
                666, "hace instantes", 1, "hace instantes",1);
    }
}

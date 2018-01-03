package urlshortener.bluecrystal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HashGeneratorServiceTests {

    @Autowired
    protected HashGenerator hashGenerator;

    //Test that the generated hash is unique

    @Test
    public void thatDifferentUrlGeneratesDifferentHash() throws Exception {
        String url = "http://example.com";
        String url2 = "http://example2.com";
        String owner = "myself";

        assertNotEquals(hashGenerator.generateHash(url, owner),hashGenerator.generateHash(url2,owner));

    }

    @Test
    public void thatDifferentOwnerGeneratesDifferentHash() throws Exception {
        String url = "http://example.com";
        String owner = "myself";
        String owner2 = "yourself";

        assertNotEquals(hashGenerator.generateHash(url, owner),hashGenerator.generateHash(url,owner2));

    }

    @Test
    public void thatSameOwnerAndUrlGeneratesDifferentHash() throws Exception {
        String url = "http://example.com";
        String owner = "myself";

        assertNotEquals(hashGenerator.generateHash(url, owner),hashGenerator.generateHash(url,owner));

    }
}


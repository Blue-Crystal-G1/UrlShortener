package urlshortener.bluecrystal.service;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class HashGenerator {

    public  HashGenerator(){}

    public String generateHash(String url, String owner) {
        return Hashing.murmur3_32().hashString(url + owner + UUID.randomUUID(), StandardCharsets.UTF_8).toString();
    }
}

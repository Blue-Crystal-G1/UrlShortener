package urlshortener.common.safebrowsing;

import com.google.api.services.safebrowsing.model.FindThreatMatchesResponse;

public interface IGoogleSafeBrowsingService {
    FindThreatMatchesResponse checkUrl(String url);
}

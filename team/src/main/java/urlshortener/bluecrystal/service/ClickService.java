package urlshortener.bluecrystal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;

@Service
public class ClickService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClickService.class);

    @Autowired
    private ClickRepository clickRepository;


    public Click save(Click click) {
        if(click != null)
            return clickRepository.save(click);
        else return null;
    }
}

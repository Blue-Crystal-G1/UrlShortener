package urlshortener.bluecrystal.scheduled;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.persistence.dao.SystemCpuRepository;
import urlshortener.bluecrystal.persistence.dao.SystemRamRepository;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CpuAndRamCheckTest {

    @Autowired
    protected CpuAndRamCheck cpuAndRamCheck;

    @Autowired
    protected SystemCpuRepository systemCpuRepository;

    @Autowired
    protected SystemRamRepository systemRamRepository;

    @Test
    public void thatCheckSystemSavesInformation() throws Exception {
        cpuAndRamCheck.checkSystem();
        assertTrue(systemCpuRepository.findAll().size() >= 1);
        assertTrue(systemRamRepository.findAll().size() >= 1);
    }

    @After
    public void finishTest() throws Exception{
        systemCpuRepository.deleteAll();
        systemRamRepository.deleteAll();
    }
}

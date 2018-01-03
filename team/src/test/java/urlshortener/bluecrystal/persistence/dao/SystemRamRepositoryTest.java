package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.model.SystemRamUsage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SystemRamRepositoryTest {

    @Autowired
    protected SystemRamRepository systemRamRepository;

    @Before
    public void startTest() throws Exception{
        systemRamRepository.deleteAll();
    }

    @Test
    public void testSave() throws Exception {
        long count = systemRamRepository.count();
        assertEquals(count,0);

        SystemRamUsage usage = new SystemRamUsage(LocalDateTime.now().atZone(
                ZoneId.systemDefault()).toInstant().toEpochMilli(), 10.12);
        SystemRamUsage usageSaved = systemRamRepository.save(usage);
        count = systemRamRepository.count();
        assertEquals(count,1);
        assertTrue(usageSaved.getUsage() > 0);
        assertTrue(usageSaved.getTime() > 0);
    }

    @Test
    public void testFindByTimeBetweenFindIfIsInRange() throws Exception {
        Long millisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        SystemRamUsage usage = new SystemRamUsage(millisNow, 10.12);
        usage = systemRamRepository.save(usage);
        List<SystemRamUsage> cpuUsageList = systemRamRepository.findByTimeBetween(millisNow - 1, millisNow + 1);
        assertNotNull(cpuUsageList);
        assertEquals(cpuUsageList.size(), 1);
        assertEquals(usage.getTime(), millisNow);
        assertTrue(usage.getUsage() ==  10.12);
    }

    @Test
    public void testFindByTimeBetweenFindIfIsNotInRange() throws Exception {
        Long millisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        SystemRamUsage usage = new SystemRamUsage(millisNow, 10.12);
        systemRamRepository.save(usage);
        List<SystemRamUsage> cpuUsageList = systemRamRepository.findByTimeBetween(millisNow + 1, millisNow + 2);
        assertNotNull(cpuUsageList);
        assertEquals(cpuUsageList.size(), 0);
    }

    @Test
    public void testDelete() throws Exception {
        Long millisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        SystemRamUsage usage = new SystemRamUsage(millisNow, 10.12);
        systemRamRepository.save(usage);
        long count = systemRamRepository.count();
        assertEquals(count, 1);

        systemRamRepository.delete(millisNow);
        count = systemRamRepository.count();
        assertEquals(count, 0);
    }
    
    @After
    public void finishTest() throws Exception{
        systemRamRepository.deleteAll();
    }

}

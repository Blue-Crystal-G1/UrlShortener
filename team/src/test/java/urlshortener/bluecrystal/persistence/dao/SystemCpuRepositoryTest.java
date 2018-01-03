package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.model.SystemCpuUsage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SystemCpuRepositoryTest {

    @Autowired
    protected SystemCpuRepository systemCpuRepository;

    @Before
    public void startTest() throws Exception{
        systemCpuRepository.deleteAll();
    }

    @Test
    public void testSave() throws Exception {
        long count = systemCpuRepository.count();
        assertEquals(count,0);

        SystemCpuUsage usage = new SystemCpuUsage(LocalDateTime.now().atZone(
                ZoneId.systemDefault()).toInstant().toEpochMilli(), 10.12);
        SystemCpuUsage usageSaved = systemCpuRepository.save(usage);
        count = systemCpuRepository.count();
        assertEquals(count,1);
        assertTrue(usageSaved.getMemoryUsage() > 0);
        assertTrue(usageSaved.getTime() > 0);
    }

    @Test
    public void testFindByTimeBetweenFindIfIsInRange() throws Exception {
        Long millisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        SystemCpuUsage usage = new SystemCpuUsage(millisNow, 10.12);
        usage = systemCpuRepository.save(usage);
        List<SystemCpuUsage> cpuUsageList = systemCpuRepository.findByTimeBetween(millisNow - 1, millisNow + 1);
        assertNotNull(cpuUsageList);
        assertEquals(cpuUsageList.size(), 1);
        assertEquals(usage.getTime(), millisNow);
        assertTrue(usage.getMemoryUsage() ==  10.12);
    }

    @Test
    public void testFindByTimeBetweenFindIfIsNotInRange() throws Exception {
        Long millisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        SystemCpuUsage usage = new SystemCpuUsage(millisNow, 10.12);
        systemCpuRepository.save(usage);
        List<SystemCpuUsage> cpuUsageList = systemCpuRepository.findByTimeBetween(millisNow + 1, millisNow + 2);
        assertNotNull(cpuUsageList);
        assertEquals(cpuUsageList.size(), 0);
    }

    @Test
    public void testDelete() throws Exception {
        Long millisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        SystemCpuUsage usage = new SystemCpuUsage(millisNow, 10.12);
        systemCpuRepository.save(usage);
        long count = systemCpuRepository.count();
        assertEquals(count, 1);

        systemCpuRepository.delete(millisNow);
        count = systemCpuRepository.count();
        assertEquals(count, 0);
    }

    @After
    public void finishTest() throws Exception{
        systemCpuRepository.deleteAll();
    }

}

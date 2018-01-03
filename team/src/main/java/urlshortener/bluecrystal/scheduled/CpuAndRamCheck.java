package urlshortener.bluecrystal.scheduled;

import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import urlshortener.bluecrystal.persistence.dao.SystemCpuRepository;
import urlshortener.bluecrystal.persistence.dao.SystemRamRepository;
import urlshortener.bluecrystal.persistence.model.SystemCpuUsage;
import urlshortener.bluecrystal.persistence.model.SystemRamUsage;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class CpuAndRamCheck {
    private final static Logger LOGGER = LoggerFactory.getLogger(CpuAndRamCheck.class);

    @Autowired
    protected SystemCpuRepository systemCpuRepository;

    @Autowired
    protected SystemRamRepository systemRamRepository;

    // Checks every 5 seconds.
    @Scheduled(fixedDelay = 5000L)
    public void checkSystem() {
        LOGGER.info("Check CPU/RAM usage");
        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // What % of memory is used
        double totalPhysicalMemorySize = os.getTotalPhysicalMemorySize();
        double freePhysicalMemorySize = os.getFreePhysicalMemorySize();
        double usedPhysicalMemorySize = totalPhysicalMemorySize - freePhysicalMemorySize;
        double usedPhysicalMemorySizePercentage = Math.round((usedPhysicalMemorySize/totalPhysicalMemorySize)*100);
        BigDecimal bd = new BigDecimal(usedPhysicalMemorySizePercentage);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        SystemRamUsage ramUsage = new SystemRamUsage(System.currentTimeMillis(), bd.doubleValue());
        LOGGER.info("Percentage of RAM used: {} %", ramUsage.getUsage());
        systemRamRepository.save(ramUsage);

        // What % load the overall system is at
        double cpu = os.getSystemCpuLoad()*100;
        bd = new BigDecimal(cpu);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        SystemCpuUsage cpuUsage = new SystemCpuUsage(System.currentTimeMillis(),bd.doubleValue());
        LOGGER.info("Percentage of CPU used: {} %", cpuUsage.getUsage());
        systemCpuRepository.save(cpuUsage);
    }
}
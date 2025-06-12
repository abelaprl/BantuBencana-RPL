package tests;

import com.Dashboard;
import com.LaporanBencanaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardTest {

    @BeforeEach
    public void setUp() {
        // Disable file operations for testing
        Dashboard.setFileOperationsEnabled(false);
        // Clear all data before each test
        Dashboard.clearAllData();
    }

    @AfterEach
    public void tearDown() {
        // Re-enable file operations after testing
        Dashboard.setFileOperationsEnabled(true);
        // Clear test data
        Dashboard.clearAllData();
    }

    @Test
    public void testAddSingleLaporanBencana() {
        // Create test data
        LaporanBencanaData laporan = new LaporanBencanaData(
            "Banjir", 
            "Jakarta", 
            "Banjir besar di Jakarta", 
            "Berat", 
            "10"
        );

        // Add to dashboard
        Dashboard.addLaporanBencana(laporan);

        // Verify
        List<LaporanBencanaData> semuaLaporan = Dashboard.getAllLaporanBencana();
        assertEquals(1, semuaLaporan.size());
        assertEquals("Banjir", semuaLaporan.get(0).getJenis());
        assertEquals("Jakarta", semuaLaporan.get(0).getLokasi());
    }

    @Test
    public void testRemoveLaporanBencanaFromDatabase() {
        // Add test data first
        LaporanBencanaData laporan1 = new LaporanBencanaData("Gempa", "Yogyakarta", "Gempa bumi", "Sedang", "5");
        LaporanBencanaData laporan2 = new LaporanBencanaData("Tsunami", "Aceh", "Tsunami besar", "Sangat Berat", "100");
        
        Dashboard.addLaporanBencana(laporan1);
        Dashboard.addLaporanBencana(laporan2);

        // Verify initial state
        assertEquals(2, Dashboard.getAllLaporanBencana().size());

        // Remove first item
        Dashboard.removeLaporanBencanaFromDatabase(0);

        // Verify removal
        List<LaporanBencanaData> remainingLaporan = Dashboard.getAllLaporanBencana();
        assertEquals(1, remainingLaporan.size());
        assertEquals("Tsunami", remainingLaporan.get(0).getJenis());
    }

    @Test
    public void testGetLaporanBencanaByUser() {
        // Create test data with different users
        LaporanBencanaData laporan1 = new LaporanBencanaData("Banjir", "Jakarta", "Banjir", "Sedang", "5", "user1@test.com");
        LaporanBencanaData laporan2 = new LaporanBencanaData("Gempa", "Bandung", "Gempa", "Berat", "10", "user2@test.com");
        LaporanBencanaData laporan3 = new LaporanBencanaData("Kebakaran", "Surabaya", "Kebakaran", "Ringan", "2", "user1@test.com");

        Dashboard.addLaporanBencana(laporan1);
        Dashboard.addLaporanBencana(laporan2);
        Dashboard.addLaporanBencana(laporan3);

        // Test filtering by user
        List<LaporanBencanaData> user1Reports = Dashboard.getLaporanBencanaByUser("user1@test.com");
        assertEquals(2, user1Reports.size());

        List<LaporanBencanaData> user2Reports = Dashboard.getLaporanBencanaByUser("user2@test.com");
        assertEquals(1, user2Reports.size());
        assertEquals("Gempa", user2Reports.get(0).getJenis());
    }

    @Test
    public void testClearAllData() {
        // Add some test data
        LaporanBencanaData laporan = new LaporanBencanaData("Test", "Test Location", "Test Description", "Sedang", "1");
        Dashboard.addLaporanBencana(laporan);

        // Verify data exists
        assertEquals(1, Dashboard.getAllLaporanBencana().size());

        // Clear all data
        Dashboard.clearAllData();

        // Verify data is cleared
        assertEquals(0, Dashboard.getAllLaporanBencana().size());
    }
}

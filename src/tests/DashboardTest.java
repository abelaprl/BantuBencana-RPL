package tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Dashboard;
import LaporanBencanaData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DashboardTest {

    @BeforeEach
    public void setup() {
        // Reset isi database sebelum tiap tes
        Dashboard.getAllLaporanBencana().clear();
    }

    @Test
    public void testRemoveLaporanBencanaFromDatabase() {
        LaporanBencanaData laporan = new LaporanBencanaData("Gempa", "Padang", "Gempa ringan", "Ringan", "0");
        Dashboard.addLaporanBencana(laporan);
        Dashboard.removeLaporanBencanaFromDatabase(0);

        assertEquals(0, Dashboard.getAllLaporanBencana().size());
    }
}

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
    public void testAddSingleLaporanBencana() {
        LaporanBencanaData laporan = new LaporanBencanaData("Banjir", "Jakarta", "Banjir parah", "Parah", "3");
        Dashboard.addLaporanBencana(laporan);

        List<LaporanBencanaData> semuaLaporan = Dashboard.getAllLaporanBencana();
        assertEquals(1, semuaLaporan.size());
        assertEquals("Banjir", semuaLaporan.get(0).getJenis());
        assertEquals("Jakarta", semuaLaporan.get(0).getLokasi());
}


    @Test
    public void testRemoveLaporanBencanaFromDatabase() {
        LaporanBencanaData laporan = new LaporanBencanaData("Gempa", "Padang", "Gempa ringan", "Ringan", "0");
        Dashboard.addLaporanBencana(laporan);
        Dashboard.removeLaporanBencanaFromDatabase(0);

        assertEquals(0, Dashboard.getAllLaporanBencana().size());
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DashboardTest {

    @BeforeEach
    public void setup() {
        // Reset isi database sebelum tiap tes
        Dashboard.getAllFeedback().clear();
        Dashboard.getAllLaporanBencana().clear();
        Dashboard.getAllDonations().clear();
    }

    // @Test
    // public void testAddFeedback() {
    //     FeedbackData feedback = new FeedbackData("Yasra", "Aplikasi bagus!");
    //     Dashboard.addFeedback(feedback);

    //     List<FeedbackData> allFeedback = Dashboard.getAllFeedback();
    //     assertEquals(1, allFeedback.size());
    //     assertEquals("Yasra", allFeedback.get(0).getName());
    // }

    // @Test
    // public void testAddLaporanBencana() {
    //     LaporanBencanaData laporan = new LaporanBencanaData("Banjir", "Bandung", "Banjir parah", "Berat", "3");
    //     Dashboard.addLaporanBencana(laporan);

    //     List<LaporanBencanaData> allLaporan = Dashboard.getAllLaporanBencana();
    //     assertEquals(1, allLaporan.size());
    //     assertEquals("Bandung", allLaporan.get(0).getLokasi());
    // }

    // @Test
    // public void testAddDonation() {
    //     DonationData donation = new DonationData("Yasra", 200000, "Banjir");
    //     Dashboard.addDonation(donation);

    //     List<DonationData> allDonasi = Dashboard.getAllDonations();
    //     assertEquals(1, allDonasi.size());
    //     assertEquals(200000, allDonasi.get(0).getAmount());
    // }

    // @Test
    // public void testRemoveFeedbackFromDatabase() {
    //     FeedbackData feedback = new FeedbackData("Test", "Hapus saya");
    //     Dashboard.addFeedback(feedback);
    //     Dashboard.removeFeedbackFromDatabase(0);

    //     assertEquals(0, Dashboard.getAllFeedback().size());
    // }

    @Test
    public void testRemoveLaporanBencanaFromDatabase() {
        LaporanBencanaData laporan = new LaporanBencanaData("Gempa", "Padang", "Gempa ringan", "Ringan", "0");
        Dashboard.addLaporanBencana(laporan);
        Dashboard.removeLaporanBencanaFromDatabase(0);

        assertEquals(0, Dashboard.getAllLaporanBencana().size());
    }
}

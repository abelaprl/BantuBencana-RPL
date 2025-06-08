/**
 * Simple test class for Dashboard without JUnit dependencies
 */
public class DashboardTest {
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    
    public static void main(String[] args) {
        System.out.println("Running Dashboard tests...");
        
        // Run each test in sequence
        setUp();
        testAddFeedbackToDatabase();
        tearDown();
        
        setUp();
        testAddLaporanBencanaToDatabase();
        tearDown();
        
        setUp();
        testRemoveFeedbackFromDatabase();
        tearDown();
        
        setUp();
        testRemoveFeedbackFromDatabaseInvalidIndex();
        tearDown();
        
        setUp();
        testRemoveLaporanBencanaFromDatabase();
        tearDown();
        
        setUp();
        testGetCurrentUserEmail();
        tearDown();
        
        setUp();
        testGetAllFeedback();
        tearDown();
        
        setUp();
        testGetAllLaporanBencana();
        tearDown();
        
        setUp();
        testInitializeDummyData();
        tearDown();
        
        System.out.println("\nTest Summary:");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed tests: " + passedTests);
        System.out.println("Failed tests: " + (totalTests - passedTests));
        
        if (passedTests == totalTests) {
            System.out.println("All tests passed! ✅");
        } else {
            System.out.println("Some tests failed! ❌");
        }
    }
    
    private static void setUp() {
        // Clear database before each test
        Dashboard.feedbackDatabase.clear();
        Dashboard.laporanBencanaDatabase.clear();
    }
    
    private static void tearDown() {
        // Clear database after each test
        Dashboard.feedbackDatabase.clear();
        Dashboard.laporanBencanaDatabase.clear();
    }
    
    private static void testAddFeedbackToDatabase() {
        FeedbackData feedback = new FeedbackData();
        feedback.setLaporanBencana("Test Laporan");
        feedback.setJenisBencana("Test Jenis");
        feedback.setLokasiLaporan("Test Lokasi");
        feedback.setUserEmail("test@example.com");
        
        assertEquals("Initial feedback database should be empty", 0, Dashboard.getAllFeedback().size());
        
        Dashboard.addFeedbackToDatabase(feedback);
        
        assertEquals("Feedback database should have one entry", 1, Dashboard.getAllFeedback().size());
        assertEquals("Added feedback should match", feedback, Dashboard.getAllFeedback().get(0));
    }
    
    private static void testAddLaporanBencanaToDatabase() {
        LaporanBencanaData laporan = new LaporanBencanaData("Banjir", "Jakarta", "Test deskripsi", "Sedang", "10");
        
        assertEquals("Initial laporan database should be empty", 0, Dashboard.getAllLaporanBencana().size());
        
        Dashboard.addLaporanBencanaToDatabase(laporan);
        
        assertEquals("Laporan database should have one entry", 1, Dashboard.getAllLaporanBencana().size());
        assertEquals("Added laporan should match", laporan, Dashboard.getAllLaporanBencana().get(0));
    }
    
    private static void testRemoveFeedbackFromDatabase() {
        // Tambah beberapa feedback
        FeedbackData feedback1 = new FeedbackData();
        feedback1.setUserEmail("user1@example.com");
        
        FeedbackData feedback2 = new FeedbackData();
        feedback2.setUserEmail("user2@example.com");
        
        Dashboard.addFeedbackToDatabase(feedback1);
        Dashboard.addFeedbackToDatabase(feedback2);
        
        assertEquals("Feedback database should have two entries", 2, Dashboard.getAllFeedback().size());
        
        // Hapus feedback pertama
        Dashboard.removeFeedbackFromDatabase(0);
        
        assertEquals("Feedback database should have one entry after removal", 1, Dashboard.getAllFeedback().size());
        assertEquals("Remaining feedback should be the second one", "user2@example.com", Dashboard.getAllFeedback().get(0).getUserEmail());
    }
    
    private static void testRemoveFeedbackFromDatabaseInvalidIndex() {
        FeedbackData feedback = new FeedbackData();
        Dashboard.addFeedbackToDatabase(feedback);
        
        assertEquals("Feedback database should have one entry", 1, Dashboard.getAllFeedback().size());
        
        // Test dengan index negatif
        Dashboard.removeFeedbackFromDatabase(-1);
        assertEquals("Negative index should not remove feedback", 1, Dashboard.getAllFeedback().size());
        
        // Test dengan index terlalu besar
        Dashboard.removeFeedbackFromDatabase(10);
        assertEquals("Out of bounds index should not remove feedback", 1, Dashboard.getAllFeedback().size());
    }
    
    private static void testRemoveLaporanBencanaFromDatabase() {
        LaporanBencanaData laporan1 = new LaporanBencanaData("Banjir", "Jakarta", "Deskripsi 1", "Sedang", "10");
        LaporanBencanaData laporan2 = new LaporanBencanaData("Gempa", "Bandung", "Deskripsi 2", "Ringan", "0");
        
        Dashboard.addLaporanBencanaToDatabase(laporan1);
        Dashboard.addLaporanBencanaToDatabase(laporan2);
        
        assertEquals("Laporan database should have two entries", 2, Dashboard.getAllLaporanBencana().size());
        
        Dashboard.removeLaporanBencanaFromDatabase(0);
        
        assertEquals("Laporan database should have one entry after removal", 1, Dashboard.getAllLaporanBencana().size());
        assertEquals("Remaining laporan should be the second one", "Gempa", Dashboard.getAllLaporanBencana().get(0).getJenisBencana());
    }
    
    private static void testGetCurrentUserEmail() {
        String email = Dashboard.getCurrentUserEmail();
        assertNotNull("Current user email should not be null", email);
        assertTrue("Email should contain @example.com", email.contains("@example.com"));
        assertTrue("Email should start with 'user'", email.startsWith("user"));
    }
    
    private static void testGetAllFeedback() {
        java.util.List<FeedbackData> feedbackList = Dashboard.getAllFeedback();
        assertNotNull("Feedback list should not be null", feedbackList);
        
        FeedbackData feedback = new FeedbackData();
        Dashboard.addFeedbackToDatabase(feedback);
        
        assertEquals("Feedback list should have one entry", 1, feedbackList.size());
    }
    
    private static void testGetAllLaporanBencana() {
        java.util.List<LaporanBencanaData> laporanList = Dashboard.getAllLaporanBencana();
        assertNotNull("Laporan list should not be null", laporanList);
        
        LaporanBencanaData laporan = new LaporanBencanaData("Banjir", "Jakarta", "Test", "Sedang", "10");
        Dashboard.addLaporanBencanaToDatabase(laporan);
        
        assertEquals("Laporan list should have one entry", 1, laporanList.size());
    }
    
    private static void testInitializeDummyData() {
        assertEquals("Initial laporan database should be empty", 0, Dashboard.getAllLaporanBencana().size());
        
        Dashboard.initializeDummyData();
        
        assertEquals("Laporan database should have three entries after initialization", 3, Dashboard.getAllLaporanBencana().size());
        
        // Verify dummy data content
        java.util.List<LaporanBencanaData> laporanList = Dashboard.getAllLaporanBencana();
        boolean hasBanjir = false;
        boolean hasGempa = false;
        boolean hasKebakaran = false;
        
        for (LaporanBencanaData laporan : laporanList) {
            if (laporan.getJenisBencana().equals("Banjir")) hasBanjir = true;
            if (laporan.getJenisBencana().equals("Gempa Bumi")) hasGempa = true;
            if (laporan.getJenisBencana().equals("Kebakaran")) hasKebakaran = true;
        }
        
        assertTrue("Laporan list should contain Banjir", hasBanjir);
        assertTrue("Laporan list should contain Gempa Bumi", hasGempa);
        assertTrue("Laporan list should contain Kebakaran", hasKebakaran);
    }
    
    // Helper assertion methods
    private static void assertEquals(String message, int expected, int actual) {
        totalTests++;
        if (expected == actual) {
            passedTests++;
            System.out.println("✅ " + message);
        } else {
            System.out.println("❌ " + message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    private static void assertEquals(String message, String expected, String actual) {
        totalTests++;
        if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
            passedTests++;
            System.out.println("✅ " + message);
        } else {
            System.out.println("❌ " + message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    private static void assertEquals(String message, Object expected, Object actual) {
        totalTests++;
        if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
            passedTests++;
            System.out.println("✅ " + message);
        } else {
            System.out.println("❌ " + message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    private static void assertTrue(String message, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("✅ " + message);
        } else {
            System.out.println("❌ " + message);
        }
    }
    
    private static void assertFalse(String message, boolean condition) {
        totalTests++;
        if (!condition) {
            passedTests++;
            System.out.println("✅ " + message);
        } else {
            System.out.println("❌ " + message);
        }
    }
    
    private static void assertNotNull(String message, Object obj) {
        totalTests++;
        if (obj != null) {
            passedTests++;
            System.out.println("✅ " + message);
        } else {
            System.out.println("❌ " + message);
        }
    }
}
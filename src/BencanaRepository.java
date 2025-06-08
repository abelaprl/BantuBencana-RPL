// src/BencanaRepository.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File; // <--- TAMBAHKAN BARIS INI

public class BencanaRepository {
    // Path ke file teks yang berisi data bencana
    private static final String FILE_PATH = "../bencana.txt";

    public List<Bencana> findAll() {
        List<Bencana> bencanas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Bencana bencana = Bencana.fromString(line);
                if (bencana != null) {
                    bencanas.add(bencana);
                }
            }
        } catch (IOException e) {
            System.err.println("Error membaca file bencana: " + e.getMessage());
            System.out.println("Mencoba membuat file dummy bencana.txt...");
            createDummyDataFile(); // Buat file jika belum ada dengan beberapa data dummy
            // Setelah membuat dummy data, coba baca lagi
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Bencana bencana = Bencana.fromString(line);
                    if (bencana != null) {
                        bencanas.add(bencana);
                    }
                }
            } catch (IOException ex) {
                System.err.println("Error membaca file bencana setelah membuat dummy: " + ex.getMessage());
            }
        }
        return bencanas;
    }

    // Metode untuk membuat file bencana.txt dengan data dummy
    private void createDummyDataFile() {
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(FILE_PATH))) {
            writer.write("Gempa Lombok|Nusa Tenggara Barat|Gempa bumi berkekuatan 6.4 SR mengguncang Lombok.|2018-07-29|bencana_lombok.jpg\n");
            writer.write("Banjir Jakarta|DKI Jakarta|Banjir besar melanda beberapa wilayah ibu kota.|2020-01-01|bencana_jakarta.jpg\n");
            writer.write("Erupsi Gunung Semeru|Jawa Timur|Gunung Semeru erupsi mengeluarkan awan panas guguran.|2021-12-04|bencana_semeru.jpg\n");
            writer.write("Tsunami Aceh|Aceh|Tsunami dahsyat meluluhlantakkan pesisir Aceh.|2004-12-26|bencana_aceh.jpg\n");
            writer.write("Kebakaran Hutan Kalimantan|Kalimantan Tengah|Kebakaran hutan dan lahan menyebabkan kabut asap pekat.|2019-09-01|bencana_kalimantan.jpg\n");
            System.out.println("File bencana.txt dengan data dummy berhasil dibuat di: " + new File(FILE_PATH).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error membuat file dummy bencana: " + e.getMessage());
        }
    }

    // Anda bisa menambahkan metode save(Bencana) atau saveAll(List<Bencana>)
    // jika ingin fitur menambah/mengedit bencana dari aplikasi.
}
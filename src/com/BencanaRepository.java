package com;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BencanaRepository {
    private static final String DATA_FILE = "bencana_data.txt";
    private List<Bencana> bencanaList;

    public BencanaRepository() {
        this.bencanaList = new ArrayList<>();
        loadFromFile();
        
        // Jika file kosong atau tidak ada, inisialisasi dengan data dummy
        if (bencanaList.isEmpty()) {
            initializeDummyData();
        }
    }

    public List<Bencana> findAll() {
        return new ArrayList<>(bencanaList);
    }

    public void save(Bencana bencana) {
        if (bencana != null) {
            bencanaList.add(bencana);
            saveToFile();
        }
    }

    public void update(int index, Bencana bencana) {
        if (index >= 0 && index < bencanaList.size() && bencana != null) {
            bencanaList.set(index, bencana);
            saveToFile();
        }
    }

    public void delete(int index) {
        if (index >= 0 && index < bencanaList.size()) {
            bencanaList.remove(index);
            saveToFile();
        }
    }

    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("DEBUG: File bencana data tidak ditemukan. Akan dibuat saat menyimpan.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Bencana bencana = Bencana.fromString(line);
                    if (bencana != null) {
                        bencanaList.add(bencana);
                    }
                }
            }
            System.out.println("DEBUG: Berhasil memuat " + bencanaList.size() + " data bencana dari file.");
        } catch (IOException e) {
            System.err.println("Error membaca file bencana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Bencana bencana : bencanaList) {
                writer.write(bencana.toString());
                writer.newLine();
            }
            System.out.println("DEBUG: Berhasil menyimpan " + bencanaList.size() + " data bencana ke file.");
        } catch (IOException e) {
            System.err.println("Error menyimpan file bencana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeDummyData() {
        System.out.println("DEBUG: Menginisialisasi data dummy bencana...");
        
        // Data dummy dengan gambar
        Bencana bencana1 = new Bencana(
            "Banjir Jakarta Selatan", 
            "Jakarta Selatan", 
            "2024-01-15", 
            "Banjir besar melanda Jakarta Selatan akibat hujan deras selama 3 hari berturut-turut.",
            "jakarta_banjir.jpg"
        );
        bencana1.addGambar("jakarta_banjir_1.jpg");
        bencana1.addGambar("jakarta_banjir_2.jpg");
        
        Bencana bencana2 = new Bencana(
            "Gempa Lombok", 
            "Lombok, NTB", 
            "2024-01-10", 
            "Gempa berkekuatan 5.2 SR mengguncang Lombok pada pagi hari.",
            "lombok_gempa.jpg"
        );
        bencana2.addGambar("lombok_gempa_1.jpg");
        
        Bencana bencana3 = new Bencana(
            "Kebakaran Hutan Riau", 
            "Riau", 
            "2024-01-08", 
            "Kebakaran hutan dan lahan di Riau menyebabkan kabut asap tebal.",
            "riau_kebakaran.jpg"
        );
        bencana3.addGambar("riau_kebakaran_1.jpg");
        bencana3.addVideo("riau_kebakaran_video.mp4");

        bencanaList.add(bencana1);
        bencanaList.add(bencana2);
        bencanaList.add(bencana3);
        
        saveToFile();
        System.out.println("DEBUG: Data dummy bencana berhasil diinisialisasi.");
    }

    public Bencana findById(int index) {
        if (index >= 0 && index < bencanaList.size()) {
            return bencanaList.get(index);
        }
        return null;
    }

    public int size() {
        return bencanaList.size();
    }

    public void clear() {
        bencanaList.clear();
        saveToFile();
    }
}
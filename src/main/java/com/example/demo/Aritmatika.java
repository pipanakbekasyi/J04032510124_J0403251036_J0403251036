package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.text.DecimalFormat;

@Entity
@Table(name = "operasi_aritmatika")
public class Aritmatika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bilangan_1", nullable = false)
    private Double bilangan1;

    @Column(name = "bilangan_2", nullable = false)
    private Double bilangan2;

    @Column(name = "operator", nullable = false, length = 5)
    private String operator;

    // Konstruktor kosong wajib untuk JPA
    public Aritmatika() {
    }

    // Konstruktor dengan parameter untuk inisialisasi objek aritmatika
    public Aritmatika(Double bilangan1, Double bilangan2, String operator) {
        this.bilangan1 = bilangan1;
        this.bilangan2 = bilangan2;
        this.operator = operator;
    }

    // Fungsi untuk mengambil nilai ID operasi
    public Long getId() {
        return id;
    }

    // Fungsi untuk mengatur nilai ID operasi
    public void setId(Long id) {
        this.id = id;
    }

    // Fungsi untuk mengambil nilai bilangan pertama
    public Double getBilangan1() {
        return bilangan1;
    }

    // Fungsi untuk mengatur nilai bilangan pertama
    public void setBilangan1(Double bilangan1) {
        this.bilangan1 = bilangan1;
    }

    // Fungsi untuk mengambil nilai bilangan kedua
    public Double getBilangan2() {
        return bilangan2;
    }

    // Fungsi untuk mengatur nilai bilangan kedua
    public void setBilangan2(Double bilangan2) {
        this.bilangan2 = bilangan2;
    }

    // Fungsi untuk mengambil simbol operator aritmatika (+, -, x, /)
    public String getOperator() {
        return operator;
    }

    // Fungsi untuk mengatur simbol operator aritmatika (+, -, x, /)
    public void setOperator(String operator) {
        this.operator = operator;
    }

    // Fungsi untuk menghitung ulang hasil operasi bilangan 1 dan bilangan 2 secara dinamis (tidak disimpan di database)
    @Transient
    public Double getResult() {
        if (bilangan1 == null || bilangan2 == null || operator == null) {
            return null;
        }
        switch (operator) {
            case "+":
                return bilangan1 + bilangan2;
            case "-":
                return bilangan1 - bilangan2;
            case "x":
                return bilangan1 * bilangan2;
            case "/":
                if (bilangan2 == 0) {
                    return null; // Pembagian dengan nol tidak terdefinisi
                }
                return bilangan1 / bilangan2;
            default:
                return null;
        }
    }

    // Fungsi bantuan untuk menampilkan angka tanpa desimal .0 jika bernilai bulat
    @Transient
    public String formatAngka(Double nilai) {
        if (nilai == null) {
            return "-";
        }
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(nilai);
    }

    // Fungsi untuk menampilkan hasil perhitungan dalam bentuk teks terformat di tabel dan form
    @Transient
    public String getFormattedResult() {
        if (bilangan1 == null || bilangan2 == null || operator == null) {
            return "";
        }
        if ("/".equals(operator) && bilangan2 == 0) {
            return "Tak Terdefinisi (/ 0)";
        }
        Double res = getResult();
        return res != null ? formatAngka(res) : "";
    }
}

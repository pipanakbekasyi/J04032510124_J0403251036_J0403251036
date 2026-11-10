package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AritmatikaController {

    @Autowired
    private AritmatikaRepository aritmatikaRepository;

    // Fungsi untuk mengarahkan halaman root (/) ke halaman utama CRUD aritmatika
    @GetMapping("/")
    public String redirectKeAritmatika() {
        return "redirect:/aritmatika";
    }

    // Fungsi untuk menampilkan halaman utama aplikasi CRUD Aritmatika (Form dan Tabel dalam 1 halaman)
    @GetMapping("/aritmatika")
    public String tampilkanHalamanAritmatika(Model model) {
        Aritmatika formDefault = new Aritmatika();
        formDefault.setOperator("+");
        model.addAttribute("aritmatika", formDefault);
        model.addAttribute("hasilSaatIni", null);
        model.addAttribute("modeEdit", false);
        model.addAttribute("listAritmatika", aritmatikaRepository.findAllByOrderByIdAsc());
        return "Aritmatika";
    }

    // Fungsi untuk memproses klik tombol "=", menghitung hasil, menyimpan/memperbarui ke database, dan mempertahankan input di form
    @PostMapping("/aritmatika/hitung")
    public String hitungDanSimpan(@ModelAttribute("aritmatika") Aritmatika aritmatika, Model model) {
        boolean sedangEdit = (aritmatika.getId() != null);

        // Simpan data (bilangan 1, bilangan 2, operator) ke database PostgreSQL
        Aritmatika tersimpan = aritmatikaRepository.save(aritmatika);

        // Buat objek untuk mempertahankan nilai bilangan 1, bilangan 2, dan operator agar tidak hilang di form
        Aritmatika formTetap = new Aritmatika();
        if (sedangEdit) {
            // Jika selesai update data yang diedit, pertahankan angka di layar namun reset ID agar klik berikutnya menjadi data baru
            formTetap.setId(null);
        } else {
            formTetap.setId( null );
        }
        formTetap.setBilangan1(tersimpan.getBilangan1());
        formTetap.setBilangan2(tersimpan.getBilangan2());
        formTetap.setOperator(tersimpan.getOperator());

        model.addAttribute("aritmatika", formTetap);
        model.addAttribute("hasilSaatIni", tersimpan.getFormattedResult());
        model.addAttribute("modeEdit", false);
        model.addAttribute("pesanSukses", sedangEdit ? "Data operasi berhasil diperbarui!" : "Operasi berhasil dihitung dan dicatat ke database!");
        model.addAttribute("listAritmatika", aritmatikaRepository.findAllByOrderByIdAsc());

        // Render langsung tanpa redirect agar input bilangan 1 dan bilangan 2 tidak ter-refresh hilang
        return "Aritmatika";
    }

    // Fungsi untuk memuat data operasi dari tabel ke dalam form ketika tombol Edit diklik (Fitur Update CRUD)
    @GetMapping("/aritmatika/edit/{id}")
    public String editData(@PathVariable("id") Long id, Model model) {
        Aritmatika dataEdit = aritmatikaRepository.findById(id).orElse(new Aritmatika());
        model.addAttribute("aritmatika", dataEdit);
        model.addAttribute("hasilSaatIni", dataEdit.getFormattedResult());
        model.addAttribute("modeEdit", true);
        model.addAttribute("listAritmatika", aritmatikaRepository.findAllByOrderByIdAsc());
        return "Aritmatika";
    }

    // Fungsi untuk menghapus satu baris data operasi aritmatika dari database berdasarkan ID (Fitur Delete CRUD)
    @GetMapping("/aritmatika/hapus/{id}")
    public String hapusData(@PathVariable("id") Long id, Model model) {
        if (aritmatikaRepository.existsById(id)) {
            aritmatikaRepository.deleteById(id);
        }
        Aritmatika formDefault = new Aritmatika();
        formDefault.setOperator("+");
        model.addAttribute("aritmatika", formDefault);
        model.addAttribute("hasilSaatIni", null);
        model.addAttribute("modeEdit", false);
        model.addAttribute("pesanSukses", "Data operasi berhasil dihapus dari database.");
        model.addAttribute("listAritmatika", aritmatikaRepository.findAllByOrderByIdAsc());
        return "Aritmatika";
    }
}

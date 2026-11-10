package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AritmatikaRepository extends JpaRepository<Aritmatika, Long> {
    // Fungsi untuk mengambil seluruh riwayat operasi aritmatika secara berurutan berdasarkan ID
    List<Aritmatika> findAllByOrderByIdAsc();
}

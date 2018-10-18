package com.apap.tugas1.repository;

import com.apap.tugas1.model.JabatanPegawaiModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JabatanPegawaiDB
 */
@Repository
public interface JabatanPegawaiDB extends JpaRepository<JabatanPegawaiModel, Long> {
	JabatanPegawaiModel findById(long id);
}
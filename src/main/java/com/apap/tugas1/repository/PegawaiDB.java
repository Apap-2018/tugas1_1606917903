package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

/**
 * PegawaiDB
 */
@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findByNip(String nip);
	PegawaiModel findById(long id);
	PegawaiModel findFirstByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	PegawaiModel findFirstByInstansiOrderByTanggalLahirDesc(InstansiModel instansi);
}

package com.apap.tugas1.repository;

import com.apap.tugas1.model.JabatanModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JabatanDB
 */
@Repository
public interface JabatanDB extends JpaRepository<JabatanModel, Long> {
	JabatanModel findById(long id);
}

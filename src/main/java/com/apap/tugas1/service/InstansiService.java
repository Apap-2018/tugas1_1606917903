package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

/**
 * InstansiService
 * @author nasya
 *
 */
public interface InstansiService {
	void addInstansi(InstansiModel instansi);
	void deleteInstansi(InstansiModel instansi);
	InstansiModel findInstansiById(long id);
	List<InstansiModel> getListInstansi();
	List<InstansiModel> findInstansiByProvinsi(ProvinsiModel provinsi);
}

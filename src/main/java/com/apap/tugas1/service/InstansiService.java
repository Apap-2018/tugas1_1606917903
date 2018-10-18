package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;

/**
 * InstansiService
 * @author nasya
 *
 */
public interface InstansiService {
	void addInstansi(InstansiModel instansi);
	void deleteInstansi(InstansiModel instansi);
	InstansiModel findInstansiById(long id);
	List<InstansiModel> listInstansi();
}

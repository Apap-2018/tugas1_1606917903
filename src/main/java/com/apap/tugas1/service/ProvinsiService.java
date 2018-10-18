package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.ProvinsiModel;

/**
 * ProvinsiService
 * @author nasya
 *
 */
public interface ProvinsiService {
	void addProvinsi(ProvinsiModel provinsi);
	void deleteProvinsi(ProvinsiModel provinsi);
	List<ProvinsiModel> listProvinsi();
}

package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

/**
 * PegawaiService
 * @author nasya
 *
 */
public interface PegawaiService {
	void addPegawai(PegawaiModel Pegawai);
	void deletePegawai(PegawaiModel Pegawai);
	List<PegawaiModel> listPegawai();
	PegawaiModel getPegawaiByNIP(String nip);
	PegawaiModel getPegawaiById(long id);
	PegawaiModel getPegawaiTuaInstansi(InstansiModel instansi);
	PegawaiModel getPegawaiMudaInstansi(InstansiModel instansi);
	void updatePegawai(PegawaiModel Pegawai);
}

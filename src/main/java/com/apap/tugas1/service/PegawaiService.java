package com.apap.tugas1.service;

import java.util.List;

import org.springframework.lang.Nullable;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

/**
 * PegawaiService
 * @author nasya
 *
 */
public interface PegawaiService {
	void addPegawai(PegawaiModel Pegawai);
	void deletePegawai(PegawaiModel Pegawai);
	void updatePegawai(PegawaiModel Pegawai);
	List<PegawaiModel> getListPegawai();
	double countGaji(PegawaiModel pegawai);
	String generateNip(PegawaiModel pegawai);
	PegawaiModel getPegawaiByNIP(String nip);
	PegawaiModel getPegawaiById(long id);
	PegawaiModel getPegawaiTuaInstansi(InstansiModel instansi);
	PegawaiModel getPegawaiMudaInstansi(InstansiModel instansi);
	List<PegawaiModel> filterPegawai(@Nullable ProvinsiModel provinsi, @Nullable InstansiModel instansi, @Nullable JabatanModel jabatan);
	List<PegawaiModel> filterByInstansi(InstansiModel instansi, List<PegawaiModel> listPegawai);
}

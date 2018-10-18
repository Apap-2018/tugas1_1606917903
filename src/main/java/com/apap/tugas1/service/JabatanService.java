package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

/**
 * JabatanService
 * @author nasya
 *
 */
public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel jabatan);
	JabatanModel findJabatanById(long id);
	List<JabatanModel> listJabatan();
}


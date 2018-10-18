package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

/**
 * JabatanServiceImpl
 * @author nasya
 *
 */
@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDB jabatanDb;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
	
	public void updateJabatan(JabatanModel jabatan) {
		JabatanModel jabatanBaru = this.findJabatanById(jabatan.getId());
		System.out.println(jabatan.getDeskripsi());
		jabatanBaru.setDeskripsi(jabatan.getDeskripsi());
		jabatanBaru.setNama(jabatan.getNama());
		jabatanBaru.setGaji_pokok(jabatan.getGaji_pokok());
		jabatanDb.save(jabatanBaru);
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.delete(jabatan);
	}

	@Override
	public JabatanModel findJabatanById(long id) {
		return jabatanDb.findById(id);
	}

	@Override
	public List<JabatanModel> listJabatan() {
		return jabatanDb.findAll();
	}
}
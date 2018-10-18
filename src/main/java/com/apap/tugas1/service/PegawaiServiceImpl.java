package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

/**
 * PegawaiServiceImpl
 * @author nasya
 *
 */
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDB PegawaiDb;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		PegawaiModel pegawaiBaru = new PegawaiModel();
		InstansiModel instansi = instansiService.findInstansiById(pegawai.getInstansi().getId());
		pegawaiBaru.setInstansi(instansi);
		pegawaiBaru.setNama(pegawai.getNama());
		pegawaiBaru.setNip("1234567");
		pegawaiBaru.setTahun_masuk(pegawai.getTahun_masuk());
		pegawaiBaru.setTanggal_lahir(pegawai.getTanggal_lahir());
		pegawaiBaru.setTempat_lahir(pegawai.getTempat_lahir());
		List<JabatanModel> lst = new ArrayList<>();
		pegawaiBaru.setJabatanList(lst);
		for(JabatanModel jabatan : pegawai.getJabatanList()) {
			lst.add(jabatanService.findJabatanById(jabatan.getId()));
		}
		PegawaiDb.save(pegawaiBaru);
	}

	@Override
	public void deletePegawai(PegawaiModel Pegawai) {
		// TODO Auto-generated method stub
		PegawaiDb.delete(Pegawai);
	}

	@Override
	public PegawaiModel getPegawaiByNIP(String nip) {
		return PegawaiDb.findByNip(nip);
	}

	@Override
	public List<PegawaiModel> listPegawai() {
		return PegawaiDb.findAll();
	}
}

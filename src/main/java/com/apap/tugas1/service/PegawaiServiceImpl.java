package com.apap.tugas1.service;

import java.util.Date;
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
		/**PegawaiModel pegawaiBaru = new PegawaiModel();
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
		}**/
		PegawaiDb.save(pegawai);
	}
	
	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel archive = this.getPegawaiById(pegawai.getId());
		archive.setInstansi(pegawai.getInstansi());
		archive.setJabatanList(pegawai.getJabatanList());
		archive.setNama(pegawai.getNama());
		archive.setTahunMasuk(pegawai.getTahunMasuk());
		archive.setTanggalLahir(pegawai.getTanggalLahir());
		archive.setTempatLahir(pegawai.getTempatLahir());
		archive.setNip(pegawai.getNip());
		archive.setId(pegawai.getId());
		PegawaiDb.save(archive);
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
	@Override
	public PegawaiModel getPegawaiTuaInstansi(InstansiModel instansi) {
		return PegawaiDb.findFirstByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public PegawaiModel getPegawaiMudaInstansi(InstansiModel instansi) {
		return PegawaiDb.findFirstByInstansiOrderByTanggalLahirDesc(instansi);
	}

	@Override
	public PegawaiModel getPegawaiById(long id) {
		return PegawaiDb.findById(id);
	}
}

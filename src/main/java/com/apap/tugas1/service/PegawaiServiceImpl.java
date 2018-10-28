package com.apap.tugas1.service;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
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
	public List<PegawaiModel> getListPegawai() {
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

	@Override
	public List<PegawaiModel> filterPegawai(ProvinsiModel provinsi, InstansiModel instansi, JabatanModel jabatan) {
		List<PegawaiModel> result = new ArrayList<>();
		List<PegawaiModel> temp = new ArrayList<>();
		
		if(jabatan != null) {
			temp = jabatan.getPegawaiList();
		}
		else {
			temp = this.getListPegawai();
		}
		
		if(provinsi != null) {
			if(instansi != null) {
				result = this.filterByInstansi(instansi, temp);
			}
			else {
				List<InstansiModel> listInstansi = instansiService.findInstansiByProvinsi(provinsi);
				for(InstansiModel ins : listInstansi) {
					result.addAll(this.filterByInstansi(ins, temp));
					
				}
			}
		}
		else {
			if(instansi != null) {
				result = this.filterByInstansi(instansi, temp);
			}
		}
		return result;
	}
	
	@Override
	public List<PegawaiModel> filterByInstansi (InstansiModel instansi, List<PegawaiModel> listPegawai){
		List<PegawaiModel> result = new ArrayList<PegawaiModel>();
		for (PegawaiModel pegawai : listPegawai) {
			if (pegawai.getInstansi().getId() == instansi.getId())
				result.add(pegawai);
		}
		return result;
	}
	
	@Override
	public double countGaji(PegawaiModel pegawai) {
		double pokok = -1;
		List<JabatanModel> lst = pegawai.getJabatanList();
		for(int i=0; i < lst.size(); i++) {
			if(lst.get(i).getGaji_pokok() > pokok) {
				pokok = lst.get(i).getGaji_pokok();
			}
		}
		return pokok + (pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100 * pokok);
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		DateFormat df = new SimpleDateFormat("ddMMYY");
		Date tglLahir = pegawai.getTanggalLahir();
		String formatted = df.format(tglLahir);
		Long kodeInstansi = pegawai.getInstansi().getId();
		
		int idAkhir = 1;
		List<PegawaiModel> sameDate = new ArrayList<>();
		for (PegawaiModel peg : this.getListPegawai()) {
			if (peg.getTanggalLahir().equals(pegawai.getTanggalLahir()) && peg.getTahunMasuk().equals(pegawai.getTahunMasuk())) {
				sameDate.add(peg);
			}
		}
		if(!sameDate.isEmpty()) {
			String lastNip = sameDate.get(sameDate.size()-1).getNip();
			String lastNumber = lastNip.substring(14,lastNip.length());
			if(lastNumber.startsWith("0")) {
				lastNumber = lastNumber.substring(1, lastNumber.length());
			}
			int lastId = Integer.parseInt(lastNumber);
			idAkhir = lastId+1;
		}
		
		String kodeMasuk = "";
		if (idAkhir<10) {
			kodeMasuk = "0"+idAkhir;
		}
		else {
			kodeMasuk = Integer.toString(idAkhir);
		}
		
		System.out.println(kodeInstansi+formatted+pegawai.getTahunMasuk()+kodeMasuk);
		return kodeInstansi+formatted+pegawai.getTahunMasuk()+kodeMasuk;
	}

}

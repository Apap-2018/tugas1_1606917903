package com.apap.tugas1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

/**
 * PegawaiController
 * @author nasya
 */
@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> lst = jabatanService.listJabatan();
		List<InstansiModel> lst2 = instansiService.listInstansi();
		model.addAttribute("listJabatan", lst);
		model.addAttribute("listInstansi", lst2);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPegawaiByNIP(@RequestParam("nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiByNIP(nip);
		if(archive != null) {
			model.addAttribute("pegawai", archive);
			model.addAttribute("gaji", archive.getGaji());
			return "view-pegawai";
		}
		else {
			model.addAttribute("msg", "Pegawai dengan NIP "+nip+" tidak ditemukan!");
			return "error-page";
		}
	}
	
	@RequestMapping(value="/pegawai/tambah",method = RequestMethod.POST, params= {"addRow"})
	private String addRow (@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList());
		}
		System.out.println(pegawai.getJabatanList().size());
		pegawai.getJabatanList().add(new JabatanModel());
		
		List<JabatanModel> jab = jabatanService.listJabatan();
		List<ProvinsiModel> prov = provinsiService.listProvinsi();
		model.addAttribute("listOfProvinsi", prov);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanList",jab);
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah")
	private String tambahPegawai(Model model) {
		PegawaiModel peg = new PegawaiModel();
		if (peg.getJabatanList()==null) {
			peg.setJabatanList(new ArrayList());
		}
		peg.getJabatanList().add(new JabatanModel());
		List<ProvinsiModel> prov = provinsiService.listProvinsi();
		List<JabatanModel> jab = jabatanService.listJabatan();
		model.addAttribute("jabatanList",jab);
		model.addAttribute("pegawai", peg);
		model.addAttribute("listOfProvinsi", prov);
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="provinsiId") long provinsiId) {
		ProvinsiModel prov = provinsiService.getProvinsiDetailById(provinsiId);
		List<InstansiModel> instansis = instansiService.findInstansiByProvinsi(prov);
		
		return instansis;
	}
	
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		System.out.println(pegawai.getNama());
		System.out.println(pegawai.getTahunMasuk());
		System.out.println(pegawai.getTempatLahir());
		System.out.println(pegawai.getTanggalLahir());
		System.out.println(pegawai.getInstansi().getNama());
		System.out.println("total jabatanku->"+pegawai.getJabatanList().size());
		String nipPegawai = generateNip(pegawai);
		System.out.println(nipPegawai);
		pegawai.setNip(nipPegawai);
		pegawaiService.addPegawai(pegawai);
		String msg = "Pegawai dengan NIP "+nipPegawai+" berhasil ditambahkan";
		model.addAttribute("msg", msg);
		return "success-page";
	}
	
	private String generateNip(PegawaiModel pegawai) {
		DateFormat df = new SimpleDateFormat("ddMMYY");
		Date tglLahir = pegawai.getTanggalLahir();
		System.out.println("hari->"+tglLahir.getDay());
		String formatted = df.format(tglLahir);
		System.out.println("date->"+formatted);
		
		Long kodeInstansi = pegawai.getInstansi().getId();
		System.out.println("kode instansi->"+kodeInstansi);
		
		int idAkhir = 0;
		for (PegawaiModel peg : pegawaiService.listPegawai()) {
			if (peg.getTanggalLahir().equals(pegawai.getTanggalLahir()) && peg.getTahunMasuk().equals(pegawai.getTahunMasuk())) {
				idAkhir+=1;
			}
		}
		idAkhir+=1;
		
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
	
	@RequestMapping(value="/pegawai/ubah",method = RequestMethod.POST, params= {"addRow"})
	private String addRow2 (@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList());
		}
		System.out.println(pegawai.getJabatanList().size());
		pegawai.getJabatanList().add(new JabatanModel());
		
		List<JabatanModel> jab = jabatanService.listJabatan();
		List<ProvinsiModel> prov = provinsiService.listProvinsi();
		model.addAttribute("listOfProvinsi", prov);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanList",jab);
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String updatePegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel peg = pegawaiService.getPegawaiByNIP(nip);
		
		List<ProvinsiModel> prov = provinsiService.listProvinsi();
		List<JabatanModel> jab = jabatanService.listJabatan();
		model.addAttribute("jabatanList",jab);
		model.addAttribute("pegawai", peg);
		model.addAttribute("listOfProvinsi", prov);
		return "updatePegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> cekInstansiUpdate(@RequestParam(value="provinsiId") long provinsiId) {
		ProvinsiModel prov = provinsiService.getProvinsiDetailById(provinsiId);
		List<InstansiModel> instansis = instansiService.findInstansiByProvinsi(prov);
		
		return instansis;
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"submit"})
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		System.out.println(pegawai.getNama());
		System.out.println(pegawai.getTahunMasuk());
		System.out.println(pegawai.getTempatLahir());
		System.out.println(pegawai.getTanggalLahir());
		System.out.println(pegawai.getInstansi().getNama());
		System.out.println("total jabatanku->"+pegawai.getJabatanList().size());
		String nipPegawai = generateNip(pegawai);
		System.out.println(nipPegawai);
		pegawai.setNip(nipPegawai);
		pegawaiService.updatePegawai(pegawai);
		String msg = "Pegawai dengan NIP "+nipPegawai+" berhasil diubah";
		model.addAttribute("msg", msg);
		return "success-page";
	}
	
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewOldYoung(@RequestParam("id") long id, Model model) {
		InstansiModel instansi = instansiService.findInstansiById(id);
		List<PegawaiModel> lst = pegawaiService.listPegawai();
		List<PegawaiModel> pegawaiInstansi = new ArrayList<>();
		for(PegawaiModel pegawai : lst) {
			if(pegawai.getInstansi().equals(instansi)) {
				pegawaiInstansi.add(pegawai);
			}
		}
		PegawaiModel termuda = pegawaiService.getPegawaiMudaInstansi(instansi);
		PegawaiModel tertua = pegawaiService.getPegawaiTuaInstansi(instansi);
		model.addAttribute("termuda", termuda);
		model.addAttribute("tertua", tertua);
		return "tertua-termuda";
	}
}

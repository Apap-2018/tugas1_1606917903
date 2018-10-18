package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.listProvinsi();
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		model.addAttribute("listInstansi", listInstansi);
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatanList(new ArrayList<JabatanModel>());
		pegawai.getJabatanList().add(new JabatanModel());
		pegawai.getJabatanList().add(new JabatanModel());
		pegawai.getJabatanList().add(new JabatanModel());
		pegawai.setInstansi(new InstansiModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("msg", "Pegawai berhasil ditambahkan");
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
		PegawaiModel tertua = Collections.max(pegawaiInstansi);
		PegawaiModel termuda = Collections.min(pegawaiInstansi);
		model.addAttribute("termuda", termuda);
		model.addAttribute("tertua", tertua);
		return "tertua-termuda";
	}
}

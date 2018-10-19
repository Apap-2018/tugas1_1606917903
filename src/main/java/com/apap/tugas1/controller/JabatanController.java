package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;



/**
 * JabatanController
 * @author nasya
 */
@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("msg", "Jabatan "+jabatan.getNama()+" berhasil ditambahkan");
		return "success-page";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam("idJabatan") long id, Model model) {
		JabatanModel archive = jabatanService.findJabatanById(id);
		int jumPegawai = archive.getPegawaiList().size();
		model.addAttribute("jumPegawai", jumPegawai);
		model.addAttribute("jabatan", archive);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value="idJabatan") long id, Model model) {
		JabatanModel archive = jabatanService.findJabatanById(id);
		JabatanModel jabatan = new JabatanModel();
		jabatan.setId(archive.getId());
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("archive", archive);
		return "updateJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.updateJabatan(jabatan);
		model.addAttribute("msg", "Jabatan berhasil diubah");
		return "success-page";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel archive = jabatanService.findJabatanById(jabatan.getId());
		System.out.println(jabatan.getId());
		if(archive.getPegawaiList().isEmpty()) {
			jabatanService.deleteJabatan(archive);
			model.addAttribute("msg", "Jabatan berhasil dihapus");
			return "success-page";
		}
		model.addAttribute("msg", "Jabatan tidak dapat dihapus");
		return "error-page";
	}
	
	@RequestMapping(value = "/jabatan/viewall/list",method = RequestMethod.GET)
	public @ResponseBody List<JabatanModel> listJabatan() {
		return jabatanService.listJabatan();
	}
	
	@RequestMapping(value = "/jabatan/viewall")
	private String viewAllJabatan(Model model) {
		List<JabatanModel> allJabatan = jabatanService.listJabatan();
		model.addAttribute("allJabatan",allJabatan);
		return "ViewAllJabatan.html";
	}
}

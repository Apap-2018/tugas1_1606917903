package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

/**
 * ProvinsiServiceImpl
 * @author nasya
 *
 */
@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDB ProvinsiDb;
	
	@Override
	public void addProvinsi(ProvinsiModel provinsi) {
		ProvinsiDb.save(provinsi);
	}

	@Override
	public void deleteProvinsi(ProvinsiModel Provinsi) {
		// TODO Auto-generated method stub
		ProvinsiDb.delete(Provinsi);
	}

	@Override
	public List<ProvinsiModel> getlistProvinsi() {
		return ProvinsiDb.findAll();
	}

	@Override
	public ProvinsiModel findProvinsiById(long id) {
		return ProvinsiDb.findById(id);
	}
}

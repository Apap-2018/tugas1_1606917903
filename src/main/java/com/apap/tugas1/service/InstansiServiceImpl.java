package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

/**
 * InstansiServiceImpl
 * @author nasya
 *
 */
@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDB instansiDb;
	
	@Override
	public void addInstansi(InstansiModel instansi) {
		instansiDb.save(instansi);
	}

	@Override
	public void deleteInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		instansiDb.delete(instansi);
	}

	@Override
	public List<InstansiModel> getListInstansi() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel findInstansiById(long id) {
		return instansiDb.findById(id);
	}
	
	@Override
	public List<InstansiModel> findInstansiByProvinsi(ProvinsiModel provinsi) {
		return instansiDb.findByProvinsi(provinsi);
	}
}

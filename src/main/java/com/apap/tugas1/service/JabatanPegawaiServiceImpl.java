package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;

/**
 * InstansiServiceImpl
 * @author nasya
 *
 */
@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService{
	@Autowired
	private JabatanPegawaiDB JabatanPegawaiDb;
	
	@Override
	public void addJabatanPegawai(JabatanPegawaiModel JabatanPegawai) {
		JabatanPegawaiDb.save(JabatanPegawai);
	}

	@Override
	public void deleteJabatanPegawai(JabatanPegawaiModel JabatanPegawai) {
		// TODO Auto-generated method stub
		JabatanPegawaiDb.delete(JabatanPegawai);
	}
}

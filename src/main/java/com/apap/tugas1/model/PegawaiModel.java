package com.apap.tugas1.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PegawaiModel
 */
@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable, Comparable<PegawaiModel>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "NIP", nullable = false, unique = true)
	private String nip;

	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempat_lahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggal_lahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahun_masuk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade= {
					CascadeType.PERSIST, CascadeType.MERGE
			})
	@JoinTable(name = "jabatan_pegawai",
			joinColumns = { @JoinColumn(name = "id_pegawai") },
			inverseJoinColumns = {@JoinColumn(name = "id_jabatan") })
	private List<JabatanModel> jabatanList;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempat_lahir() {
		return tempat_lahir;
	}

	public void setTempat_lahir(String tempat_lahir) {
		this.tempat_lahir = tempat_lahir;
	}

	public Date getTanggal_lahir() {
		return tanggal_lahir;
	}

	public void setTanggal_lahir(Date tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}

	public String getTahun_masuk() {
		return tahun_masuk;
	}

	public void setTahun_masuk(String tahun_masuk) {
		this.tahun_masuk = tahun_masuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}
	
	public List<JabatanModel> getJabatanList() {
		return jabatanList;
	}

	public void setJabatanList(List<JabatanModel> jabatanList) {
		this.jabatanList = jabatanList;
	}
	
	public double getGaji() {
		double pokok = -1;
		List<JabatanModel> lst = this.getJabatanList();
		for(int i=0; i < lst.size(); i++) {
			if(lst.get(i).getGaji_pokok() > pokok) {
				pokok = lst.get(i).getGaji_pokok();
			}
		}
		return pokok + (this.getInstansi().getProvinsi().getPresentase_tunjangan()/100 * pokok);
	}
	
	public int getUmur() {
		  Date now = new Date();
		  DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		  int d1 = Integer.parseInt(formatter.format(tanggal_lahir));
		  int d2 = Integer.parseInt(formatter.format(now));
		  int age = (d2 - d1) / 10000;   
		  return age;
	}

	@Override
	public int compareTo(PegawaiModel other) {
		int otherPegawai = other.getUmur();
		return this.getUmur() - otherPegawai;
	}
}

package com.lelasoft.dao;

import java.util.List;

import com.lelasoft.model.HospitalTeam;
import com.lelasoft.tools.Hospitals;

public interface HospitalTeamDao {

	public HospitalTeam findById(Long id);

	public HospitalTeam save(HospitalTeam obj);
	
	public HospitalTeam update(HospitalTeam obj);

	public HospitalTeam remove(Long id);

	public List<HospitalTeam> findAll();
	
	public List<HospitalTeam> findAllByHospital(Hospitals hospital);
}

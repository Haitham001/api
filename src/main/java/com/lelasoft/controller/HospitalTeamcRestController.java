package com.lelasoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lelasoft.dao.HospitalTeamDao;
import com.lelasoft.model.HospitalTeam;
import com.lelasoft.tools.BaseResponse;

@Controller
@RequestMapping("/rest/team")
public class HospitalTeamcRestController {
	@Autowired
	private HospitalTeamDao teamDao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<HospitalTeam>> gethospitalTeam() {
		BaseResponse<List<HospitalTeam>> response = new BaseResponse<List<HospitalTeam>>();
		try {
			List<HospitalTeam> hospitalTeam = teamDao.findAll();
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of hospitalTeam!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(hospitalTeam);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load hospitalTeam, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<HospitalTeam> updatehospitalTeam(
			@RequestBody HospitalTeam team) {
		BaseResponse<HospitalTeam> response = new BaseResponse<HospitalTeam>();
		try {
			team = teamDao.update(team);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage(team.getMember().getUsername() + " has been saved successfully");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(team);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't add hospitalTeam,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody BaseResponse<HospitalTeam> removeHospitalTeamById(
			@PathVariable("id") Long id) {
		BaseResponse<HospitalTeam> response = new BaseResponse<HospitalTeam>();
		try {
			HospitalTeam mt = teamDao.remove(id);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Hospital team has been deleted!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(mt);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't delete hospitalTeam!, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
}
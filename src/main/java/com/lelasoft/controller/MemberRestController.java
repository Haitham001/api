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
import com.lelasoft.dao.MemberDao;
import com.lelasoft.model.HospitalTeam;
import com.lelasoft.model.Member;
import com.lelasoft.tools.BaseResponse;
import com.lelasoft.tools.Membership;

@Controller
@RequestMapping("/rest/members")
public class MemberRestController {
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private HospitalTeamDao teamDao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<Member>> listAllMembers() {
		BaseResponse<List<Member>> response = new BaseResponse<List<Member>>();
		try {
			List<Member> members = memberDao.findAllOrderedByName();
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of users.");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(members);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't return user list..> " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<Member> lookupMemberById(
			@PathVariable("id") Long id) {
		BaseResponse<Member> response = new BaseResponse<Member>();
		try {
			Member member = memberDao.findById(id);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of users.");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(member);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't return user list..> " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
	
	
	@RequestMapping(value = "/team/{teamId}",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<Member> getMemberByTeam(
			@PathVariable Long teamId) {
		BaseResponse<Member> response = new BaseResponse<Member>();
		try {
			Member member = memberDao.findByTeam(teamDao.findById(teamId));
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of users.");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(member);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't get  member,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{username}/{password}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<Member> login(
			@PathVariable("username") String username,
			@PathVariable("password") String password) {
		BaseResponse<Member> response = new BaseResponse<Member>();
		try {
			Member member = memberDao.login(username, password);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Login successfully, Welcome "
					+ member.getUsername());
			head.setPrint(true);
			head.setStatus(1);
			response.setData(member);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Login failed, Check your username & password, "
					+ e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<Member> registerNewMember(
			@RequestBody Member member) {
		BaseResponse<Member> response = new BaseResponse<Member>();
		try {
			memberDao.register(member);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage(member.getUsername()
					+ " has been saved successfully");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(member);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't add member,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/team", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<Member> registerHospitalTeam(
			@RequestBody Member member) {
		BaseResponse<Member> response = new BaseResponse<Member>();
		try {
			HospitalTeam team = member.getHospitalTeam();
			member.setHospitalTeam(null);
			member.setMembership(Membership.USER);
			memberDao.updateMember(member);
			if (team != null){
				teamDao.remove(team.getId());
			}
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage(member.getUsername()
					+ " has been saved successfully");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(member);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't add member,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<Member> updateMember(
			@RequestBody Member member) {
		BaseResponse<Member> response = new BaseResponse<Member>();
		try {
			memberDao.updateMember(member);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Member token has been saved successfully");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(member);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't add member,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody BaseResponse<Member> removeMemberById(
			@PathVariable("id") Long id) {
		BaseResponse<Member> response = new BaseResponse<Member>();
		try {
			Member member = memberDao.remove(id);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Member" + member.getUsername()
					+ " has been deleted!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(member);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't delete member! " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
}
package com.lelasoft.dao;

import java.util.List;

import com.lelasoft.model.HospitalTeam;
import com.lelasoft.model.Member;

public interface MemberDao
{
    public Member findById(Long id);
    
	public Member findByTeam(HospitalTeam team);  

    public Member findByEmail(String email);

    public List<Member> findAllOrderedByName();

    public void register(Member member);
    
    public void updateMember(Member member);
    
    public Member remove(Long id);

    public Member login(String email, String password);

}

package com.krislq.register.dao;

import java.util.ArrayList;
import java.util.List;

import com.krislq.register.bean.Member;

public class MemberDao {
    public List<Member> getMembers(){
        List<Member> members = new ArrayList<Member>();
        members.add(new Member("Kris", "kris"));
        members.add(new Member("Kris2", "kris"));
        members.add(new Member("Kris3", "kris"));
        members.add(new Member("Kris4", "kris"));
        members.add(new Member("Kris5", "kris"));
        members.add(new Member("Kris6", "kris"));
        members.add(new Member("Kris7", "kris"));
        members.add(new Member("Kris8", "kris"));
        members.add(new Member("Kris9", "kris"));
        members.add(new Member("Kris10", "kris"));
        members.add(new Member("Kris11", "kris"));
        members.add(new Member("Kris12", "kris"));
        members.add(new Member("Kris17", "kris"));
        members.add(new Member("Kris18", "kris"));
        members.add(new Member("Kris19", "kris"));
        members.add(new Member("Kris20", "kris"));
        members.add(new Member("Kris21", "kris"));
        members.add(new Member("Kris32", "kris"));
        return members;
    }
}

package com.krislq.register.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.krislq.register.bean.Member;

public class MemberDao {
    public List<Member> getMembers(){
        List<Member> members = new ArrayList<Member>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("members"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] memberContents = line.trim().split(",");
                if(memberContents.length > 1) {
                    Member member = new Member(memberContents[1], memberContents[0]);
                    members.add(member);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if(reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return members;
    }
}

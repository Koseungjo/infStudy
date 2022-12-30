package com.inflearn.demo.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<Users> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new Users(1,"rhtmdwh1",new Date(),"pass1","980420-1111111"));
        users.add(new Users(2,"rhtmdwh2",new Date(),"pass2","980420-2222222"));
        users.add(new Users(3,"rhtmdwh3",new Date(),"pass3","980420-3333333"));
    }

    public List<Users> findAll(){
        return users;
    }

    public Users save(Users users){
        if (users.getId() == null){
            users.setId(++usersCount);
            UserDaoService.users.add(users);
        }
        return users;
    }

    public Users findOne(int id){
        for (Users users : UserDaoService.users){
            if(users.getId() == id){
                return users;
            }
        }
        return null;
    }

    public Users deleteById(int id){
        Iterator<Users> iterator = users.iterator();

        while (iterator.hasNext()){
            Users users = iterator.next();

            if (users.getId() == id){
                iterator.remove();
                return users;
            }
        }

        return null;
    }
}


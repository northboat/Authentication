package cia.northboat.auth.service;

import cia.northboat.auth.dao.FriendRepository;
import cia.northboat.auth.pojo.Friend;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class FriendService {
    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }


    public Friend find(String name){
        return friendRepository.findByName(name);
    }

    // 获取所有按 lastTime 升序的数据
    public List<Friend> getAllFriendsByLastTime() {
        return friendRepository.findAllByOrderByLastTimeAsc();
    }


    // 保存 Friend 实体
    public void saveFriend(String name, String pin) {
        Friend friend = new Friend(name, pin);
        friendRepository.save(friend);
    }


    public int record(String name, String pin){
        Friend friend = friendRepository.findByName(name);
        if(Objects.isNull(friend)){
            saveFriend(name, pin);
            return 1;
        }
        if(!friend.getPin().equals(pin)){
            return 0;
        }
        friend.setLastTime(new Date());
        friend.setLongest();
        friendRepository.save(friend);
        return 2;
    }
}
package cia.northboat.auth.pojo;

import cia.northboat.auth.utils.DateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Friend {
    @Id
    String name;
    String pin;
    Date lastTime;
    int longest;

    public Friend(String name, String pin){
        this.name = name;
        this.pin = pin;
        lastTime = new Date();
        longest = 0;
    }

    public void setLongest(){
        int gap = DateUtil.getDayGap(this.lastTime);
        this.longest = Math.max(gap, longest);
    }
}
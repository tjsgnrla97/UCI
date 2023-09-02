package com.cos.jwt.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeResponse implements Comparable<TimeResponse>{
    Long userSeq;
    String username;
    Long hour;
    Long min;
    Long sec;

    public static TimeResponse res(long diff, long userSeq, String username){
        TimeResponse time = new TimeResponse();
        diff = Math.abs(diff);

        time.setHour(diff/3600);
        time.setMin(diff%3600/60);
        time.setSec(diff%3600%60);

        time.setUserSeq(userSeq);
        time.setUsername(username);

        return time;
    }

    @Override
    public String toString() {
        return "TimeResponse{" +
                "userSeq=" + userSeq +
                ", username='" + username + '\'' +
                ", hour=" + hour +
                ", min=" + min +
                ", sec=" + sec +
                '}';
    }

    @Override
    public int compareTo(TimeResponse timeResponse) {
        if (this.hour > timeResponse.hour)
        {
            return 1;
        }
        else if(this.hour == timeResponse.hour)
        {
            if(this.min > timeResponse.min)
            {
                return 1;
            }
            else if(this.min == timeResponse.min)
            {
                if(this.sec >= timeResponse.sec)
                {
                    return 1;
                }
                else
                    return -1;
            }
            else
                return -1;
        }
        else
            return -1;
    }
}

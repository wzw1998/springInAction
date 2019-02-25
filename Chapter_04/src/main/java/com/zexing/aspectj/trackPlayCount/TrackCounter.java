package com.zexing.aspectj.trackPlayCount;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class TrackCounter {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    // execution(...play(int))的 int 是被通知的方法的获得的参数的类型
    // 通过 && args(trackNumber) 表示被通知方法的实参也将传递给通知方法
    @Pointcut("execution(* com.zexing.aspectj.trackPlayCount.CDPlayer.play(int)) && args(trackNumber)")
    public void pointcut(int trackNumber) { // 形参名必须和 args()一致
    }

    // @Around("pointcut(trackNumber)")中的 "trackNumber"
    // 不必与 args() 相同 ，可以另外命名的，但必须保证本通知内一致即可。
    @Around("pointcut(trackNumber)")
    public void countTrack(ProceedingJoinPoint pjp, int trackNumber) {
        try {
            pjp.proceed(); //调用被通知方法
            // 每次调用被通知方法成功之后，音轨的播放次数+1
            int currentCount = getTrackCurrentCount(trackNumber);
            map.put(trackNumber, ++currentCount);
        } catch (Throwable e) {
            // 调用出现异常后的代码
            System.out.println("CDPlayer 播放异常！");
        }
    }

    public int getTrackCurrentCount(int trackNumber) {
        return map.containsKey(trackNumber) ? map.get(trackNumber) : 0;
    }
}

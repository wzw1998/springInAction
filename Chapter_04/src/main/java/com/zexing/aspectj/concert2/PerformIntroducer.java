package com.zexing.aspectj.concert2;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformIntroducer {
    /**
     *
     * Created by Joson on 2/26/2019.
     */
    //通过 PerformIntroducer 的介绍，潮剧表演者进入音乐会进行加场表演
    @DeclareParents(value = "com.zexing.aspectj.concert.Performance+",defaultImpl = OnePerformer.class )
    public static Performer performer;
}

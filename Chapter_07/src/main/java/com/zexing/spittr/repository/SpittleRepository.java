package com.zexing.spittr.repository;

import com.zexing.spittr.bean.Spittle;

import java.util.List;

public interface SpittleRepository {
    List<Spittle> findSpittles(Long max, int count);

    Spittle findOneSpittle(long spittleId);
}

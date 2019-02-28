package com.zexing.spittr.repository;

import com.zexing.spittr.bean.Spitter;

public interface SpittlerRepository {
    Spitter save(Spitter spitter);

    Spitter findSpittlerByUserName(String userName);
}

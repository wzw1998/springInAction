package com.zexing.spittr.repository;

import com.zexing.spittr.bean.Spitter;

public interface SpitterRepository {
    Spitter save(Spitter spitter);

    Spitter findSpitterByUserName(String userName);
}

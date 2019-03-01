package com.zexing.spittr.repository;

import com.zexing.spittr.bean.Spitter;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {

    /**
     *
     * Created by Joson on 2/28/2019.
     */
    @Override
    public Spitter save(Spitter spitter) {
        return null;
    }

    @Override
    public Spitter findSpitterByUserName(String userName) {
        return null;
    }
}

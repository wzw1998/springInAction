package com.zexing.spittr.repository;

import com.zexing.spittr.bean.Spittle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcSpittleRepository implements SpittleRepository {

    /**
     *
     * Created by Joson on 2/27/2019.
     */
    @Override
    public List<Spittle> findSpittles(Long max, int count) {
        return null;
    }

    @Override
    public Spittle findOneSpittle(long spittleId) {
        return null;
    }

}

package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserAuthInfo;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthInfoRepository extends CrudRepository<UserAuthInfo, Long> {

    @Query("select * from user_auth_info\n" +
            "where user_id=:userId\n" +
            "order by last_update_time desc\n" +
            "limit :pos,:size;")
    List<UserAuthInfo> findWithPaging(Long userId, Integer pos, Integer size);

    @Query("select count(*) from user_auth_info\n" +
            "where user_id=:userId;")
    Long countQualified(Long userId);

    List<UserAuthInfo> findByAuthCode(Long authCode);
}

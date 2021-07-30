package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying
    @Query("update user u set u.status=:status where u.id=:id;")
    void updateStatusById(Long id, Integer status);

    @Query("select * from user\n" +
            "where (:mobile is null or mobile like concat('%',:mobile,'%'))\n" +
            "and (:username is null or username like concat('%',:username,'%'))\n" +
            "and (:realName is null or real_name like concat('%',:realName,'%'))\n" +
            "and (:userId is null or id=:userId)\n" +
            "and (:status is null or status=:status)\n" +
            "order by last_update_time\n" +
            "limit :pos,:size;")
    List<User> findWithPaging(String mobile, String username, String realName, Long userId, Byte status, Integer pos, Integer size);

    @Query("select count(*) from user\n" +
            "where (:mobile is null or mobile like concat('%',:mobile,'%'))\n" +
            "and (:username is null or username like concat('%',:username,'%'))\n" +
            "and (:realName is null or real_name like concat('%',:realName,'%'))\n" +
            "and (:userId is null or id=:userId)\n" +
            "and (:status is null or status=:status);")
    Long countQualified(String mobile, String username, String realName, Long userId, Byte status);
}

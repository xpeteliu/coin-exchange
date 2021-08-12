package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserBank;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBankRepository extends CrudRepository<UserBank, Long> {

    @Modifying
    @Query("update user_bank ub set ub.status=:status where ub.id=:id;")
    void updateStatusById(Long id, Integer status);

    @Query("select * from user_bank\n" +
            "where user_id=:userId\n" +
            "order by last_update_time desc\n" +
            "limit :pos,:size;")
    List<UserBank> findWithPaging(Long userId, Integer pos, Integer size);

    @Query("select count(*) from user_bank\n" +
            "where user_id=:userId;")
    Long countQualified(Long userId);

    @Query("select * from user_bank ub where ub.user_id=:userId and ub.status=1 limit 1;")
    Optional<UserBank> findOneAvailableByUserId(Long userId);
}

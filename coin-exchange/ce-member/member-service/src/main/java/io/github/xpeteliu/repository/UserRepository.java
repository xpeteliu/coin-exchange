package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying
    @Query("update user u set u.status=:status where u.id=:id;")
    void updateStatusById(Long id, Integer status);

    @Modifying
    @Query("update user u set u.reviews_status=:status where u.id=:id;")
    void updateReviewStatusById(Long id, Integer reviewStatus);

    @Query("select * from user\n" +
            "where (:mobile is null or mobile like concat('%',:mobile,'%'))\n" +
            "and (:username is null or username like concat('%',:username,'%'))\n" +
            "and (:realName is null or real_name like concat('%',:realName,'%'))\n" +
            "and (:userId is null or id=:userId)\n" +
            "and (:status is null or status=:status)\n" +
            "and (:reviewStatus is null or reviews_status=:reviewStatus)\n" +
            "order by last_update_time desc\n" +
            "limit :pos,:size;")
    List<User> findWithPaging(String mobile, String username, String realName, Long userId, Byte status, Integer reviewStatus, Integer pos, Integer size);

    @Query("select count(*) from user\n" +
            "where (:mobile is null or mobile like concat('%',:mobile,'%'))\n" +
            "and (:username is null or username like concat('%',:username,'%'))\n" +
            "and (:realName is null or real_name like concat('%',:realName,'%'))\n" +
            "and (:userId is null or id=:userId)\n" +
            "and (:status is null or status=:status)\n" +
            "and (:reviewStatus is null or reviews_status=:reviewStatus);\n")
    Long countQualified(String mobile, String username, String realName, Long userId, Byte status, Integer reviewStatus);

    @Query("select * from user\n" +
            "where direct_inviteid=:id\n" +
            "order by last_update_time desc\n" +
            "limit :pos,:size;")
    List<User> findInviteeWithPaging(Long id, int pos, int size);

    @Query("select count(*) from user\n" +
            "where direct_inviteid=:id\n")
    Long countInvitee(Long id);

    @Modifying
    @Query("update user u set country_code=:countryCode,mobile=:newMobilePhone where id=:userId;")
    void updateMobileById(String countryCode, String newMobilePhone, Long userId);

    Long countByMobileAndCountryCode(String mobile, String countryCode);

    @Query("select password from user u where u.id=:userId;")
    String findPasswordById(Long userId);

    @Modifying
    @Query("update user set password=:newPassword where id=:userId;")
    String updatePasswordById(Long userId, String newPassword);

    @Query("select paypassword from user u where u.id=:userId;")
    String findPayPasswordById(Long userId);

    @Modifying
    @Query("update user set paypassword=:newPayPassword where id=:userId;")
    String updatePayPasswordById(Long userId, String newPayPassword);

    @Query("select * from user u where u.direct_inviteid=:userId;")
    List<User> findInvitees(Long userId);

    @Query("select * from user\n" +
            "where id in (:ids)\n" +
            "and (:username is null or username like concat('%',username,'%'))\n" +
            "and (:mobile is null or mobile=:mobile)")
    List<User> findAllBasicsWithId(List<Long> ids, String username, String mobile);

    @Query("select * from user\n" +
            "where (:username is null or username like concat('%',username,'%'))\n" +
            "and (:mobile is null or mobile=:mobile)")
    List<User> findAllBasicsWithoutId(String username, String mobile);
}

package com.gabojago.trip.user.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.provider = :provider and u.providerId = :providerId")
    User findByProviderAndProviderId(String provider, String providerId);
    @Query("select u from User u where u.nickname = :nickname")
    User findByNickname(String nickname);
    boolean existsByNicknameAndIdNot(String nickname, Integer id);
    @Query(value = "SELECT u FROM User u order by RAND() limit 1", nativeQuery = true)
    User findRandomUser();
}

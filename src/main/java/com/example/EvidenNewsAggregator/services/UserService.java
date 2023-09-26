package com.example.EvidenNewsAggregator.services;

import com.example.EvidenNewsAggregator.entities.Tables;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.repostories.GenericRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserService implements GenericRepository<Users> {
    private final DSLContext dslContext;

    @Autowired
    public UserService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    public Users findByUsername(String username) {
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.USERNAME.eq(username))
                .fetchOneInto(Users.class);
    }

    @Transactional
    public Users findByEmail(String email) {
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.EMAIL.eq(email))
                .fetchOneInto(Users.class);
    }

    @Transactional
    public void add(Users user) {
        dslContext.insertInto(Tables.USERS,
                        Tables.USERS.USERNAME,
                        Tables.USERS.EMAIL,
                        Tables.USERS.PASSWORD,
                        Tables.USERS.ROLE_ID)
                .values(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoleId())
                .execute();
    }

    @Override
    public int deleteById(Integer t) {
        return 0;
    }

    @Override
    public Iterable<Users> findAll() {
        return dslContext.selectFrom(Tables.USERS)
                .fetchInto(Users.class);
    }

    @Override
    public Users findById(Integer id) {
        return null;
    }

    @Override
    public int update(Users users) {
        return 0;
    }

//    public List<Users> findAllUsers() {
//        return dslContext.selectFrom(Tables.USERS)
//                .fetchInto(Users.class);
//    }


    // Implement other database operations as needed
}

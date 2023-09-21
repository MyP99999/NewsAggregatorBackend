package com.example.EvidenNewsAggregator.user;

import com.example.EvidenNewsAggregator.entities.Tables;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {
    private final DSLContext dslContext;

    @Autowired
    public UserRepository(DSLContext dslContext) {
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

//    public List<Users> findAllUsers() {
//        return dslContext.selectFrom(Tables.USERS)
//                .fetchInto(Users.class);
//    }


    // Implement other database operations as needed
}

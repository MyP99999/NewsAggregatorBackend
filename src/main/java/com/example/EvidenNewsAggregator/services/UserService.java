package com.example.EvidenNewsAggregator.services;

import com.example.EvidenNewsAggregator.entities.Tables;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.entities.tables.records.UsersRecord;
import com.example.EvidenNewsAggregator.repostories.GenericRepository;
import org.jooq.DSLContext;
import org.jooq.UpdateSetMoreStep;
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
    public Users findById(Integer id) {
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.ID.eq(id))
                .fetchOneInto(Users.class);
    }

    @Override
    public int update(Users users) {
        return 0;
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

    @Transactional
    public int deleteById(Integer id) {
        return dslContext.deleteFrom(Tables.USERS)
                .where(Tables.USERS.ID.eq(id))
                .execute();
    }


    @Transactional
    public int updateUserFields(Integer userId, Users updatedUser) {
        // Verificați dacă utilizatorul există
        Users existingUser = findById(userId);
        if (existingUser == null) {
            return 0; // Utilizatorul nu există, nu a fost actualizat nimic.
        }

        // Actualizați doar câmpurile dorite
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getIcon() != null) {
            existingUser.setIcon(updatedUser.getIcon());
        }

        UpdateSetMoreStep<UsersRecord> updateStep = (UpdateSetMoreStep<UsersRecord>) dslContext.update(Tables.USERS)
                .set(Tables.USERS.USERNAME, existingUser.getUsername())
                .set(Tables.USERS.EMAIL, existingUser.getEmail())
                .set(Tables.USERS.ICON, existingUser.getIcon())
                .where(Tables.USERS.ID.eq(userId));

        return updateStep.execute();
    }


    @Override
    public Iterable<Users> findAll() {
        return dslContext.selectFrom(Tables.USERS)
                .fetchInto(Users.class);
    }



//    public List<Users> findAllUsers() {
//        return dslContext.selectFrom(Tables.USERS)
//                .fetchInto(Users.class);
//    }


    // Implement other database operations as needed
}

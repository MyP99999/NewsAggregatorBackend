package com.example.EvidenNewsAggregator.services;

import com.example.EvidenNewsAggregator.entities.Tables;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Roles;
import com.example.EvidenNewsAggregator.repostories.GenericRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService implements GenericRepository<Roles> {
    private final DSLContext dslContext;

    @Autowired
    public RoleService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public void add(Roles role) {
        dslContext.insertInto(Tables.ROLES,
                        Tables.ROLES.NAME)
                .values(role.getName())
                .execute();
    }

    @Override
    @Transactional
    public int deleteById(Integer id) {

        try {
            return dslContext.deleteFrom(Tables.ROLES)
                    .where(Tables.ROLES.ID.eq(id))
                    .execute();
        } catch (Exception e) {
            return 0; // Return 0 to indicate failure
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Roles> findAll() {
        return dslContext.selectFrom(Tables.ROLES)
                .fetchInto(Roles.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Roles findById(Integer id) {
        return dslContext.selectFrom(Tables.ROLES)
                .where(Tables.ROLES.ID.eq(id))
                .fetchOneInto(Roles.class);
    }

    @Override
    public int update(Roles roles) {
        return 0;
    }
}

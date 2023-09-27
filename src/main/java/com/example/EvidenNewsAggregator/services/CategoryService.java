package com.example.EvidenNewsAggregator.services;


import com.example.EvidenNewsAggregator.entities.Tables;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Categories;
import com.example.EvidenNewsAggregator.repostories.GenericRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("categoryService")
public class CategoryService implements GenericRepository<Categories> {
    private final DSLContext dslContext;

    @Autowired
    public CategoryService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public void add(Categories category) {
        dslContext.insertInto(Tables.CATEGORIES,
                        Tables.CATEGORIES.NAME)
                .values(category.getName())
                .execute();
    }

    @Override
    @Transactional
    public int deleteById(Integer id) {

        try {
            return dslContext.deleteFrom(Tables.CATEGORIES)
                    .where(Tables.CATEGORIES.ID.eq(id))
                    .execute();
        } catch (Exception e) {
            return 0; // Return 0 to indicate failure
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Categories> findAll() {
        // Implement the logic to retrieve all categories
        return dslContext.selectFrom(Tables.CATEGORIES)
                .fetchInto(Categories.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Categories findById(Integer id) {
        // Implement the logic to retrieve a category by ID
        return dslContext.selectFrom(Tables.CATEGORIES)
                .where(Tables.CATEGORIES.ID.eq(id))
                .fetchOneInto(Categories.class);
    }

    @Override
    public int update(Categories categories) {
        return 0;
    }
}



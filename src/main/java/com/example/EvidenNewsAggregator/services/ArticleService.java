package com.example.EvidenNewsAggregator.services;

import com.example.EvidenNewsAggregator.entities.Tables;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Articles;
import com.example.EvidenNewsAggregator.entities.tables.records.ArticlesRecord;
import com.example.EvidenNewsAggregator.repostories.GenericRepository;
import org.jooq.DSLContext;
import org.jooq.UpdateSetMoreStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService implements GenericRepository<Articles> {
    private final DSLContext dslContext;

    @Autowired
    public ArticleService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public void add(Articles article) {
        if (containsBadWords(article)) {
            throw new IllegalArgumentException("The article contains inappropriate content.");
        } else {
            dslContext.insertInto(Tables.ARTICLES,
                            Tables.ARTICLES.TITLE,
                            Tables.ARTICLES.DATE,
                            Tables.ARTICLES.DESCRIPTION,
                            Tables.ARTICLES.RATING,
                            Tables.ARTICLES.SOURCE_LINK,
                            Tables.ARTICLES.NAME,
                            Tables.ARTICLES.APPROVED,
                            Tables.ARTICLES.IMAGE,
                            Tables.ARTICLES.CATEGORY_ID,
                            Tables.ARTICLES.USER_ID)
                    .values(
                            article.getTitle(),
                            article.getDate(),
                            article.getDescription(),
                            article.getRating(),
                            article.getSourceLink(),
                            article.getName(),
                            article.getApproved(),
                            article.getImage(),
                            article.getCategoryId(),
                            article.getUserId())
                    .execute();
        }
    }

    @Override
    @Transactional
    public int deleteById(Integer id) {

        try {
            return dslContext.deleteFrom(Tables.ARTICLES)
                    .where(Tables.ARTICLES.ID.eq(id))
                    .execute();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Articles> findAll() {
        return dslContext.selectFrom(Tables.ARTICLES)
                .fetchInto(Articles.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Articles findById(Integer id) {
        return dslContext.selectFrom(Tables.ARTICLES)
                .where(Tables.ARTICLES.ID.eq(id))
                .fetchOneInto(Articles.class);
    }

    @Override
    public int update(Articles articles) {
        return 0;
    }

    @Transactional
    public int updateArticleFields(Integer articleId, Articles updatedArticle) {
        Articles existingArticle = findById(articleId);
        if (existingArticle == null) {
            return 0;
        }

        // Perform the update for each field
        UpdateSetMoreStep<ArticlesRecord> updateStep = dslContext.update(Tables.ARTICLES)
                .set(Tables.ARTICLES.ID, articleId);

        if (updatedArticle.getTitle() != null) {
            updateStep.set(Tables.ARTICLES.TITLE, updatedArticle.getTitle());
            existingArticle.setTitle(updatedArticle.getTitle());
        }
        if (updatedArticle.getDate() != null) {
            updateStep.set(Tables.ARTICLES.DATE, updatedArticle.getDate());
            existingArticle.setDate(updatedArticle.getDate());
        }
        if (updatedArticle.getDescription() != null) {
            updateStep.set(Tables.ARTICLES.DESCRIPTION, updatedArticle.getDescription());
            existingArticle.setDescription(updatedArticle.getDescription());
        }
        if (updatedArticle.getRating() != null) {
            updateStep.set(Tables.ARTICLES.RATING, updatedArticle.getRating());
            existingArticle.setRating(updatedArticle.getRating());
        }
        if (updatedArticle.getSourceLink() != null) {
            updateStep.set(Tables.ARTICLES.SOURCE_LINK, updatedArticle.getSourceLink());
            existingArticle.setSourceLink(updatedArticle.getSourceLink());
        }
        if (updatedArticle.getName() != null) {
            updateStep.set(Tables.ARTICLES.NAME, updatedArticle.getName());
            existingArticle.setName(updatedArticle.getName());
        }
        if (updatedArticle.getApproved() != null) {
            updateStep.set(Tables.ARTICLES.APPROVED, updatedArticle.getApproved());
            existingArticle.setApproved(updatedArticle.getApproved());
        }
        if (updatedArticle.getImage() != null) {
            updateStep.set(Tables.ARTICLES.IMAGE, updatedArticle.getImage());
            existingArticle.setImage(updatedArticle.getImage());
        }
        if (updatedArticle.getCategoryId() != null) {
            updateStep.set(Tables.ARTICLES.CATEGORY_ID, updatedArticle.getCategoryId());
            existingArticle.setCategoryId(updatedArticle.getCategoryId());
        }

        // Specify the condition to update the article by its ID
        updateStep.where(Tables.ARTICLES.ID.eq(articleId));

        return updateStep.execute();
    }

    private boolean containsBadWords(Articles article) {
        List<String> badWords = Arrays.asList("fuck", "sex", "shit", "kys", "dick");

        String textToCheck = article.getTitle() + " " + article.getDescription() + " " + article.getName();

        for (String badWord : badWords) {
            if (textToCheck.toLowerCase().contains(badWord.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

}

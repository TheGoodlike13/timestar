package com.superum.helper;

import com.superum.api.v3.lesson.LessonRepository;
import com.superum.api.v3.lesson.LessonTransformer;
import com.superum.api.v3.lesson.impl.LessonRepositoryImpl;
import com.superum.api.v3.lesson.impl.LessonTransformerImpl;
import com.superum.config.DefaultSqlConfigV3;
import com.superum.config.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@Configuration
@Lazy
public class HelperConfiguration {

    @Bean
    public DB db() {
        return new DB(persistenceContext.dsl(), lessonTransformer());
    }

    @Bean
    @Primary
    public LessonTransformer lessonTransformer() {
        return new LessonTransformerImpl(sqlConfig.lessonQueries(), sqlConfig.groupQueries(), lessonRepository());
    }

    @Bean
    @Primary
    public LessonRepository lessonRepository() {
        return new LessonRepositoryImpl(persistenceContext.dsl());
    }

    // PRIVATE

    @Autowired
    private PersistenceContext persistenceContext;

    @Autowired
    private DefaultSqlConfigV3 sqlConfig;

}

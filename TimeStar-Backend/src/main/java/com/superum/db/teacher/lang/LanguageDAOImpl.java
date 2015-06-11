package com.superum.db.teacher.lang;

import static com.superum.db.generated.timestar.Tables.LANGUAGES;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.superum.db.generated.timestar.tables.records.LanguagesRecord;

@Repository
@Transactional
public class LanguageDAOImpl implements LanguageDAO {

	@Override
	public Languages create(Languages languages) {
		int teacherId = languages.getTeacherId();
		List<String> languageList = languages.getLanguages();
		
		InsertValuesStep2<LanguagesRecord, Integer, String> step = sql.insertInto(LANGUAGES, LANGUAGES.TEACHER_ID, LANGUAGES.CODE);
		for (String language : languageList)
			step = step.values(teacherId, language);
		
		step.execute();
		return languages;
	}

	@Override
	public Languages read(Integer teacherId) {
		List<String> languages =  sql.select(LANGUAGES.CODE)
				.from(LANGUAGES)
				.where(LANGUAGES.TEACHER_ID.eq(teacherId))
				.fetch().stream()
				.map(record -> record.getValue(LANGUAGES.CODE))
				.collect(Collectors.toList());
		return new Languages(teacherId, languages);
	}

	@Override
	public Languages update(Languages languages) {
		throw new UnsupportedOperationException("No updating; delete the wrong ones, add new ones.");
	}

	@Override
	public Languages delete(Integer teacherId) {
		return delete(new Languages(teacherId, Collections.emptyList()));
	}

	@Override
	public Languages delete(Languages languages) {
		int teacherId = languages.getTeacherId();
		List<String> languageList = languages.getLanguages();
		
		Condition condition = LANGUAGES.TEACHER_ID.eq(teacherId);
		for (String language : languageList)
			condition = condition.and(LANGUAGES.CODE.eq(language));
		
		sql.delete(LANGUAGES)
			.where(condition)
			.execute();
		return languages;
	}
	
	// CONSTRUCTORS

	@Autowired
	public LanguageDAOImpl(DSLContext sql) {
		this.sql = sql;
	}

	// PRIVATE
	
	private final DSLContext sql;

}
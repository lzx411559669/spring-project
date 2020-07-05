package fm.douban.service.impl;

import com.mongodb.client.result.DeleteResult;
import fm.douban.model.Subject;
import fm.douban.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private static final Logger LOG = LoggerFactory.getLogger(SubjectServiceImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Subject addSubject(Subject subject) {
        if (subject == null) {
            LOG.error("input subject is null");
            return null;
        }
        return mongoTemplate.insert(subject);
    }

    @Override
    public Subject get(String subjectId) {
        if (!StringUtils.hasText(subjectId)) {
            LOG.error("input subjectId is blank");
            return null;
        }
        return mongoTemplate.findById(subjectId, Subject.class);
    }

    @Override
    public List<Subject> getSubjects(String type) {
        if (!StringUtils.hasText(type)) {
            LOG.error("input type is blank");
            return null;
        }
        Query query = new Query(Criteria.where("subjectType").is(type));
        return mongoTemplate.find(query, Subject.class);
    }

    @Override
    public List<Subject> getSubjects(String type, String subType) {
        if (!StringUtils.hasText(type) || !StringUtils.hasText(subType)) {
            LOG.error("input type or subType is blank");
            return null;
        }
        Criteria criteria = new Criteria();
        List<Criteria> subCris = new ArrayList<>();
        if (StringUtils.hasText(type)) {
            subCris.add(Criteria.where("subjectType").is(type));
        }
        if (StringUtils.hasText(subType)) {
            subCris.add(Criteria.where("subjectSubType").is(subType));
        }
        //至少包含一个查询条件
        if (subCris.isEmpty()) {
            return null;
        }
        criteria.andOperator(subCris.toArray(new Criteria[]{}));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Subject.class);
    }

    @Override
    public boolean delete(String subjectId) {
        if (!StringUtils.hasText(subjectId)) {
            LOG.error("input subjectID is blank");
            return false;
        }
        Subject subject = new Subject();
        subject.setId(subjectId);
        DeleteResult result = mongoTemplate.remove(subject);
        return result != null && result.getDeletedCount() > 0;
    }
}

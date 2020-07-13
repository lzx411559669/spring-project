package fm.douban.service.impl;

import com.mongodb.client.result.DeleteResult;
import fm.douban.model.Favorite;
import fm.douban.service.FavoriteService;
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

/**
 * @ClassName FavoriteServiceImpl
 * @Author 刘正星
 * @Date 2020/7/10 18:34
 **/
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private static final Logger LOG = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Favorite add(Favorite favorite) {
        if (favorite == null) {
            LOG.error("input favorit is null");
            return null;
        }
       return mongoTemplate.insert(favorite);
    }

    @Override
    public List<Favorite> list(Favorite favParam) {
        if (favParam == null) {
            LOG.error("input favorit is null");
            return null;
        }
        Criteria criteria = new Criteria();
        List<Criteria> subCriteria = new ArrayList<>();
        if (StringUtils.hasText(favParam.getUserId())){
            subCriteria.add(Criteria.where("userId").is(favParam.getUserId()));
        }
        if (StringUtils.hasText(favParam.getItemId())){
            subCriteria.add(Criteria.where("itemId").is(favParam.getItemId()));
        }
        if (!subCriteria.isEmpty()){
           criteria.andOperator(subCriteria.toArray(new Criteria[]{}));
        }
        Query query= new Query(criteria);
        return mongoTemplate.find(query,Favorite.class);
    }

    @Override
    public boolean delete(Favorite favParam) {
        if (favParam == null) {
            LOG.error("input favorit is null");
            return false;
        }
        DeleteResult deleteResult = mongoTemplate.remove(favParam);

        return deleteResult !=null && deleteResult.getDeletedCount() > 0;
    }
}

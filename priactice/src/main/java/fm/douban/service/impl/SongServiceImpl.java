package fm.douban.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import fm.douban.model.Song;
import fm.douban.param.SongQueryParam;
import fm.douban.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.regex.Pattern;

@Service
public class SongServiceImpl implements SongService {
    private static final Logger LOG = LoggerFactory.getLogger(SongServiceImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Song add(Song song) {
        if (song == null) {
            LOG.error("input song data is null");
            return null;
        }
        return mongoTemplate.insert(song);
    }

    @Override
    public Song get(String songId) {
        if (!StringUtils.hasText(songId)) {
            LOG.error("input songId is blank");
            return null;
        }
        return mongoTemplate.findById(songId, Song.class);
    }

    @Override
    public Page<Song> list(SongQueryParam songQueryParam) {
        // 作为服务，要对入参进行判断，不能假设被调用时，入参一定正确
        if (songQueryParam==null){
            LOG.error("input songqueryparam is null");
            return null;
        }
        // 总条件
        Criteria criteria = new Criteria();
        // 可能有多个子条件
        List<Criteria> subCris = new ArrayList<>();
        if (StringUtils.hasText(songQueryParam.getName())){
            //正则构建模糊查询
            Pattern pattern =Pattern.compile("^.*"+songQueryParam.getName()+".*$", Pattern.CASE_INSENSITIVE);
            subCris.add(Criteria.where("name").regex(pattern));
        }
        if (StringUtils.hasText(songQueryParam.getLyrics())){
            Pattern pattern =Pattern.compile("^.*"+songQueryParam.getLyrics()+".*$", Pattern.CASE_INSENSITIVE);
            subCris.add(Criteria.where("lyrics").regex(pattern));
        }
        if (!subCris.isEmpty()){
            criteria.andOperator(subCris.toArray(new Criteria[]{}));
        }
        Query query = new Query(criteria);
        long count = mongoTemplate.count(query, Song.class);
        Pageable pageable = PageRequest.of(songQueryParam.getPageNum()-1,songQueryParam.getPageSize());
        query.with(pageable);
        List<Song> songs = mongoTemplate.find(query, Song.class);
        // 构建分页器
        Page<Song> pageResult = PageableExecutionUtils.getPage(songs, pageable, new LongSupplier() {
            @Override
            public long getAsLong() {
                return count;
            }
        });
        return pageResult;
    }

    @Override
    public boolean modify(Song song) {
        if (song == null) {
            LOG.error("input song is null");
            return false;
        }
        Update updateData = new Update();
        Query query = new Query(Criteria.where("id").is(song.getId()));
        if (song.getName() != null) {
            updateData.set("name", song.getName());
        }
        if (song.getLyrics() != null) {
            updateData.set("lyrics", song.getLyrics());
        }
        if (song.getCover() != null) {
            updateData.set("cover", song.getCover());
        }
        if (song.getSingerIds() != null) {
            updateData.set("songerIds", song.getSingerIds());
        }
        if (song.getUrl() != null) {
            updateData.set("url", song.getUrl());
        }
        UpdateResult updataResult = mongoTemplate.updateFirst(query, updateData, song.getClass());

        return updataResult != null && updataResult.getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String songId) {
        if (!StringUtils.hasText(songId)) {
            LOG.error("input songid is blank");
            return false;
        }
        Song song = new Song();
        song.setId(songId);
        DeleteResult result = mongoTemplate.remove(song);
        return result != null && result.getDeletedCount() > 0;
    }

    @Override
    public List<Song> getAll() {
        return mongoTemplate.findAll(Song.class);
    }
}

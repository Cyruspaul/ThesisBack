package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.APIResponse;
import com.cyrus.mapper.AnnouncementsMappper;
import com.cyrus.mapper.CommentMapper;
import com.cyrus.models.Comments;
import com.cyrus.models.PublicationPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementsMappper announcementsMappper;
    private final CommentMapper comentMapper;

    public APIResponse<?> list() {
        return APIResponse.success(announcementsMappper.selectList(new QueryWrapper<PublicationPO>()));
    }

    public APIResponse<?> details(String annId) {
        return APIResponse.success(announcementsMappper.selectById(annId));
    }

    public APIResponse<?> recent() {
        List<PublicationPO> selectList = announcementsMappper.selectList(new QueryWrapper<PublicationPO>()
                .between("pdate", Date.valueOf(LocalDate.now().minusWeeks(2)), Date.valueOf(LocalDate.now()))
                .select("pid as anid, ptitle as atitle, phost as ahost")
        );
        return APIResponse.success(selectList);
    }

    public APIResponse<?> search(Map<String, String> search) {

        if (search.size() == 0) {
            return APIResponse.warning(0).message("搜索没有内容 ！！！");
        }
        QueryWrapper<PublicationPO> queryWrapper = new QueryWrapper<PublicationPO>();
        if (search.get("time") != null) {
            queryWrapper
                    .between("pdate", Date.valueOf(LocalDate.parse(search.get("time")).minusWeeks(4)),
                            Date.valueOf(LocalDate.parse(search.get("time")).plusWeeks(4)));
        }
        queryWrapper.like("ptitle", search.get("text"))
                .select("pid as anid, ptitle as atitle, phost as ahost");

        List<PublicationPO> publicationPOS = announcementsMappper.selectList(queryWrapper);

        return APIResponse.success(publicationPOS);
    }

    public APIResponse<?> add(PublicationPO publicationPO) {
        publicationPO.setGmtCreate(Date.valueOf(LocalDate.now()));
        publicationPO.setGmtModified(Date.valueOf(LocalDate.now()));
        publicationPO.setAuthor("Admin");
        publicationPO.setAviews(0);

        int insert = announcementsMappper.insert(publicationPO);
        return APIResponse.success(insert).message("通告发布成功 ！！");
    }

    public APIResponse<?> delete(Map<String, String> data) {
//        System.out.println(data);

        if (announcementsMappper.deleteById(data.get("anid")) != 1) {
            return APIResponse.warning(0).message("删除操作失败了 ！！");
        }

        Map<String, Object> map = new HashMap();
        map.put("publication_id", data.get("anid"));
        comentMapper.deleteByMap(map);

        return APIResponse.success(1).message("删除操作成功 ！！");
    }

    public APIResponse<?> commentlist(String id) {
        return APIResponse.success(comentMapper.selectList(new QueryWrapper<Comments>().eq("publication_id", id)));
    }

    public APIResponse<?> commentdelete(Map<String, String> data) {
        if (comentMapper.deleteById(data.get("comid")) != 1) {
            return APIResponse.warning(0).message("删除操作失败了 ！！");
        }

        return APIResponse.success(1).message("删除操作成功 ！！");
    }

    public APIResponse<?> commentadd(Comments comments) {

        comments.setGmtCreate(Date.valueOf(LocalDate.now()));
        comments.setGmtModified(Date.valueOf(LocalDate.now()));
        comments.setIsDeleted(false);
        int insert = comentMapper.insert(comments);
        return APIResponse.success(insert).message("通告发布成功 ！！");
    }

    public APIResponse<?> update(PublicationPO publicationPO) {

        publicationPO.setGmtModified(Date.valueOf(LocalDate.now()));
        int i = announcementsMappper.updateById(publicationPO);
        if (i == 0)
            return APIResponse.warning(0).message("更新操作失败了 ！！");
        return APIResponse.success(1).message("更新操作成功了 ！！");
    }
}

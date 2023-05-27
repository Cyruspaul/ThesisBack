package com.cyrus.controller;

import com.cyrus.config.APIResponse;
import com.cyrus.models.Comments;
import com.cyrus.models.PublicationPO;
import com.cyrus.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @RequestMapping("add")
//    @Cacheable(value = "listAnnoucements")
    public APIResponse<?> add(@RequestBody PublicationPO publicationPO) {
        return announcementService.add(publicationPO);
    }

    @RequestMapping("update")
//    @Cacheable(value = "listAnnoucements")
    public APIResponse<?> update(@RequestBody PublicationPO publicationPO) {
        return announcementService.update(publicationPO);
    }

    @RequestMapping("delete")
    public APIResponse<?> delete(@RequestBody Map<String, String> data) {
        return announcementService.delete(data);
    }

    @RequestMapping("details")
    public APIResponse<?> details(@RequestBody Map<String, String> eventId) {
        return announcementService.details(eventId.get("id"));
    }

    @RequestMapping("recent")
    @Cacheable(value = "recentAnnoucements")
    public APIResponse<?> recent() {
        return announcementService.recent();
    }

    @RequestMapping("search")
    public APIResponse<?> search(@RequestBody Map<String, String> search) {
        return announcementService.search(search);
    }
//-------------------------------------------------------------------------------------------------------------------------


    @RequestMapping("/comment/add")
//    @Cacheable(value = "listAnnoucements")
    public APIResponse<?> commentadd(@RequestBody Comments comments) {
        return announcementService.commentadd(comments);
    }

    @RequestMapping("/comment/list")
//    @Cacheable(value = "listAnnoucements")
    public APIResponse<?> commentlist(@RequestBody Map<String, String> pub) {
        return announcementService.commentlist(pub.get("id"));
    }

    @RequestMapping("/comment/delete")
    public APIResponse<?> commentdelete(@RequestBody Map<String, String> data) {
        return announcementService.commentdelete(data);
    }

}

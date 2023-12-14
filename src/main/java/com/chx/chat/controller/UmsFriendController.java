package com.chx.chat.controller;

import com.chx.chat.http.HttpAddFriendRequest;
import com.chx.chat.service.UmsMemberFriendCheckService;
import com.chx.chat.service.UmsMemberFriendService;
import com.chx.chat.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@RestController
@RequestMapping("/ums")
public class UmsFriendController {
    @Autowired
    private UmsMemberFriendCheckService checkService;

    /**
     * 添加好友
     * @param request
     * @return
     */
    @PostMapping("/add-friend")
    public R addFriend(@RequestBody HttpAddFriendRequest request){
        return checkService.addFriend(request);
    }
}

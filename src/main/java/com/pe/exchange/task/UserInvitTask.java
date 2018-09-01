package com.pe.exchange.task;

import com.pe.exchange.dao.UserDao;
import com.pe.exchange.dao.UserInvitDao;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserInvit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserInvitTask {

    @Autowired
    UserDao userDao;
    @Autowired
    UserInvitDao userInvitDao;

    /***
     *
     * @param referrerUserId 邀请人
     * @param invitedUserId 受邀人
     */
    @Async
    public void userInvit(String referrerUserId, Integer invitedUserId) {

        try {
            Integer userId = Integer.valueOf(referrerUserId);
            User one = userDao.getOne(userId);

            if (one == null) {
                // 如果邀请人不存在,则结束
                return;
            }
            List<UserInvit> list = new ArrayList<>();
            UserInvit userInvit = new UserInvit();
            userInvit.setUserId(userId);
            userInvit.setInvitedUserId(invitedUserId);
            userInvit.setLevel(1);
            list.add(userInvit);

            // 获取邀请人的所有上层关系
            List<UserInvit> referrerList = userInvitDao.findByInvitedUserId(userId);
            //添加与所有邀请人上层的关系,只保留10代
            for (UserInvit invit : referrerList) {

                userInvit = new UserInvit();
                userInvit.setUserId(userId);
                userInvit.setInvitedUserId(invit.getUserId());
                userInvit.setLevel(invit.getLevel() + 1);
                if (userInvit.getLevel() < 10) {
                    list.add(userInvit);
                }
            }
            userInvitDao.saveAll(list);

        } catch (Exception e) {
            log.error("用户关系收集异常,邀请人:"+referrerUserId+" 受邀人:"+invitedUserId, e);
        }
    }
}

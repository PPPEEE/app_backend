package com.pe.exchange.task;

import com.pe.exchange.dao.UserDao;
import com.pe.exchange.dao.UserInvitDao;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserInvit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
            //获取邀请人的历史邀请人数
            UserInvit param=new UserInvit();
            param.setUserId(userId);
            param.setInvitLevel(1);
            Example<UserInvit> example=Example.of(param);
            long count = userInvitDao.count(example);

            List<UserInvit> list = new ArrayList<>();
            UserInvit userInvit = new UserInvit();
            userInvit.setUserId(userId);
            userInvit.setInvitedUserId(invitedUserId);
            userInvit.setInvitLevel(1);
            userInvit.setInvitOrder((int)count+1);
            list.add(userInvit);

            // 获取邀请人的所有上层关系
            List<UserInvit> referrerList = userInvitDao.findByInvitedUserId(userId);
            //添加与所有邀请人上层的关系,只保留10代
            for (UserInvit invit : referrerList) {

                userInvit = new UserInvit();
                userInvit.setUserId(userId);
                userInvit.setInvitedUserId(invit.getUserId());
                userInvit.setInvitLevel(invit.getInvitLevel() + 1);
                if (userInvit.getInvitLevel() < 10) {
                    list.add(userInvit);
                }
            }
            userInvitDao.saveAll(list);

        } catch (Exception e) {
            log.error("用户关系收集异常,邀请人:"+referrerUserId+" 受邀人:"+invitedUserId, e);
        }
    }
}

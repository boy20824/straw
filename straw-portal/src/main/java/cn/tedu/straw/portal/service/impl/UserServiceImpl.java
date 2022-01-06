package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.mapper.UserRoleMapper;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.RegisterVo;
import cn.tedu.straw.portal.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    ClassroomMapper classroomMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    IQuestionService iQuestionService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final List<User> masters = new CopyOnWriteArrayList<>();

    private final Map<String, User> masterMap = new ConcurrentHashMap<>();

    private final Timer timer = new Timer();

    //代碼塊在創建對象時候執行
    {   //第一次是一個小時以後,每個小時執行一次,清除緩存,實踐緩存過期功能
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (masters) {
                    masters.clear();
                    masterMap.clear();
                }
            }
        }, 1000 * 60 * 60, 1000 * 60 * 60);
    }

    @Override
    public UserDetails getUserDetails(String username) {
        User user = userMapper.findUserByUsername(username);

        if (user == null) {
            return null;
        }
        List<Permission> permissions = userMapper.findUserPermissionById(user.getId());

        System.out.println(permissions);

        //添加用戶全部的權限訊息
        String[] arr = new String[permissions.size()];
        int i = 0;
        for (Permission p : permissions) {
            arr[i++] = p.getName();
        }

        //添加用戶的全部角色訊息
        List<Role> roles=userMapper.findUserRolesById(user.getId());
        arr= Arrays.copyOf(arr,arr.length+roles.size());
        for(Role role:roles){
            arr[i++]=role.getName();
        }

        UserDetails u = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(arr)
                .disabled(user.getEnabled() == 0)  //false才能通過 是否允許
                .accountLocked(user.getLocked() == 1) //false才能通過 是否鎖定
                .build();
        return u;
    }

    @Override
    @Transactional
    public void registerStudent(RegisterVo registerVo) {
        //為了讓方法更加健全,在方法開始前都會先檢查方法參數
        if (registerVo == null) {
            log.info("方法參數為空");
            throw ServiceException.unprocessableEntity("參數為空!!");
        }
        log.debug("方法參數{}", registerVo);

        //檢驗邀請碼
        log.debug("驗證邀請碼{}", registerVo.getInviteCode());
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invite_code", registerVo.getInviteCode());
        Classroom classroom = classroomMapper.selectOne(queryWrapper);
        if (classroom == null) {
            log.info("驗證邀請碼失敗!!");
            throw ServiceException.notFound("無效的邀請碼,請聯繫任課老師");
        }
        log.debug("驗證手機號碼是否使用{}", registerVo.getPhone());
        User user = userMapper.findUserByUsername(registerVo.getPhone());
        if (user != null) {
            log.info("手機號碼已經註冊過!");
            throw ServiceException.unprocessableEntity("手機號碼已經註冊過!");
        }
        user = new User();
        user.setUsername(registerVo.getPhone());
        user.setNickName(registerVo.getNickname());
        //密碼加密
        String password = bCryptPasswordEncoder.encode(registerVo.getPassword());
        user.setPassword("{bcrypt}" + password);
        user.setPhone(registerVo.getPhone());
        user.setLocked(0);
        user.setEnabled(1);
        user.setAccountType(0);//1是老師  0是學生
        user.setCreateTime(LocalDateTime.now());
        user.setClassroomId(classroom.getId());
        user.setBirthday(null);
        int rows = userMapper.insert(user);
        if (rows != 1) {
            log.info("保存用戶訊息失敗");
            throw new ServiceException("數據庫繁忙,請稍後在試");
        }
        log.debug("保存User數據成功{}", user);
        log.debug("設置用戶是一個學生角色");
        //Role的ID 是 2
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(2);
        rows = userRoleMapper.insert(userRole);
        if (rows != 1) {
            log.info("保存用戶訊息失敗");
            throw new ServiceException("數據庫繁忙,請稍後在試");
        }
        log.debug("設置用戶的角色{}", userRole);
    }

    @Override
    public String currentUsername() {
        /*
        利用Spring-Security提供的API獲取當前已經登錄的用戶名
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //檢查是否是匿名登錄,匿名登錄沒有登錄狀態
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return username;
        }
        throw ServiceException.notFound("沒有找到當前登錄用戶!");
    }

    @Override
    public List<User> getMasters() {

        if (masters.isEmpty()) {
            synchronized (masters) {
                if (masters.isEmpty()) {
                 /*
                 user 表中 type屬性是1的都是回答問題的老師
                */
                    QueryWrapper<User> query = new QueryWrapper<>();
                    query.eq("type", 1);//type 0是 學生 type 1是 老師
                    List<User> list = userMapper.selectList(query);
                    //初始化 masters緩存
                    masters.addAll(list);
                    //初始化 masterMap緩存
                    masters.forEach(master -> masterMap.put(master.getNickName(), master));
                    //清除密碼
                    masters.forEach(master -> master.setPassword(""));
                }
            }
        }
        return masters;
    }

    @Override
    public Map<String, User> getMasterMap() {
        if (masterMap.isEmpty()) {
            getMasters();
        }
        return masterMap;
    }

    @Override
    public UserVO getCurrentUserVo() {
        String username = currentUsername();
        UserVO userVO = userMapper.getUserVoByUsername(username);
        Integer count = iQuestionService.countQuestionsByUserId(userVO.getId());
        //TODO: 以後增加統計收藏問題的數量
        userVO.setQuestions(count).setCollections(0);
        return userVO;
    }
}

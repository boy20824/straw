package cn.tedu.straw.sys.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserVO implements Serializable {
    Integer id;
    String username;
    String nickname;

    //用戶提交的問題數量
    Integer questions;
    //用戶收藏的問題數量
    Integer collections;
}

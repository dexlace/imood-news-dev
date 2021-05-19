package com.dexlace.admin.repository;

import com.dexlace.model.mo.FriendLinkMO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
public interface FriendLinkRepository extends MongoRepository<FriendLinkMO,String> {
    /**
     * 自定义Dao方法
     * 同Spring Data JPA一样Spring Data mongodb也提供自定义方法的规则，
     * 如下： 按照ﬁndByXXX，ﬁndByXXXAndYYY、countByXXXAndYYY等规则定义方法，实现查询操作。
     */

    public List<FriendLinkMO> getAllByIsDelete(Integer isDelete);

}

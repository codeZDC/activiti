package com.tuzhi.activiti.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.tuzhi.activiti.domain.User;

import tk.mybatis.mapper.common.Mapper;

 /**
 * @ClassName:UserMapper
 * @Description:数据层接口
 * @author 郑德超
 * @CreateDate 2018-04-20 16:28:17
 */
public interface IUserMapper extends Mapper<User> {
	
	/**
	 * @title:findUserList
	 * @description: 查询列表
	 * @author 郑德超
	 * @param user
	 * @CreateDate  2018-04-20 16:28:17
	 */
	List<User> findUserList(User user);
	
		/**
	 * @title:updateSelectiveById
	 * @description: 更新（更新不为null的值）
	 * @author 郑德超
	 * @param user
	 * @CreateDate  2018-04-20 16:28:17
	 */
    int updateUserSelectiveById(User user);
    
    @Select("select id,username ,password from t_user where username= #{username} and password=#{password}")
    User login(User user);

    //获取所有用户键值对
    @Select("SELECT id,username FROM t_user")
	List<User> getUsers();
    
	List<Map<String, Object>> getNextRoles(String[] arr);
}

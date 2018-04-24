package com.tuzhi.activiti.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tuzhi.activiti.base.BaseDomain;


/**
 * @ClassName:User
 * @Description:的实体类
 * @author 郑德超
 * @CreateDate 2018-04-20 16:28:17
 */
@Table(name = "t_user")
public class User extends BaseDomain {
	
    /****/
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /****/
    private String username;

    /****/
    private String password;

    /****/
    public Integer getId(){
        return id;
    }
    /****/
    public void setId(Integer id){
        this.id= id;
    }
    /****/
    public String getUsername(){
        return username;
    }
    /****/
    public void setUsername(String username){
        this.username= username;
    }
    /****/
    public String getPassword(){
        return password;
    }
    /****/
    public void setPassword(String password){
        this.password= password;
    }

	
}

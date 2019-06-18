package com.huai.shiro.dao.Impl;


import com.huai.shiro.dao.IUserDAO;
import com.huai.shiro.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {

    private JdbcTemplate template;

    @Autowired
    private void setDataSource(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return template.queryForObject("select * from user where username = ? ",new RowMapper<User>(){
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setId(rs.getLong("id"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }, username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;


    }

//    @Override
//    public List<User> selectUser() {
//        String  sql = "select resource from user ";
//        List<User> users = new ArrayList<>();
//        return template.query(sql, new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User();
//                user.setUsername(rs.getString("username"));
//                user.setId(rs.getLong("id"));
//                user.setPassword(rs.getString("password"));
//                users.add(user);
//                return null;
//
//            }
//
//        });
//        return users;
//    }
}

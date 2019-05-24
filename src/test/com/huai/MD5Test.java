package com.huai;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class MD5Test {
    @Test
    public  void testMd5() throws Exception{
        String password = "666";
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println(md5Hash);
        /*加盐*/
        md5Hash = new Md5Hash(password,"zhangsan");
        System.out.println(md5Hash);
        /*加数列次数*/
        md5Hash = new Md5Hash(password,"zhangsan",3);
        System.out.println(md5Hash);
    }
}

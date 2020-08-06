package test;

import org.junit.Test;

import java.io.File;

/**
 * @author Florence
 * 头像改变测试类
 */
public class ChangeFaceServletTest {
    @Test
    public void  makeSureMkdirExist() {
        //创建以savePath为存储路径的文件
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\userface");
        String savePath =file.getAbsolutePath();
        System.out.println(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath+"目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
    }

}
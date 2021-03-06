package controller;

import myinterface.UserServer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import server.UserServerImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 修改头像（上传头像）
 * @author Florence
 */
@WebServlet("/ChangeFaceServlet")
public class ChangeFaceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String savePath = makeSureMkdirExist(request);
        try{
            //创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建一个文件解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //中文乱码问题
            upload.setHeaderEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            //使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            Map<String,String> map = new HashMap<>(5);
            //循环获取对象，并放入map中存储信息
            for(FileItem item : list){
                //自定义获取信息方法
                getInfFromFromData(item,map,savePath);
            }
            UserServer server= new UserServerImpl();
            //调用服务改变用户头像地址
            server.changeUserFace(map.get("account"),map.get("face"));
            //返回用户头像地址
            response.getWriter().write(map.get("face"));
        }catch (Exception e) {
            System.out.println("头像上传失败");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request, response);
    }
    /**
     * 确保头像保存目录存在
     * @return 头像基本目录
     */
    private String makeSureMkdirExist(HttpServletRequest request) {
        //创建以savePath为存储路径的文件
        File file = new File(request.getServletContext().getRealPath("/userface"));
        String savePath =file.getAbsolutePath();
        System.out.println(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath+"目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        return savePath;
    }

    /**
     * 自定义方法根据一个FILEITEM对象来获取信息
     * @param item 信息或者文件对象
     * @param map 存放获取完的用户账号和头像地址
     * @param savePath 存放的根路径
     */
    private void getInfFromFromData(FileItem item,Map<String,String> map,String savePath){
        try {
            //普通字符串数据
            if(item.isFormField()){
                map.put("account",item.getString("UTF-8"));
            }
            //二进制数据（文件，图片）
            else{
                //得到上传的文件名称，
                String filename = item.getName();
                System.out.println(filename);
                //如果为空返回
                if(filename==null || "".equals(filename.trim())){
                    return;
                }
                //防止出现意外，正确获取名字
                filename = filename.substring(filename.lastIndexOf("\\")+1);
                //获取item中的上传文件的输入流
                InputStream in = item.getInputStream();
                //创建一个文件输出流
                FileOutputStream out = new FileOutputStream(savePath+"\\"+filename);
                //存储文件名
                map.put("face","../userface/"+filename);
                //创建一个缓冲区
                byte[] buffer = new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int len;
                //循环将输入流读入到缓冲区当中
                while((len=in.read(buffer))>0){
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
                //删除处理文件上传时生成的临时文件
                item.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

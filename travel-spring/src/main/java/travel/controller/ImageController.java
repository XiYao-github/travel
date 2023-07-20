package travel.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import travel.base.utils.Result;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    private final static String PROJECT_PATH = System.getProperty("user.dir");

    @Value("${image.path}")
    private String imagePath;
    @Value("${server.port}")
    private String port;

    public String getPath() {
        log.info("图片路径：" + PROJECT_PATH + imagePath);
        return PROJECT_PATH + imagePath;
    }

    /**
     * 文件上传
     *
     * @param file
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());

        //获取文件名
        //String originalFilename = file.getOriginalFilename();
        //截取文件后缀名
        //String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名
        //String fileName = UUID.randomUUID().toString() + suffix;

        String fileName = file.getOriginalFilename();

        //创建一个目录对象
        File dir = new File(getPath());
        //判断当前目录是否存在
        if (!dir.exists()) {
            //目录不存在，需要创建
            dir.mkdirs();
        }
        File fileNew = new File(getPath() + fileName);
        if (fileNew.exists()) {
            //存在相同名称图片，返回请求路径
            return new Result().success("http://localhost:" + port + "/travel/image/download?name=" + fileName);
        }
        try {
            //将临时文件转存到指定位置
            file.transferTo(fileNew);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result().success("http://localhost:" + port + "/travel/image/download?name=" + fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(getPath() + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.chx.chat.controller;

import com.chx.chat.config.OssConfig;
import com.chx.chat.config.redis.RedisService;
import com.chx.chat.utils.PathUtils;
import com.chx.chat.utils.R;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月13日
 */
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/oss")
public class OssUploadController {


    @PostMapping("/upload")
    public R uploadImg(@RequestParam("file") MultipartFile img) {
        //判断文件类型

        //获取原始文件名
        String originalFilename = img.getOriginalFilename();

        //对原始文件进行判断
//        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg") ) {
//            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
//        }

        //判断文件大小，限制为5MB
//        if (img.getSize() > 5 * 1024 * 1024) {
//            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
//        }

        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return R.ok().setData(url);
    }


    @Autowired
    private RedisService redisService;
    @GetMapping("/redis")
    public String red(){
        redisService.setCacheObject("moc","12312313", 5l, TimeUnit.SECONDS);
        return "redis";
    }

    private String uploadOss(MultipartFile imgFile, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create("9Rj9lMl9x3hUtjlviQVHGczQ8gDOaCanUqraLzFp","ruOB8qtj-E4OAniq6QSNQfxrScnLF2jPDDPG9aQl");
            String upToken = auth.uploadToken("milsun");
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println("key:" + putRet.key);
                System.out.println("path:" + putRet.hash);
                // 设置下载凭证有效期（秒）
                long expireInSeconds = 3600; // 设置为1小时，你可以根据需求进行调整
                // 生成下载凭证
                String privateUrl = auth.privateDownloadUrl("https://cloud.gzmilsun.com/" + key, expireInSeconds);
                //返回域名 + key + token
                return privateUrl;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString() + "900");
                try {
                    System.err.println(r.bodyString() + "90");
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        //出现异常则返回原始链接
        return "https://cloud.gzmilsun.com/" + key;

    }
}

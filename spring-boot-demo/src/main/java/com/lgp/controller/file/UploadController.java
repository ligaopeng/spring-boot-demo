package com.lgp.controller.file;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lgp.common.util.FileDownload;
import com.lgp.entity.excel.CardData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-19 19:43
 */
@RestController
@RequestMapping(value = "file")
public class UploadController {

    /**
     * 上传单个文件
     *
     * @param file
     * @return
     */

    @PostMapping(value = "upload")
    public String upload(MultipartFile file) {
        String savePath = "";
        try {
            //上传目录
//            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            String uploadDir = "/Users/ligaopeng/Myapp/import/" + "upload/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //调用上传方法
            savePath = executeUpload(uploadDir, file);
            return savePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePath;
    }


    /**
     * 上传多个文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "uploads")
    public List<String> uploads(MultipartFile[] file) {
        List<String> savePaths = new ArrayList<>();
        try {
            //上传目录地址
            String uploadDir = "/Users/ligaopeng/Myapp/import/" + "upload/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //遍历文件数组执行上传
            for (int i = 0; i < file.length; i++) {
                if (file[i] != null) {
                    //调用上传方法
                    String uploadPath = executeUpload(uploadDir, file[i]);
                    savePaths.add(uploadPath);
                }
            }
        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
        }
        return savePaths;
    }

    private String executeUpload(String uploadDir, MultipartFile file) throws Exception {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String fileName = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        String serverPath = uploadDir + fileName;
        File serverFile = new File(serverPath);
        file.transferTo(serverFile);
        return serverPath;
    }


    @PostMapping(value = "importExcel")
    public void importExcel(HttpServletResponse response, MultipartFile file) throws Exception {
        FileOutputStream os = null;
        String s = "";
        String ss = "";
        try {
            //上传文件
            String savePath = upload(file);
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            List<CardData> result = ExcelImportUtil.importExcel(new File(savePath), CardData.class, params);
            String saveDir = "/Users/ligaopeng/Myapp/import/" + "sql/";
            File file1 = new File(saveDir);
            if (!file1.exists()) {
                file1.mkdir();
            }
            ss = System.currentTimeMillis() + ".sql";
            s = saveDir + ss;
            os = new FileOutputStream(s);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            for (int i = 0; i < 10; i++) {
                CardData cardData = result.get(i);
                String cardSql = cardData.getUserPhone() + "_" + cardData.getOpenTime() + "_" + cardData.getExpireTime();
                bufferedWriter.write(cardSql.toCharArray());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        if (StringUtils.isNotBlank(s)) {
            FileDownload.fileDownload(response, s, ss);
        }
    }


}


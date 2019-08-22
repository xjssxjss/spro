package com.spro.util;

import com.spro.common.BusinessException;
import com.spro.common.GlobalConstant;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liyang on 2017/4/22.
 */
public class FileUtil {

    private static final int BUFFER_SIZE = 16 * 1024;

    private static final String SIGN = "/";

    private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateformatYYYYMM = new SimpleDateFormat("yyyyMM");
    private static final SimpleDateFormat dateformatYYYYMMddHHmmssSSS = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");

    /**
     * 文件上传到服务器
     *
     * @param file
     * @param date
     * @param entityName
     * @return
     * @throws IOException
     */
    public static File fileUpload(MultipartFile file, Date date,
                                  String entityName) throws IOException {
        byte[] bytes = file.getBytes();
        if (bytes == null || bytes.length == 0)
            return null;
        String finalTargetFolder;
        // finalTargetFolder = GlobalConstant.UPLOAD_FOLDER + "/" +
        // dateformat.format(date);
        finalTargetFolder = null//resourceBox.getResource("upload_path")
                + SIGN
                + dateformatYYYYMM.format(date);
        // finalTargetFolder = GlobalConstant.UPLOAD_FOLDER + "/" + entityName;
        File rootFolder = new File(finalTargetFolder);
        // 文件夹不存在，则创建文件夹
        if (!rootFolder.exists())
            rootFolder.mkdirs();
        // 生成文件名
        String[] nameSplit = file.getOriginalFilename().split("\\.");
        System.out.println(nameSplit[nameSplit.length - 1]);
        File finalFile = new File(finalTargetFolder + SIGN + entityName + "_"
                // + (new Date()).getTime()
                + dateformatYYYYMMddHHmmssSSS.format(date) + "."
                + nameSplit[nameSplit.length - 1]);
        // 写文件
        if (!file.isEmpty()) {
            file.transferTo(finalFile);
        }
        return finalFile;
    }

    /**
     * 下载
     *
     * @param request
     * @param response
     * @param storeName
     * @param contentType
     * @param realName
     * @throws Exception
     */
    public static void download(HttpServletRequest request,
                                HttpServletResponse response, String folder,
                                String storeName, String contentType, String realName)
            throws Exception {
        response.setContentType(GlobalConstant.CONTENT_TYPE);
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        String downLoadPath = folder + storeName;

        long fileLength = new File(downLoadPath).length();

        response.setContentType(contentType);
        response.setHeader("Content-disposition", "attachment; filename=\""
                + new String(realName.getBytes("utf-8"), "ISO8859-1") + "\"");
        response.setHeader("Content-Length", String.valueOf(fileLength));

        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    /**
     * Excel 解析
     *
     * @param file           Excel文件
     * @param len            数据列的数量
     * @param dateformat     日期格式
     * @param isNumberFormat 是否需要数字千分位
     * @param titleCheck     需要按顺序校验的表头，为空表示不校验
     * @return List<String[]>
     * String[]表示每一行的数据
     * @throws IOException
     */
    public static List<String[]> fileRead(MultipartFile file, Integer len, String dateformat, Boolean isNumberFormat, List<String> titleCheck)
            throws IOException {
        // 报错信息
        StringBuffer errorSB = new StringBuffer();
        List<String[]> list = new ArrayList<String[]>();
        SimpleDateFormat dformat = null;
        if (!StringUtil.isEmptyWithTrim(dateformat)) {
            dformat = new SimpleDateFormat(dateformat);
        }
        InputStream is = file.getInputStream();
        XSSFWorkbook wbs = new XSSFWorkbook(is);
        XSSFSheet childSheet = wbs.getSheetAt(0);
        for (int j = 0; j <= childSheet.getLastRowNum(); j++) {
            String[] arr = new String[len];
            XSSFRow row = childSheet.getRow(j);
            if (null != row) {
                int k = 0;
                for (k = 0; k < row.getLastCellNum(); k++) {
                    String value = null;
                    XSSFCell cell = row.getCell(k);
                    if (null != cell) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_FORMULA:
                                try {
                                    value = String.valueOf(cell.getStringCellValue());
                                } catch (IllegalStateException e) {
                                    value = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_STRING:// 字符串
                                value = cell.getRichStringCellValue().getString();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:// 数字
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    double d = cell.getNumericCellValue();
                                    Date date = HSSFDateUtil.getJavaDate(d);
                                    if (dformat != null)
                                        value = dformat.format(date);
                                } else {
                                    NumberFormat nf = NumberFormat.getInstance();
                                    nf.setGroupingUsed(isNumberFormat);// true时的格式：1,234,567,890
                                    value = nf.format(cell.getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                value = "";
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                value = String.valueOf(cell.getErrorCellValue());
                                break;
                            default:
                                value = cell.toString().trim();
                                break;
                        }
                    }
                    if (value != null)
                        value = value.trim();
                    arr[k] = value;
                }

                if (j == 0) {
                    String exceMsg = "导入文件模板格式错误，请下载本系统的模板。";
                    if (k >= len - 1) {
                        if (titleCheck != null && titleCheck.size() == len) {
                            for (int title = 0; title < titleCheck.size(); title++) {
                                if (!titleCheck.get(title).equals(arr[title])) {
                                    throw new BusinessException(exceMsg);
                                }
                            }
                        } else if (titleCheck != null && titleCheck.size() != len) {
                            throw new BusinessException(exceMsg);
                        }
                    } else {
                        throw new BusinessException(exceMsg);
                    }
                }
                if (j == 0 || StringUtil.isEmptyWithTrim(arr[0])) {
                    continue;
                }
                list.add(arr);
            }
        }

        if (errorSB.length() > 0) {
            throw new RuntimeException(errorSB.toString());
        }

        return list;
    }

    /**
     * Excel 解析    与fileRead方法差别在于不会校验第一行的值必须存在
     *
     * @param file           Excel文件
     * @param len            数据列的数量
     * @param dateformat     日期格式
     * @param isNumberFormat 是否需要数字千分位
     * @param titleCheck     需要按顺序校验的表头，为空表示不校验
     * @return List<String[]>
     * String[]表示每一行的数据
     * @throws IOException
     */
    public static List<String[]> fileReadTwo(MultipartFile file, Integer len, String dateformat, Boolean isNumberFormat, List<String> titleCheck)
            throws IOException {
        // 报错信息
        StringBuffer errorSB = new StringBuffer();
        List<String[]> list = new ArrayList<String[]>();
        SimpleDateFormat dformat = null;
        if (!StringUtil.isEmptyWithTrim(dateformat)) {
            dformat = new SimpleDateFormat(dateformat);
        }
        InputStream is = file.getInputStream();
        XSSFWorkbook wbs = new XSSFWorkbook(is);
        XSSFSheet childSheet = wbs.getSheetAt(0);
        for (int j = 0; j <= childSheet.getLastRowNum(); j++) {
            String[] arr = new String[len];
            XSSFRow row = childSheet.getRow(j);
            if (null != row) {
                int k = 0;
                for (k = 0; k < row.getLastCellNum(); k++) {
                    String value = null;
                    XSSFCell cell = row.getCell(k);
                    if (null != cell) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_FORMULA:
                                try {
                                    value = String.valueOf(cell.getStringCellValue());
                                } catch (IllegalStateException e) {
                                    value = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_STRING:// 字符串
                                value = cell.getRichStringCellValue().getString();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:// 数字
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    double d = cell.getNumericCellValue();
                                    Date date = HSSFDateUtil.getJavaDate(d);
                                    if (dformat != null)
                                        value = dformat.format(date);
                                } else {
                                    NumberFormat nf = NumberFormat.getInstance();
                                    nf.setGroupingUsed(isNumberFormat);// true时的格式：1,234,567,890
                                    value = nf.format(cell.getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                value = "";
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                value = String.valueOf(cell.getErrorCellValue());
                                break;
                            default:
                                value = cell.toString().trim();
                                break;
                        }
                    }
                    if (value != null)
                        value = value.trim();
                    arr[k] = value;
                }

                if (j == 0) {
                    String exceMsg = "导入文件模板格式错误，请下载本系统的模板。";
                    if (k >= len - 1) {
                        if (titleCheck != null && titleCheck.size() == len) {
                            for (int title = 0; title < titleCheck.size(); title++) {
                                if (!titleCheck.get(title).equals(arr[title])) {
                                    throw new BusinessException(exceMsg);
                                }
                            }
                        } else if (titleCheck != null && titleCheck.size() != len) {
                            throw new BusinessException(exceMsg);
                        }
                    } else {
                        throw new BusinessException(exceMsg);
                    }
                }
                if (j == 0) {
                    continue;
                }
                list.add(arr);
            }
        }

        if (errorSB.length() > 0) {
            throw new BusinessException(errorSB.toString());
        }

        return list;
    }

    /**
     * copy文件
     *
     * @param oldLoad 需要下载文件路径
     * @param newLoad 需要copy路径
     */
    public static void copyFile(String oldLoad, String newLoad, String fileNme) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File rootFolder = new File(newLoad);
            //文件夹不存在，则创建文件夹
            if (!rootFolder.exists()) rootFolder.mkdirs();
            //图片下载
            URL url = new URL(oldLoad);
            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();
            outputStream = new FileOutputStream(new File(newLoad + "//" + fileNme));
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }


    }

    /**
     * 创建文件
     *
     * @param filepath
     * @throws IOException
     */
    public static void createFile(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) file.createNewFile();
    }

    public static void copyAndRenameFile(String sourceFile, String targetPath, String newFileName) {
        File in = new File(sourceFile);
        File out = new File(targetPath); // 目标文件夹
        try {
            Files.copy(in.toPath(), out.toPath().resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

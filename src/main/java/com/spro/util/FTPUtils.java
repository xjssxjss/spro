package com.spro.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;


/**
 * FTP服务器工具类
 *
 * @author 码农先生
 * @version 1.0
 * @date 2013-11-18
 * @see 参数 地址1：https://blog.csdn.net/hbcui1984/article/details/2720204
 * @see 参数 地址2:https://blog.csdn.net/yibing548/article/details/38586073
 * @see 参考地址3：https://blog.csdn.net/for_china2012/article/details/16820607
 *
 */
@SuppressWarnings("all")
public class FTPUtils {

    private static final Logger logger= LoggerFactory.getLogger(FTPUtils.class);

    private static FTPUtils ftpUtils;
    private static FTPClient ftpClient;

    private String url; //服务器地址
    private String port; // 服务器端口
    private String username; // 用户登录名
    private String password; // 用户登录密码

    private InputStream is; // 文件下载输入流

    /**
     * 私有构造方法
     */
    private FTPUtils() {
        initConfig();
        if (null == ftpClient) {
            ftpClient = new FTPClient();
        }
    }

    /**
     * 获取FTPUtils对象实例
     *
     * @return FTPUtils对象实例
     */
    public synchronized static FTPUtils getInstance() {
        if (null == ftpUtils) {
            ftpUtils = new FTPUtils();
        }
        return ftpUtils;
    }

    /**
     * 初始化FTP服务器连接属性
     */
    public void initConfig() {
        // 构造Properties对象
        Properties properties = new Properties();

        // 定义配置文件输入流
        InputStream is = null;
        try {
            // 获取配置文件输入流
            is = FTPUtils.class.getResourceAsStream("/spro.properties");
            // 加载配置文件
            properties.load(is);
            // 读取配置文件
            url = (String) properties.get("url"); // 设置服务器地址
            port = (String) properties.get("port"); // 设置端口
            username = (String) properties.get("username"); // 设置用户名
            password = (String) properties.get("password"); // 设置密码
        } catch (IOException e) {
            logger.error("光大加载配置文件FTP异常！"+e.getMessage());
            throw new RuntimeException("光大FTP初始化失败，关闭输入流异常" , e);
        } finally {
            // 判断输入流是否为空
            if (null != is) {
                try {
                    // 关闭输入流
                    is.close();
                } catch (IOException e) {
                    logger.error("光大加载配置文件FTP异常！"+e.getMessage());
                    throw new RuntimeException("光大FTP初始化失败，关闭输入流异常" , e);
                }
            }
        }
    }

    /**
     * 连接（配置通用连接属性）至服务器
     *
     * @param serverName
     *            服务器名称
     * @param remotePath
     *            当前访问目录
     * @return <b>true</b>：连接成功

     *         <b>false</b>：连接失败
     */
    public boolean connectToTheServer(String serverName, String remotePath) {
        // 定义返回值
        boolean result = false;
        try {
            // 连接至服务器，端口默认为21时，可直接通过URL连接
            ftpClient.connect(serverName, Integer.parseInt(port));
            // 登录服务器
            ftpClient.login(username, password);
            // 判断返回码是否合法
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                // 不合法时断开连接
                ftpClient.disconnect();
                // 结束程序
                return result;
            }
            // 设置文件操作目录
            result = ftpClient.changeWorkingDirectory(remotePath);
            // 设置文件类型，二进制
            result = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 设置缓冲区大小
            ftpClient.setBufferSize(3072);
            // 设置字符编码
            ftpClient.setControlEncoding("UTF-8");
        } catch (IOException e) {
            logger.error("光大连接FTP服务器异常"+e.getMessage());
            throw new RuntimeException("光大连接FTP服务器异常" , e);
        }
        return result;
    }

    /**
     * 上传文件至FTP服务器
     *
     * @param serverName
     *            服务器名称
     * @param storePath
     *            上传文件存储路径
     * @param fileName
     *            上传文件存储名称
     * @param is
     *            上传文件输入流
     * @return <b>true</b>：上传成功

     *         <b>false</b>：上传失败
     */
    public boolean storeFile(String storePath,
                             String fileName, InputStream is) {
        boolean result = false;
        try {
            // 上传文件
            ftpClient.mkd(storePath);
            // 连接至服务器
            result = connectToTheServer(url, storePath);
            // 判断服务器是否连接成功
            if (result) {
                result = ftpClient.storeFile(fileName, is);
            }
            // 关闭输入流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 判断输入流是否存在
            if (null != is) {
                try {
                    // 关闭输入流
                    is.close();
                } catch (IOException e) {
                    logger.error("上传文件至FTP异常"+e.getMessage());
                    throw new RuntimeException("上传文件至FTP异常" , e);
                }
            }
            // 登出服务器并断开连接
            //ftpUtils.logout();
        }
        return result;
    }

    /**
     * 下载FTP服务器文件至本地

     * 操作完成后需调用logout方法与服务器断开连接
     *
     * @param serverName
     *            服务器名称
     * @param remotePath
     *            下载文件存储路径
     * @param fileName
     *            下载文件存储名称
     * @return <b>InputStream</b>：文件输入流
     */
    public InputStream retrieveFile(String serverName, String remotePath,
                                    String fileName) {
        try {
            boolean result = false;
            // 连接至服务器
            result = connectToTheServer(serverName, remotePath);
            // 判断服务器是否连接成功
            if (result) {
                // 获取文件输入流
                is = ftpClient.retrieveFileStream(fileName);
            }
        } catch (IOException e) {
            logger.error("光大从FTP下载文件到本地异常"+e.getMessage());
            throw new RuntimeException("光大从FTP下载文件到本地异常" , e);
        }
        return is;
    }

    /**
     * 删除FTP服务器文件
     *
     * @param serverName
     *            服务器名称
     * @param remotePath
     *            当前访问目录
     * @param fileName
     *            文件存储名称
     * @return <b>true</b>：删除成功

     *         <b>false</b>：删除失败
     */
    public boolean deleteFile(String serverName, String remotePath,
                              String fileName) {
        boolean result = false;
        // 连接至服务器
        result = connectToTheServer(serverName, remotePath);
        // 判断服务器是否连接成功
        if (result) {
            try {
                // 删除文件
                result = ftpClient.deleteFile(fileName);
            } catch (IOException e) {
                logger.error("删除FTP服务器上的 文件异常"+e.getMessage());
                throw new RuntimeException("删除FTP服务器上的 文件异常" , e);
            } finally {
                // 登出服务器并断开连接
                ftpUtils.logout();
            }
        }
        return result;
    }

    /**
     * 检测FTP服务器文件是否存在
     *
     * @param serverName
     *            服务器名称
     * @param remotePath
     *            检测文件存储路径
     * @param fileName
     *            检测文件存储名称
     * @return <b>true</b>：文件存在

     *         <b>false</b>：文件不存在
     */
    public boolean checkFile(String serverName, String remotePath,
                             String fileName) {
        boolean result = false;
        try {
            // 连接至服务器
            result = connectToTheServer(serverName, remotePath);
            // 判断服务器是否连接成功
            if (result) {
                // 默认文件不存在
                result = false;
                // 获取文件操作目录下所有文件名称
                String[] remoteNames = ftpClient.listNames();
                // 循环比对文件名称，判断是否含有当前要下载的文件名
                for (String remoteName : remoteNames) {
                    if (fileName.equals(remoteName)) {
                        result = true;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("光大检查FTP文件是否存在异常"+e.getMessage());
            throw new RuntimeException("光大检查FTP文件是否存在异常" , e);
        } finally {
            // 登出服务器并断开连接
            ftpUtils.logout();
        }
        return result;
    }

    /**
     * 登出服务器并断开连接
     *
     * @param ftp
     *            FTPClient对象实例
     * @return <b>true</b>：操作成功

     *         <b>false</b>：操作失败
     */
    public boolean logout() {
        boolean result = false;
        if (null != is) {
            try {
                // 关闭输入流
                is.close();
            } catch (IOException e) {
                logger.error("光大登录FTP服务器异常"+e.getMessage());
                throw new RuntimeException("光大登录FTP服务器异常" , e);
            }
        }
        if (null != ftpClient) {
            try {
                // 登出服务器
                result = ftpClient.logout();
            } catch (IOException e) {
                logger.error("光大登录FTP服务器异常"+e.getMessage());
                throw new RuntimeException("光大登录FTP服务器异常" , e);
            } finally {
                // 判断连接是否存在
                if (ftpClient.isConnected()) {
                    try {
                        // 断开连接
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        logger.error("光大关闭FTP服务器异常"+e.getMessage());
                        throw new RuntimeException("光大关闭FTP服务器异常" , e);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url
     *            FTP服务器hostname
     * @param path
     *            FTP服务器保存目录
     * @param filename
     *            上传到FTP服务器上的文件名
     * @param input
     *            输入流
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(String path, String filename,
                              InputStream input) {
        boolean success = false;
        try {
            int reply;
            ftpClient.connect(url, Integer.parseInt(port));// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(username, password);// 登录
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            storeFile(path,filename, input);

            input.close();
            success = true;
        } catch (IOException e) {
            logger.error("向FTP服务器上传文件异常"+e.getMessage());
            throw new RuntimeException("向FTP服务器上传文件异常" , e);
        }
        return logout();
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public boolean downFile( String remotePath, String fileName,
                             String localPath,String fileSaveName) {
        // 初始表示下载失败
        boolean success = false;
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            int reply;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.connect(url, Integer.parseInt(port));
            //登陆ftp
            ftpClient.login(username, password);
            reply = ftpClient.getReplyCode();
            /*
             * 判断是否连接成功
             */
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            // 转移到FTP服务器目录
            ftpClient.changeWorkingDirectory(remotePath);
            // 列出该目录下所有文件
            FTPFile[] fs = ftpClient.listFiles();
            // 遍历所有文件，找到指定的文件
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    // 根据绝对路径初始化文件
                    File localFile = new File(localPath );
                    //输出流
                    OutputStream is = new FileOutputStream(localPath + "/" + fileSaveName);
                    //下载文件
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            // 下载成功
            success = true;
        } catch (IOException e) {
            logger.error("从FTP服务器下载文件异常"+e.getMessage());
            throw new RuntimeException("从FTP服务器下载文件异常" , e);
        } finally {
            // 登出服务器并断开连接
            ftpUtils.logout();
        }
        return success;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
     * @param url
     *            FTP服务器hostname
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public boolean downFile(String url, String remotePath, String fileName,
                            String localPath,String pathName) {
        // 初始表示下载失败
        boolean success = false;

        try {
            int reply;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.connect(url, Integer.parseInt(port));
            //登陆ftp
            ftpClient.login(username, password);
            reply = ftpClient.getReplyCode();
            /*
             * 判断是否连接成功
             */
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            // 转移到FTP服务器目录
            ftpClient.changeWorkingDirectory(remotePath);
            // 列出该目录下所有文件
            FTPFile[] fs = ftpClient.listFiles();
            // 遍历所有文件，找到指定的文件
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    // 根据绝对路径初始化文件
                    File localFile = new File(localPath + "/" + ff.getName());
                    //输出流
                    OutputStream is = new FileOutputStream(localFile);
                    //下载文件
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            // 下载成功
            success = true;
        } catch (IOException e) {
            logger.error("从FTP服务器下载文件异常"+e.getMessage());
            throw new RuntimeException("从FTP服务器下载文件异常" , e);
        } finally {
            // 登出服务器并断开连接
            ftpUtils.logout();
        }
        return success;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param url
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public static boolean downFile(String url, int port, String username,
                                   String password, String remotePath, String fileName,
                                   String localPath) {
        // 初始表示下载失败
        boolean success = false;
        // 创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.connect(url, port);
            reply = ftp.getReplyCode();
            /*
             * 判断是否连接成功
             */
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            } else {
                // 登录ftp
                if (ftp.login(username, password)) {
                    // 转到指定下载目录
                    ftp.changeWorkingDirectory(remotePath);
                    // 列出该目录下所有文件
                    FTPFile[] fs = ftp.listFiles();
                    // 遍历所有文件，找到指定的文件
                    for (FTPFile ff : fs) {
                        if (ff.getName().equals(fileName)) {
                            // 根据绝对路径初始化文件
                            File localFile = new File(localPath + "/"
                                    + ff.getName());
                            // 输出流
                            OutputStream is = new FileOutputStream(localFile);
                            // 下载文件
                            ftp.retrieveFile(ff.getName(), is);
                            is.close();
                        }
                    }
                    // 退出ftp
                    ftp.logout();
                    // 下载成功
                    success = true;
                }
            }
        } catch (IOException e) {
            logger.error("从FTP服务器下载文件异常"+e.getMessage());
            throw new RuntimeException("从FTP服务器下载文件异常" , e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    logger.error("关闭FTP连接异常"+ioe.getMessage());
                    throw new RuntimeException("关闭FTP连接异常" , ioe);
                }
            }
        }
        return success;
    }

    /**
     * 读取本地TXT
     *
     * @param filepath
     *            txt文件目录即文件名
     */

    public ArrayList<String> readtxt(String filepath) {
        ArrayList<String> readList = new ArrayList<String>();
        ArrayList retList = new ArrayList();
        try {
            String temp = null;
            File f = new File(filepath);
            String adn = "";
            // 指定读取编码用于读取中文
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    f), "UTF-8");
            BufferedReader reader = new BufferedReader(read);
            // bufReader = new BufferedReader(new FileReader(filepath));
            do {
                temp = reader.readLine();
                readList.add(temp);
            } while (temp != null);
            read.close();
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("读取本地txt异常"+e.getMessage());
            throw new RuntimeException("读取本地txt异常" , e);
        }
        return readList;
    }

    /**
     *
     * @Description： 测试连接ftP服务器
     * @author: GuXiYang
     * @date: 2015-6-18 下午4:28:15
     * @param args
     */

}
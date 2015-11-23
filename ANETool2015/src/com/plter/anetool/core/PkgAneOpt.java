package com.plter.anetool.core;

import com.plter.anetool.models.AneConfigInfo;
import com.plter.anetool.utils.ArrayTool;
import com.plter.anetool.utils.FileTool;
import com.plter.anetool.utils.Log;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by plter on 11/20/15.
 */
public class PkgAneOpt {


    public static void startPkgAne(String airSdkHome,AneConfigInfo info){

        //Check JDK Home
        Log.info("正在检查Java环境");
        File javaHomeDir = new File(System.getProperty("java.home"));
        File javaHomeBinDir = new File(javaHomeDir,"bin");
        String osName = System.getProperty("os.name").toLowerCase();
        File javaFile = null;
        if (osName.startsWith("mac")||osName.startsWith("linux")) {
            javaFile = new File(javaHomeBinDir, "java");
        }else if (osName.startsWith("win")){
            javaFile = new File(javaHomeBinDir,"java.exe");
        }else {
            Log.error("您的系统不支持此操作");
            return;
        }

        if (!javaFile.exists()){
            Log.error("JRE不完整");
            return;
        }

        //Check Flex/AIR SDK
        Log.info("正在检查 AIR/Flex SDK");
        File airSdkHomeDir = new File(airSdkHome);
        File airSdkLibDir = new File(airSdkHomeDir,"lib");
        File adtJarFile = new File(airSdkLibDir,"adt.jar");
        if (!adtJarFile.exists()){
            Log.error("所选的AIR/Flex SDK不完整");
            return;
        }

        final File finalJavaFile = javaFile;
        new Thread(){
            @Override
            public void run() {
                super.run();

                File tmpDir = new File(System.getProperty("java.io.tmpdir"));
                File tmpWorkDir = new File(tmpDir,"AneWork"+System.currentTimeMillis());
                if (tmpWorkDir.exists()){
                    tmpWorkDir.delete();
                }
                tmpWorkDir.mkdirs();
                Platform.runLater(() -> Log.info("创建临时工作目录 "+tmpWorkDir.getAbsolutePath()));

                File swcFile = new File(info.swcPath);
                if (!swcFile.exists()){
                    Platform.runLater(() -> Log.error("所选的swc文件存在"));
                    return;
                }

                ZipFile swcZip = null;
                try {
                    swcZip = new ZipFile(swcFile);
                } catch (IOException e) {
                    e.printStackTrace();

                    Platform.runLater(() -> Log.error("打开swc文件时出错"));
                    return;
                }

                File librarySwfFile = new File(tmpWorkDir,"library.swf");
                byte[] buffer = new byte[1024];
                try {
                    ZipEntry swfEntry = swcZip.getEntry("library.swf");
                    InputStream inputStream = swcZip.getInputStream(swfEntry);
                    FileOutputStream fos = new FileOutputStream(librarySwfFile);
                    int size = 0;
                    while ((size=inputStream.read(buffer))!=-1){
                        fos.write(buffer,0,size);
                    }
                    fos.flush();
                    fos.close();
                    inputStream.close();

                    Platform.runLater(() -> Log.info("成功释放swf文件"));
                } catch (IOException e) {
                    e.printStackTrace();

                    Platform.runLater(() -> Log.error("读取swc并释放swf时出错"));
                    return;
                }


                File extensionXML = new File(tmpWorkDir,"extension.xml");
                try {
                    FileOutputStream fos = new FileOutputStream(extensionXML);
                    fos.write(info.toExtensionXMLContent().getBytes("UTF-8"));
                    fos.flush();
                    fos.close();

                    Platform.runLater(() -> Log.info("成功生成extension.xml"));
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> Log.info("生成extension.xml时出错"));
                    return;
                }

                File androidWorkDir = null;

                if (info.supportAndroid){
                    File jarOrSoFile = new File(info.jarPath);
                    if (!jarOrSoFile.exists()){
                        Platform.runLater(() -> Log.info("所选的Android本机库文件不存在"));
                        return;
                    }

                    Log.infoFromAnotherThread("创建Android临时工作目录");
                    androidWorkDir = new File(tmpWorkDir,"AndroidARM");
                    androidWorkDir.mkdirs();

                    Log.infoFromAnotherThread("拷贝相关资源到Android临时工作目录");
                    FileTool.copyTo(librarySwfFile,new File(androidWorkDir,"library.swf"));
                    FileTool.copyTo(jarOrSoFile,new File(androidWorkDir,jarOrSoFile.getName()));
                }

                //pkg ane command
                List<String> args = new ArrayList<String>();
                args.add(finalJavaFile.getAbsolutePath());
                args.add("-jar");
                args.add(adtJarFile.getAbsolutePath());
                args.add("-package");
                if (!info.useTimestamp){
                    args.add("-tsa");
                    args.add("none");
                }
                args.add("-storetype");
                args.add("PKCS12");
                args.add("-keystore");
                args.add(info.certPath);
                args.add("-storepass");
                args.add(info.certPassword);
                args.add("-target");
                args.add("ane");
                args.add(info.aneOutputPath);
                args.add(extensionXML.getAbsolutePath());
                args.add("-swc");
                args.add(swcFile.getAbsolutePath());
                if (info.supportAndroid){
                    args.add("-platform");
                    args.add("Android-ARM");
                    args.add("-C");
                    args.add(androidWorkDir.getAbsolutePath());
                    args.add(".");
                }

                Platform.runLater(() -> Log.info("执行打包命令:"+ ArrayTool.join(args," ")));
                ProcessBuilder pb = new ProcessBuilder(args);
                try {
                    int resultCode = pb.start().waitFor();

                    Log.infoFromAnotherThread("清理临时目录");
                    FileTool.deleteDirectory(tmpWorkDir);

                    checkResultCode(resultCode);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void checkResultCode(int resultCode) {
        switch(resultCode)
        {
            case 0:
                Log.infoFromAnotherThread("打包完成");
                break;
            case 100:
            {
                Log.errorFromAnotherThread("无法分析应用程序描述符");
                break;
            }
            case 101:
                Log.errorFromAnotherThread("缺少命名空间");
                break;
            case 102:
                Log.errorFromAnotherThread("命名空间无效");
                break;
            case 103:
                Log.errorFromAnotherThread("意外的元素或属性：\n1.删除引起错误的元素和属性。描述符文件中不允许使用自定义值。\n" +
                        "2.检查元素和属性名称的拼写。\n" +
                        "3.确保将元素放置在正确的父元素内，且使用属性时对应着正确的元素。");
                break;
            case 104:
                Log.errorFromAnotherThread("缺少元素或属性");
                break;
            case 105:
                Log.errorFromAnotherThread("元素或属性所含的某个值无效");
                break;
            case 106:
                Log.errorFromAnotherThread("窗口属性组合非法");
                break;
            case 107:
                Log.errorFromAnotherThread("窗口最小大小大于窗口最大大小");
                break;
            case 108:
                Log.errorFromAnotherThread("前面的元素中已使用的属性");
                break;
            case 109:
                Log.errorFromAnotherThread("重复元素");
                break;
            case 110:
                Log.errorFromAnotherThread("至少需要一个指定类型的元素");
                break;
            case 111:
                Log.errorFromAnotherThread("在应用程序描述符中列出的配置文件都不支持本机扩展，将配置文件添加到支持 本机扩展的 supportedProfies 列表。");
                break;
            case 112:
                Log.errorFromAnotherThread("AIR 目标不支持本机扩展，选择支持本机扩展的目标。");
                break;
            case 113:
                Log.errorFromAnotherThread("<nativeLibrary> 和 <initializer> 必须一起提供，必须为本机扩展中的每个本机库都指定初始值设定项函数。");
                break;
            case 114:
                Log.errorFromAnotherThread("找到不含 <nativeLibrary> 的 <finalizer>，除非平台使用本机库，否则不要指定终结器。");
                break;
            case 115:
                Log.errorFromAnotherThread("默认平台不得包含本机实施。");
                break;
            case 116:
                Log.errorFromAnotherThread("此目标不支持浏览器调用，对于指定的打包目标，<allowBrowserInvocation> 元素不能为 true。");
                break;
            case 117:
                Log.errorFromAnotherThread("此目标至少需要命名空间 n 打包本机扩展。");
                break;
            case 200:
                Log.errorFromAnotherThread("无法打开图标文件");
                break;
            case 201:
                Log.errorFromAnotherThread("图标大小错误");
                break;
            case 202:
                Log.errorFromAnotherThread("图标文件包含的某种图像格式不受支持");
                break;
            case 300:
                Log.errorFromAnotherThread("缺少文件，或无法打开文件");
                break;
            case 301:
                Log.errorFromAnotherThread("缺少或无法打开应用程序描述符文件");
                break;
            case 302:
                Log.errorFromAnotherThread("包中缺少根内容文件");
                break;
            case 303:
                Log.errorFromAnotherThread("包中缺少图标文件");
                break;
            case 304:
                Log.errorFromAnotherThread("初始窗口内容无效");
                break;
            case 305:
                Log.errorFromAnotherThread("初始窗口内容的 SWF 版本超出命名空间的版本");
                break;
            case 306:
                Log.errorFromAnotherThread("配置文件不受支持");
                break;
            case 307:
                Log.errorFromAnotherThread("命名空间必须至少为 nnn");
                break;
            case 2:
                Log.errorFromAnotherThread("用法错误，检查命令行参数是否存在错误");
                break;
            case 5:
                Log.errorFromAnotherThread("未知错误，此错误表示所发生的情况无法按常见的错误条件作出解释。可能的根源包括 ADT 与 Java 运行时环境之间不兼容、ADT 或 JRE 安装损坏以及 ADT 内有编程错误。");
                break;
            case 6:
                Log.errorFromAnotherThread("无法写入输出目录，确保指定的（或隐含的）输出目录可访问，并且所在驱动器有足够的磁盘空间。");
                break;
            case 7:
                Log.errorFromAnotherThread("无法访问证书");
                break;
            case 8:
                Log.errorFromAnotherThread("证书无效，证书文件格式错误、被修改、已到期或被撤消。");
                break;
            case 9:
                Log.errorFromAnotherThread("无法为 AIR 文件签名，验证传递给 ADT 的签名选项。");
                break;
            case 10:
                Log.errorFromAnotherThread("无法创建时间戳，ADT 无法与时间戳服务器建立连接。如果通过代理服务器连接到 Internet，则可能需要配置 JRE 的代理服务器设置。");
                break;
            case 11:
                Log.errorFromAnotherThread("创建证书时出错");
                break;
            case 12:
                Log.errorFromAnotherThread("输入无效，验证命令行中传递给 ADT 的文件路径和其他参数。");
                break;
            case 13:
                Log.errorFromAnotherThread("缺少设备 SDK，验证设备 SDK 配置。ADT 找不到执行指定命令所需的设备 SDK。");
                break;
            case 14:
                Log.errorFromAnotherThread("设备错误，ADT 无法执行命令，因为存在设备限制或设备问题。例如，在尝试卸载未实际安装的应用程序时会显示此退出代码。");
                break;
            case 15:
                Log.errorFromAnotherThread("无设备，验证设备是否已连接且已开启，或仿真器是否正在运行。");
                break;
            case 16:
                Log.errorFromAnotherThread("缺少 GPL 组件，当前的 AIR SDK 未包含执行请求的操作所需的所有组件。");
                break;
            case 17:
                Log.errorFromAnotherThread("设备打包工具失败，由于缺少预期的操作系统组件，因此无法创建包。");
                break;
            case 400:
                Log.errorFromAnotherThread("当前的 Android sdk 版本不支持属性，检查属性名称的拼写是否正确，以及对于在其中出现的元素是否为有效的属性。如果此属性是在 Android 2.2 之后新增的，您可能需要在 ADT 命令中设置 -platformsdk 标志。");
                break;
            case 401:
                Log.errorFromAnotherThread("当前的 Android sdk 版本不支持属性值，检查属性值的拼写是否正确，以及对于该属性是否为有效的值。如果此属性值是在 Android 2.2 之后新增的，您可能需要在 ADT 命令中设置 -platformsdk 标志。");
                break;
            case 402:
                Log.errorFromAnotherThread("当前的 Android sdk 版本不支持 XML 标签，检查 XML 标签名称的拼写是否正确，以及是否为有效的 Android 清单文档元素。如果此元素是在 Android 2.2 之后新增的，您可能需要在 ADT 命令中设置 -platformsdk 标志。");
                break;
            case 403:
                Log.errorFromAnotherThread("不允许覆盖 Android 标签");
                break;
            case 404:
                Log.errorFromAnotherThread("不允许覆盖 Android 属性");
                break;
            case 405:
                Log.errorFromAnotherThread("Android 标签 %1 必须是 manifestAdditions 标签中的第一个元素");
                break;
            case 406:
                Log.errorFromAnotherThread("Android 标签 %2 的属性 %1 具有无效值 %3。");
                break;
            case 1000:
                Log.errorFromAnotherThread("参数太少");
                break;
            case 1001:
                Log.errorFromAnotherThread("创建进程失败");
                break;
        }
    }

}

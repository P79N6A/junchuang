package com.teachingplan.console.service.impl.file;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.xslf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.*;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * Created by v_yanfzhang on 2018/2/5.
 */
@Service("ppt2HtmlService")
public class PPT2HtmlService {
    /**
     * 将PPTX文件转换为Html文件
     * @param sourceFileName            PPT源文件
     * @param imageFormatNameString     转换成的图片格式,如:"jpg"、"jpeg"、"bmp" "png" "gif" "tiff"
     */
    public void pptx2Html(String oldFileName, String sourceFileName,String imageFormatNameString){
        // *.html 父目录
        String targetFileName = System.getProperty("user.dir") + "/file/";
        File targetFile = new File(targetFileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        // 存储图片目录
        String targetFileImgName = targetFileName+"img/";
        File targetFileImg = new File(targetFileImgName);
        if(!targetFileImg.exists()){
            targetFileImg.mkdirs();
        }
        // html文件流
        FileOutputStream htmlOutputStream = null;
        // html内容
        StringBuffer htmlContent = new StringBuffer();
        // PPT文件输入流
        FileInputStream pptInputStream = null;
        // PPT对象
        XMLSlideShow slideShow=null;
        // 每一张图片的输出流
        FileOutputStream imgPageOutputStream = null;
        try{
            try {
                // 初始化PPT文件输入流
                pptInputStream=new FileInputStream(sourceFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                // 初始化PPT对象
                slideShow=new XMLSlideShow(pptInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 初始化html流对象
                htmlOutputStream = new FileOutputStream(sourceFileName.replace(".pptx",".html"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //获取PPT每页的尺寸大小（宽和高度）
            Dimension onePPTPageSize=slideShow.getPageSize();
            //获取PPT文件中的所有PPT页面，并转换为一张张播放片
            java.util.List<XSLFSlide> pptPageXSLFSLiseList= slideShow.getSlides();

            String xmlFontFormat="<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">"+
                    "<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> "+
                    "<a:latin typeface=\"+mj-ea\"/> "+
                    "</a:rPr>"+
                    "</xml-fragment>";

            for (int i = 0; i < pptPageXSLFSLiseList.size(); i++) {
                //设置中文为宋体，解决中文乱码问题
                for (XSLFShape shape: pptPageXSLFSLiseList.get(i).getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape textShape = (XSLFTextShape)shape;
                        for (XSLFTextParagraph textParagraph:textShape.getTextParagraphs()) {
                            java.util.List<XSLFTextRun> textRunList = textParagraph.getTextRuns();
                            for (XSLFTextRun textRun:textRunList) {
                                textRun.setFontFamily("宋体");
                            }
                        }
                    } else if (shape instanceof XSLFGroupShape) {

                        setGroupFont(shape);
//                        XSLFGroupShape groupShape = (XSLFGroupShape)shape;
//                        for (XSLFShape xslfShape : groupShape.getShapes()) {
//                            if (xslfShape instanceof XSLFTextShape) {
//                                XSLFTextShape textShape = (XSLFTextShape)xslfShape;
//                                for (XSLFTextParagraph textParagraph:textShape.getTextParagraphs()) {
//                                    java.util.List<XSLFTextRun> textRunList = textParagraph.getTextRuns();
//                                    for (XSLFTextRun textRun:textRunList) {
//                                        textRun.setFontFamily("宋体");
//                                    }
//                                }
//                            }
//                        }
                    }
                }

                CTSlide oneCTSlide=pptPageXSLFSLiseList.get(i).getXmlObject();
                CTGroupShape oneCTGroupShape=oneCTSlide.getCSld().getSpTree();
                java.util.List<CTShape> oneCTShapeList=oneCTGroupShape.getSpList();
                for (CTShape ctShape : oneCTShapeList) {
                    CTTextBody oneCTTextBody = ctShape.getTxBody();

                    if(null==oneCTTextBody){
                        continue;
                    }
                    CTTextParagraph[]  oneCTTextParagraph= oneCTTextBody.getPArray();
                    CTTextFont oneCTTextFont=null;
                    try {
                        oneCTTextFont=CTTextFont.Factory.parse(xmlFontFormat);
                    } catch (XmlException e) {
                        e.printStackTrace();
                    }
                    for (CTTextParagraph ctTextParagraph : oneCTTextParagraph) {
                        CTRegularTextRun[]  onrCTRegularTextRunArray=ctTextParagraph.getRArray();
                        for (CTRegularTextRun ctRegularTextRun : onrCTRegularTextRunArray) {
                            CTTextCharacterProperties oneCTTextCharacterProperties =ctRegularTextRun.getRPr();
                            oneCTTextCharacterProperties.setLatin(oneCTTextFont);
                        }
                    }
                }
                // 创建BufferedImage 对象，图像尺寸为原来的PPT的每页尺寸
                BufferedImage oneBufferedImage=new BufferedImage(onePPTPageSize.width, onePPTPageSize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D  oneGraphics2D = oneBufferedImage.createGraphics();
                // 将PPT文件中的每个页面中的相关内容画到转换后的图片中
                pptPageXSLFSLiseList.get(i).draw(oneGraphics2D);
                try {
                    // 设置图片的存放路径和图片格式，注意生成的文件路径为绝对路径，最终获得各个图像文件所对应的输出流的对象
                    String imgName=oldFileName + (i+1)+"_"+ UUID.randomUUID().toString()+"."+imageFormatNameString;
                    imgPageOutputStream=new FileOutputStream(targetFileImgName+imgName);
                    // 保存成为html内容
                    htmlContent.append("<img width='100%' height='auto' style='margin-bottom:20px;' src='/file/img/"+imgName+"'/>");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //将转换后的各个图片文件保存带指定的目录中
                try {
                    ImageIO.write(oneBufferedImage, imageFormatNameString, imgPageOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally{
            try {
                if(imgPageOutputStream!=null){
                    imgPageOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(pptInputStream!=null){
                    pptInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 写入html内容到文件中
        try {
            htmlOutputStream.write(htmlContent.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setGroupFont(XSLFShape shape) {
        XSLFGroupShape groupShape = (XSLFGroupShape)shape;
        for (XSLFShape xslfShape : groupShape.getShapes()) {
            if (xslfShape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape)xslfShape;
                for (XSLFTextParagraph textParagraph:textShape.getTextParagraphs()) {
                    java.util.List<XSLFTextRun> textRunList = textParagraph.getTextRuns();
                    for (XSLFTextRun textRun:textRunList) {
                        textRun.setFontFamily("宋体");
                    }
                }
            } else if (xslfShape instanceof XSLFGroupShape) {
                setGroupFont(xslfShape);
            }
        }
    }

    /**
     * 将PPT文件转换为Html文件
     * @param sourceFileName            PPT源文件
     * @param imageFormatNameString     转换成的图片格式,如:"jpg"、"jpeg"、"bmp" "png" "gif" "tiff"
     */
    public void ppt2Html(String oldFileName,String sourceFileName,String imageFormatNameString){
        // *.html 父目录
        String targetFileName = System.getProperty("user.dir") + "/file/";
        File targetFile = new File(targetFileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        // 存储图片目录
        String targetFileImgName = targetFileName+"img/";
        File targetFileImg = new File(targetFileImgName);
        if(!targetFileImg.exists()){
            targetFileImg.mkdirs();
        }
        // html文件流
        FileOutputStream htmlOutputStream = null;
        // html内容
        StringBuffer htmlContent = new StringBuffer();
        // PPT文件输入流
        FileInputStream pptInputStream = null;
        // PPT对象
        HSLFSlideShow slideShow=null;
        // 每一张图片的输出流
        FileOutputStream imgPageOutputStream = null;
        try{
            // 初始化PPT输入流对象
            try {
                pptInputStream=new FileInputStream(sourceFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 初始化PPT对象
            try {
                slideShow=new HSLFSlideShow(pptInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 初始化html流对象
                htmlOutputStream = new FileOutputStream(sourceFileName.replace(".ppt",".html"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //获取PPT每页的大小（宽和高度）
            Dimension  onePPTPageSize= slideShow.getPageSize();
            //获得PPT文件中的所有的PPT页面（获得每一张幻灯片）,并转为一张张的播放片
            java.util.List<HSLFSlide> pptPageSlideList=slideShow.getSlides();
            //下面循环的主要功能是实现对PPT文件中的每一张幻灯片进行转换和操作
            for (int i = 0; i <pptPageSlideList.size(); i++) {
                //这几个循环只要是设置字体为宋体，防止中文乱码，
                java.util.List<java.util.List<HSLFTextParagraph>> oneTextParagraphs=pptPageSlideList.get(i).getTextParagraphs();
                for (java.util.List<HSLFTextParagraph> list :oneTextParagraphs) {
                    for (HSLFTextParagraph hslfTextParagraph : list) {
                        java.util.List<HSLFTextRun> HSLFTextRunList= hslfTextParagraph.getTextRuns();
                        for (int j = 0; j <HSLFTextRunList.size(); j++) {
							 /*
							  * 如果PPT在WPS中保存过，则 HSLFTextRunList.get(j).getFontSize();的值为0或者26040，
							  * 因此首先识别当前文本框内的字体尺寸是否为0或者大于26040，则设置默认的字体尺寸。
							  */
                            //设置字体大小
                            Double size= HSLFTextRunList.get(j).getFontSize();
                            if((size<=0)||(size>=26040)){
                                HSLFTextRunList.get(j).setFontSize(20.0);
                            }
                            //设置字体样式为宋体
                            HSLFTextRunList.get(j).setFontFamily("宋体");
                        }
                    }
                }
                //创建BufferedImage对象，图像的尺寸为原来的每页的尺寸
                BufferedImage oneBufferedImage=new BufferedImage(onePPTPageSize.width, onePPTPageSize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D oneGraphics2D=oneBufferedImage.createGraphics();
                //设置转换后的图片背景色为白色
                oneGraphics2D.setPaint(Color.white);
                oneGraphics2D.fill(new Rectangle2D.Float(0,0,onePPTPageSize.width,onePPTPageSize.height));
                pptPageSlideList.get(i).draw(oneGraphics2D);
                //设置图片的存放路径和图片格式，注意生成的图片路径为绝对路径，最终获得各个图像文件所对应的输出流对象
                try {
                    String imgName=oldFileName + (i+1)+"_"+UUID.randomUUID().toString()+"."+imageFormatNameString;
                    imgPageOutputStream=new FileOutputStream(targetFileImgName+imgName);
                    // 保存成为html内容
                    htmlContent.append("<img width='100%' height='auto' style='margin-bottom:20px;' src='/file/img/"+imgName+"'/>");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //转换后的图片文件保存的指定的目录中
                try {
                    ImageIO.write(oneBufferedImage, imageFormatNameString, imgPageOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }finally{
            try {
                if(imgPageOutputStream!=null){
                    imgPageOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(pptInputStream!=null){
                    pptInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 写入html内容到文件中
        try {
            htmlOutputStream.write(htmlContent.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

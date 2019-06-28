package com.danxiaochong.blog.controlller;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
/**
 * 备忘录的到期邮件提醒
 * */
public class SendEmails {
    //发件人地址
    public static String senderAddress = "1171403238@qq.com";
    //收件人地址
//    public static String recipientAddress = "18701474327@163.com";
    //发件人账户名
    public static String senderAccount = "1171403238@qq.com";
    //发件人账户密码
    public static String senderPassword = "qgulwhvjoncjgjjf";

    public void SendEmail(String recipientAddress) throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session,recipientAddress);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());

        //5、关闭邮件连接
        transport.close();
    }

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session,String recipientAddress) throws Exception{
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
//        Map<String,String> map = new HashMap();
//        map.put("杨帆163", "18701474327@163.com");
//        map.put("杨帆icloud", "vito.yang@icloud.com");
//        for(String key:map.keySet()) {
//            msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(map.get(key)));
//        }
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        msg.setRecipient(Message.RecipientType.CC, new InternetAddress(senderAddress)); // 抄送人
        //4.设置邮件主题
        msg.setSubject("小乌龟备忘提醒","UTF-8");

        //下面是设置邮件正文
        msg.setContent("您有一个待办事项预约提醒，请注意关注。", "text/html;charset=UTF-8");
        // 5. 创建附件"节点"
//        MimeBodyPart attachment = new MimeBodyPart();
//        // 读取本地文件
//        DataHandler dh2 = new DataHandler(new FileDataSource("D:\\KTHWAS\\ExcelsMon\\财务部考勤日报2019-02-27.xls"));
//        // 将附件数据添加到"节点"
//        attachment.setDataHandler(dh2);
//        // 设置附件的文件名（需要编码）
//        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 6. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
//        MimeMultipart mm = new MimeMultipart();
////        mm.addBodyPart(text_image);
//        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
//        mm.setSubType("mixed");         // 混合关系

        // 7. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
//        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

}


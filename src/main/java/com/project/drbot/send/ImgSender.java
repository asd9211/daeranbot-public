package com.project.drbot.send;

import com.jcraft.jsch.*;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Vector;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImgSender {
    @Value("${remote.ip}")
    private String remoteAddr;
    @Value("${remote.port}")
    private int port;
    @Value("${remote.username}")
    private String username;
    @Value("${remote.private_key}")
    private String privateKey;
    @Value("${img.input_file_path}")
    private String inputFilePath;
    @Value("${img.output_file_path}")
    private String outputFilePath;


    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp channelSftp = null;

    private final DaeranBoardRepository daeranBoardRepository;

    public void sendImgBySFTP() {
        JSch jsch = new JSch();
        try {
            jsch.addIdentity(privateKey);
            session = jsch.getSession(username, remoteAddr, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");

            channel.connect();
            channelSftp = (ChannelSftp) channel;

            LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0));
            LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

            List<DaeranBoardEntity> entityList = daeranBoardRepository.findByRegDateBetween(startDatetime, endDatetime);
            Vector<?> lists = channelSftp.ls("/home/ec2-user/imgstorage/");

            for(DaeranBoardEntity dn : entityList){
                try{
                    String fileName = dn.getLogoPath().replace("/images/", "");
                    boolean isExists = false;
                    for (Object row : lists) {
                        if(dn.getLogoPath().contains("/images/")){
                            if(row.toString().contains(fileName)){
                                isExists = true;
                                break;
                            }
                        }
                    }
                    if(!isExists && dn.getLogoPath().contains("/images/")){
                        String filePath = inputFilePath + fileName;
                        String DestinyPath = outputFilePath + fileName;
                        channelSftp.put(filePath, DestinyPath);
                    }
                }catch (Exception e ){
                    log.info(e.getMessage());
                }
            }

        } catch (JSchException e) {
            log.error("JSchException", e);
        } catch (SftpException e1) {
            log.error("SftpException", e1);
        } finally {
            if (channelSftp != null) channelSftp.disconnect();
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
    }

}

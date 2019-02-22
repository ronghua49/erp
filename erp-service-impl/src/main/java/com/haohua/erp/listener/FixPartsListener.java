package com.haohua.erp.listener;    /*
 * @author  Administrator
 * @date 2018/8/8
 */
import com.haohua.erp.service.PartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class FixPartsListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(FixPartsListener.class);
    @Autowired
    private PartService partService;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            logger.debug("监听到新的维修配件列表{}",textMessage.getText());
            partService.addFixTransPartsList(textMessage.getText());
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

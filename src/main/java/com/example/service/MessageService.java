package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // create message
    public Message createMessage(Message message) {
        if (message.getMessageText().length() == 0 || message.getMessageText().length() >= 255) {
            return null;
        }

        if (accountRepository.existsById(message.getPostedBy())) {
            return messageRepository.save(message);
        }
        return null;
    }
    
    // get all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // get message by id
    public Message getMessageById(int message_id) {
        Optional<Message> existingMessage = messageRepository.findById(message_id);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            return message;
        }
        return null;
    }

    // delete message by id
    public void deleteMessageById(int message_id) {
        messageRepository.deleteById(message_id);
    }

    // update message by id
    public void updateMessageById(int message_id, Message message) {
        Optional<Message> existingMessage = messageRepository.findById(message_id);
        if (existingMessage.isPresent() && message.getMessageText().length() > 0 && message.getMessageText().length() <= 255) {
            Message newMessage = existingMessage.get();
            newMessage.setMessageText(message.getMessageText());
            messageRepository.save(newMessage);
        }
    }

    // get all messages from user given account id
    public List<Message> getAllMessagesByAccountId(int account_id) {
        return messageRepository.findAllMessagesByAccountId(account_id);
    }
}

package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account newAccount = accountService.registerAccount(account);
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).body(null);
        }
        if (newAccount == null) {
            return ResponseEntity.status(409).body(null);
        }
        return ResponseEntity.status(200).body(newAccount);

    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account existingAccount = accountService.loginAccount(account);
        if (existingAccount == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.status(200).body(existingAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        if (newMessage == null) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(newMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable ("message_id") int message_id) {
        Message message = messageService.getMessageById(message_id);
        return ResponseEntity.status(200).body(message);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable ("message_id") int message_id) {
        Message message = messageService.getMessageById(message_id);
        if (message != null) {
            messageService.deleteMessageById(message_id);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable ("message_id") int message_id, @RequestBody Message newMessage) {
        Message message = messageService.getMessageById(message_id);
        if (message != null && newMessage.getMessageText().length() > 0 && newMessage.getMessageText().length() <= 255) {
            messageService.updateMessageById(message_id, newMessage);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable ("account_id") int account_id){
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        return ResponseEntity.status(200).body(messages);
    }
}

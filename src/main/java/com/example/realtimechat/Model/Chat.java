package com.example.realtimechat.Model;

import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Date;

public class Chat extends Document {
        private static final String USERID = "userId";
        private static final String USR_DOMAIN = "userDomain";
        private static final String TYPE = "type";
        private static final String CONTENT = "content";
        private static final String SENDER = "sender";
        private static final String CREATED_AT = "createdAt";
        public static final String CHAT_BOX = "ChatBox";
        public static final String WEBSITE_DOMAIN = "WebsiteDomain";

        public String getUserid() {
            return getString(USERID);
        }

        public void setUserid(String userid) {
            put(USERID, userid);
        }

        public String getUsrDomain() {
            return getString(USR_DOMAIN);
        }

        public void setUsrDomain(String usrDomain) {
            put(USR_DOMAIN, usrDomain);
        }

        public enum MessageType {
            CHAT, JOIN, LEAVE
        }

        public MessageType getType() {
            return MessageType.valueOf(getString(TYPE));
        }

        public void setType(MessageType type) {
            put(TYPE, type.name());
        }

        public String getContent() {
            return getString(CONTENT);
        }

        public void setContent(String content) {
            put(CONTENT, content);
        }

        public String getSender() {
            return getString(SENDER);
        }

        public void setSender(String sender) {
            put(SENDER, sender);
        }

        public Date getCreatedAt() {
            return getDate(CREATED_AT);
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            put(CREATED_AT, createdAt);
        }
}

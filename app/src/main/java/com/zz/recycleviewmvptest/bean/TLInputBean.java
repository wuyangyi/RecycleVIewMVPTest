package com.zz.recycleviewmvptest.bean;

/**
 * 图灵机器人输入对象
 */
public class TLInputBean {


    /**
     * reqType : 0
     * perception : {"inputText":{"text":"你叫什么"}}
     * userInfo : {"apiKey":"c00282de107144fb940adab994d9ff98","userId":"225167"}
     */

    private int reqType;
    private PerceptionBean perception;
    private UserInfoBean userInfo;

    public TLInputBean(String content) {
        reqType = 0;
        UserInfoBean user = new UserInfoBean();
        user.setUserId("484225");
        user.setApiKey("73808218b0544165a57901cb4c4cf5f7");
        this.userInfo = user;

        PerceptionBean p = new PerceptionBean();
        PerceptionBean.InputTextBean in = new PerceptionBean.InputTextBean();
        in.setText(content);
        p.setInputText(in);
        this.perception = p;
    }

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public PerceptionBean getPerception() {
        return perception;
    }

    public void setPerception(PerceptionBean perception) {
        this.perception = perception;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class PerceptionBean {
        /**
         * inputText : {"text":"你叫什么"}
         */

        private InputTextBean inputText;

        public InputTextBean getInputText() {
            return inputText;
        }

        public void setInputText(InputTextBean inputText) {
            this.inputText = inputText;
        }

        public static class InputTextBean {
            /**
             * text : 你叫什么
             */

            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }

    public static class UserInfoBean {
        /**
         * apiKey : c00282de107144fb940adab994d9ff98
         * userId : 225167
         */

        private String apiKey;
        private String userId;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}

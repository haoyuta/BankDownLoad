package com.item.itm;

/**
 * 账户类
 */
public class Account {
    /**
     * 成员变量
     */
    private String cardId;//卡账户
    private String userName;//用户名
    private String passWord;//账户密码
    private double money;//账户余额
    private double quotaMoney;//每次取现额度

    /**
     * 成员方法
     */

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }
}


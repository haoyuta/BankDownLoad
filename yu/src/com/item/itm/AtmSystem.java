package com.item.itm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AtmSystem {
    public static void main(String[] args) {
        //1.定义账户类
        //2.定义一个accounts集合容器，负责以后储存全部账户对象，进行相关的业务操作。
        ArrayList<Account> accounts = new ArrayList<>();
        //3.展示系统首页
        while (true) {
            System.out.println("===========文豪ATM系统===========");
            System.out.println("1.账户登录");
            System.out.println("2.账户开户");


            Scanner input = new Scanner(System.in);
            System.out.println("请选择操作:");
            int command = input.nextInt();
            switch (command) {
                case 1:
                    //用户登录操作
                    Login(accounts, input);
                    break;
                case 2:
                    //用户开户操作
                    register(accounts, input);
                    break;
                default:
                    System.out.println("对不起，您输入操作不存在!");
            }
        }
    }
    /**
     * 用户登录功能的实现
     *
     * @param accounts
     */
    private static void Login(ArrayList<Account> accounts, Scanner input) {
        System.out.println("===========用户登录操作===========");
        //1.判断账户集合中是否存在账户，如果不存在账户，登录功能无法执行
        if (accounts.size() == 0) {
            System.out.println("对不起，系统中不存在账户，请先开户在进行登录!");
            return; //卫语言风格，解决方法的执行
        }
        //2.正式进入登录操作
        System.out.println("请您输入登录卡号:");
        String cardId = input.next();
        //3.判断卡号是否存在，根据卡号去账户集合查询账户对象
        for (int i = 0; i < accounts.size(); i++) {
            Account login = accounts.get(i);
            int m = i; //为注销账户功能作准备
            if (login.getCardId().equals(cardId)) {
                while (true) {
                    System.out.println("请您输入登录密码:");
                    String passWord = input.next();
                    if (login.getPassWord().equals(passWord)) {
                        System.out.println(login.getUserName() + "贵宾，欢迎您进入系统，您的卡号:" + login.getCardId());
                        //登录成功后进入用户操作界面，实现用户操作功能
                        controlSystem(accounts,input,login,m);
                        return;
                    } else {
                        System.out.println("您输入的密码有误，请确认!");
                    }
                }
            }
        }
        System.out.println("不存在该卡号!");

    }
    /**
     * 用户开户功能的实现
     *
     * @param accounts 接收的账户集合
     */
    private static void register(ArrayList<Account> accounts, Scanner input) {
        System.out.println("===========系统开户操作===========");
        //1.创建account对象用于封装账户信息
        Account account = new Account();
        //2.录入当前这个账户的信息，注入到账户对象中去
        System.out.println("请输入用户名:");
        String userName = input.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("请输入账户密码:");
            String passWord = input.next();
            System.out.println("请输入确认账户密码:");
            String okpassWord = input.next();
            if (passWord.equals(okpassWord)) {
                //密码认证通过，可以注入账户对象
                account.setPassWord(okpassWord);
                System.out.println("设置密码成功!");
                break;//密码已经录入成功了，死循环没有必要再继续
            } else {
                System.out.println("再次输入的密码有误，请重新设置账户密码!");
            }

        }

        System.out.println("请输入账户当次限额:");
        double quotaMoney = input.nextDouble();
        account.setQuotaMoney(quotaMoney);

        /*
        难点，可反复研究
         */
        //为账户随机一个8位且与其他账户不重复的号码(独立功能，独立成方法)
        String cardId = getRandomCardId(accounts);//定义一个变量来接一下返回值
        account.setCardId(cardId);


        //3.把账户对象添加到账户集合中去
        accounts.add(account);
        System.out.println("恭喜您," + account.getUserName() + "先生/女士开户成功!");
        System.out.println("您的卡号是" + account.getCardId() + "请妥善保管!");
    }

    /**
     * //为账户生成8为与其他账户不同的号码
     *
     * @return
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        //1.随机生成一个8位号码
        Random sj = new Random();
        while (true) {
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                cardId += sj.nextInt(9);
            }
            //2.判断随机号码是否与accounts集合中账户号码重复(独立功能,独立成方法)
            if (getAccountByCardId(cardId, accounts) == null) {
                //说明随机号码可行
                return cardId;
            }

        }
    }

    /**
     * 判断随机号码是否与accounts集合中账户号码重复
     *
     * @param cardId
     * @param accounts
     * @return
     */
    private static Account getAccountByCardId(String cardId, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }
        }
        return null;//查无此账户号码
    }

    /**
     * 登录成功后用户操作界面
     *
     * @param accounts
     * @param input
     */
    private static void controlSystem(ArrayList<Account> accounts, Scanner input, Account login, int m) {
        while (true) {
            System.out.println("===========欢迎您进入黑马银行用户操作界面===========");
            System.out.println("1.查询");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.退出");
            System.out.println("7.注销当前账户");
            System.out.println("您可继续选择功能继续操作了:");
            int command = input.nextInt();
            switch (command) {
                case 1:
                    //查询操作
                    findUserMessage(accounts, input);
                    break;
                case 2:
                    //存款操作
                    depositOpreate(input, login);
                    break;
                case 3:
                    //取款操作
                    withdrawOperate(input, login);
                    break;
                case 4:
                    //转帐
                    transferAccounts(accounts, input, login);
                    break;
                case 5:
                    //修改密码
                    changePassword(input, login);
                    break;
                case 6:
                    //退出
                    return;
                case 7:
                    //注销当前账户
                    boolean judge = closeAnAccount(accounts, input, login, m);
                    if (judge == true) {
                        return;
                    }
                    break;
                default:
                    System.out.println("对不起，您输入操作不存在!");
            }
        }


    }


    /**
     * 查询用户账户信息功能
     *
     * @param accounts
     * @param input
     */
    private static void findUserMessage(ArrayList<Account> accounts, Scanner input) {
        System.out.println("请你输入需要查询的账户的卡号:");
        String cardId = input.next();
        Account acc = getAccountByCardId(cardId, accounts);
        if (acc == null) {
            System.out.println("非常抱歉，系统中不存在账户");
            return;
        }
        System.out.println("===========欢迎您进入黑马银行用户详情界面===========");
        System.out.println("您的账户信息如下:");
        System.out.println("卡号:" + acc.getCardId());
        System.out.println("姓名:" + acc.getUserName());
        System.out.println("余额:" + acc.getMoney());
        System.out.println("当次取现额度:" + acc.getQuotaMoney());

    }

    /**
     * 存款操作
     *
     * @param input
     * @param login
     */

    private static void depositOpreate(Scanner input, Account login) {
        System.out.println("请输入你要存入的金额:");
        double deposit = input.nextDouble();
        login.setMoney(deposit);
        System.out.println("恭喜你存款成功，存款金额为:" + login.getMoney());
    }

    /**
     * 取款操作
     *
     * @param input
     * @param login
     */
    private static void withdrawOperate(Scanner input, Account login) {
        if (login.getMoney() == 0) {
            System.out.println("对不起，当前您余额为0，无法取款");
            return;
        }

        System.out.println("请输入你要取款的金额:");
        double withdraw = input.nextDouble();
        if (withdraw <= login.getQuotaMoney()) {
            if (login.getMoney() - withdraw >= 0) {
                login.setMoney(login.getMoney() - withdraw);
                System.out.println("恭喜你取款成功，取款金额为:" + withdraw + "   " + "当前余额为:" + login.getMoney());
                return;
            }
        } else {
            System.out.println("对不起，当前取现余额为" + login.getQuotaMoney() + "小于您的取现金额，无法一次性取现这么多");
            return;
        }
        System.out.println("对不起，您的余额为:" + login.getMoney() + "   " + "余额不足，无法取这么多钱!");

    }

    /**
     * 修改密码操作
     *
     * @param input
     * @param login
     */
    private static void changePassword(Scanner input, Account login) {
        while (true) {
            System.out.println("请输入你想要修改的密码:");
            String passWord = input.next();
            System.out.println("请输入确认账户密码:");
            String okpassWord = input.next();
            if (passWord.equals(okpassWord)) {
                //密码认证通过，可以注入账户对象
                login.setPassWord(okpassWord);
                System.out.println("修改密码成功!");
                return;//密码已经录入成功了，死循环没有必要再继续
            } else {
                System.out.println("再次输入的密码有误，请重新设置账户密码!");
            }
        }

    }

    /**
     * 转账操作
     *
     * @param accounts
     * @param input
     * @param login
     */
    private static void transferAccounts(ArrayList<Account> accounts, Scanner input, Account login) {
        System.out.println("请输入下想要转账用户的卡号:");
        String transferUserCardId = input.next();
        Account acc = getAccountByCardId(transferUserCardId, accounts);
        if (acc == null) {
            System.out.println("对不起，没有此用户信息，无法完成转账!");
            return;
        }
        System.out.println("请输入想转账的金额:");
        double transferAccounts = input.nextDouble();
        if (login.getMoney() - transferAccounts >= 0) {
            acc.setMoney(transferAccounts);
            login.setMoney(login.getMoney() - transferAccounts);
            System.out.println("恭喜你转账成功，转账金额为:" + transferUserCardId + "   " + "当前您余额为:" + login.getMoney());
            return;
        }

        System.out.println("对不起，您的余额为:" + login.getMoney() + "   " + "余额不足，无法转这么多钱!");

    }

    /**
     * 注销当前账户操作
     *
     * @param accounts
     * @param m
     */
    private static boolean closeAnAccount(ArrayList<Account> accounts, Scanner input, Account login, int m) {
        System.out.println("请问您真的要注销账户吗!");
        System.out.println("请您输入回答:(y/n)");
        String answer = input.next();
        switch (answer) {
            case "y":
                if (login.getMoney() == 0) {
                accounts.remove(m);
                System.out.println("当前账户注销成功，感谢您的使用!");
                return true;
            }
            System.out.println("对不起，您的账户还剩余额" + login.getMoney() + ",无法为您注销账户!");
            return false;
            default:
                return false;
        }
    }
}


/****
 * 心得:1.写一个系统，要明白它的功能，要懂得把大的事物拆分成很多小的事物，一步一步进行
 * 2.要善于使用switch，if，for，while语句
 * 3.在方法中定义的对象，每次方法的结束，重新开始后又会重新建立一个对象，与上一次的是不同的
 * 4.当if语句行不通时，可以考虑switch语句
 * 5.要善于使用注释，帮自己理清思绪
 *6.每个方法定义的变量相对独立，即使同名也不一样，如果同一个方法要出现同名可以考虑this方法，用来区分对象变量和定义的变量
 *7.定义变量的时候尽量考虑与项目相关的名称，可以考虑英语单词
 */




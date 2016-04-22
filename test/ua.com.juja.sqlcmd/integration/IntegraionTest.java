package ua.com.juja.sqlcmd.integration;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


/**
 * Created by Юлия on 22.04.2016.
 */
public class IntegraionTest {

    private  ConfigurableInputStream in;
    private  ByteArrayOutputStream out;

    @Before
    public void setup(){
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp(){
        //given
        in.add("help");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                "Существующие команды: \r\n" +
                "\tconnect|databaseName|userName|password\r\n" +
                "\t\tдля подключения к базе данныхб с которой будем работать.\r\n" +
                "\tlist\r\n" +
                "\t\tдля получения списка все таблиц базы, к которой подключились.\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tдля получения содержимого таблицы 'tableName'.\r\n" +
                "\thelp\r\n" +
                "\t\tдля вывода этого списка на экран.\r\n" +
                "\texit\r\n" +
                "\t\tдля выхода из программы.\r\n" +
                "Введи команду или help для помощи: \r\n" +
                "До скорой встречи!\r\n", getData());
    }

    public String getData() {
        try {
            return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testExit(){
        //given

        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testListWithoutConnect(){
        //given
        in.add("list");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                //list
                "Вы не можете пользоватся командой 'list' пока не подключитесь с помощью команды connect|databaseName|userName|password\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindWithoutConnect(){
        //given
        in.add("find|user");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                //find
                "Вы не можете пользоватся командой 'find|user' пока не подключитесь с помощью команды connect|databaseName|userName|password\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testUnsupported(){
        //given
        in.add("unsupported");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                //unsupported
                "Вы не можете пользоватся командой 'unsupported' пока не подключитесь с помощью команды connect|databaseName|userName|password\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect(){
        //given
        in.add("connect|sqlcmd|postgres|12345678");
        in.add("unsupported");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                //connect
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                "Успех!\r\n"+"Введи команду или help для помощи: \r\n"+
                //unsupported
                "Несуществующая команда: unsupported\r\n" + "Введи команду или help для помощи: \r\n"+
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testListAfterConnect(){
        //given
        in.add("connect|sqlcmd|postgres|12345678");
        in.add("list");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                //connect
                "Успех!\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //list
                "[user, test]\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect(){
        //given
        in.add("connect|sqlcmd|postgres|12345678");
        in.add("find|user");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                //connect
                "Успех!\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //find|user
                "---------------------------\r\n" +
                "| name | password | id | \r\n" +
                "---------------------------\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConectAfterConnect(){
        //given
        in.add("connect|sqlcmd|postgres|12345678");
        in.add("list");
        in.add("connect|test|postgres|12345678");
        in.add("list");
        in.add("exit");

        //when
        ua.com.juja.sqlCmd.controller.Main.main(new String[0]);

        //then
        assertEquals("Привет пользователь!\r\n" +
                "Введи, пожалуйста, имя базы данных, имя пользователя и " +
                "пароль в формате: connect|database|userName|password\r\n" +
                //connect sqlcmd
                "Успех!\r\n" +
                "Введи команду или help для помощи: \r\n"+
                //list
                "[user, test]\r\n" +
                "Введи команду или help для помощи: \r\n" +
                //connect test
                "Успех!\r\n" +
                "Введи команду или help для помощи: \r\n" +
                //list
                "[test2]\r\n"+
                "Введи команду или help для помощи: \r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }
}

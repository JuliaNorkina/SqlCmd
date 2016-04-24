package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlCmd.view.View;

/**
 * Created by Julia on 24.04.2016.
 */
public class FakeView implements View {

    private String messages = "";
    private Object content;

    @Override
    public void write(String message) {
        messages += message + "\n";
    }

    @Override
    public String read() {
        return null;
    }

    public String  getContent() {
        return messages;
    }
}

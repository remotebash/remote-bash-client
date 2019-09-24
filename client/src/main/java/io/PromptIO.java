package io;

import com.sun.jna.Platform;
import modal.Command;

import java.util.Date;
import java.util.Scanner;

public class PromptIO {

    public Command exec(Command command) {
        switch (command.getPlatform()) {
            case WINDOWS:
                return execOnWindows(command);
            case LINUX:
            case MACOSX:
            case SOLARIS:
            case FREEBSD:
            case UNKNOWN:
            default:
                throw new UnsupportedOperationException(String.format("Operating system not supported: %1$d", Platform.getOSType()));
        }
    }

    private Command execOnWindows(Command command) {
        try {
            Scanner in = new Scanner(Runtime.getRuntime().exec(command.getCommand()).getInputStream(), "ISO-8859-1");
            while (in.hasNext()) {
                command.setResult(in.nextLine());
            }
            command.setEnd(new Date());
            return command;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }
}
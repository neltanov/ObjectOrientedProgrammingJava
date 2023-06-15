package ru.nsu.fit.neltanov;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TimerTask;

class HeartbeatTask extends TimerTask {
    private final ObjectOutputStream outputStream;
    private final String clientName;
    public HeartbeatTask(ObjectOutputStream outputStream, String clientName) {
        this.outputStream = outputStream;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try {
            Message heartbeat = new Message(clientName, "Heartbeat");
            outputStream.writeObject(heartbeat);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error sending test message. The connection is lost.");
            System.exit(0);
        }
    }
}

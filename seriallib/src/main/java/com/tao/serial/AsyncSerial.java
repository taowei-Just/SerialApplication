package com.tao.serial;

import com.tao.utilslib.log.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSerial extends BaseSerial<SerialData> {
    private ExecutorService singlePool;
    SerialCall serialCall;
    List<SerialDataTask> taskList = new ArrayList<>();
    Mode mode = Mode.signleMode;
    private String tag = getClass().getSimpleName();

    public AsyncSerial(String serialPath, int baudrate, SerialCall serialCall) throws Exception {
        super(serialPath, baudrate);
        this.serialCall = serialCall;
        singlePool = Executors.newSingleThreadExecutor();
    }

    public AsyncSerial setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public void setSerialCall(SerialCall serialCall) {
        this.serialCall = serialCall;
    }

    static int id = 0;

    public static int getId() {
        return ++id;
    }

    public void send(SerialData data) {
        try {
            SerialDataTask task = new SerialDataTask(this, data, serialCall);
            if (mode == Mode.signleMode)
                closeTask();
            singlePool.submit(task);
            taskList.add(task);
        } catch (Exception e) {
            e.printStackTrace();
            serialCall.onSendError(data);
        }
    }

    public void send(List<SerialData> dataS) {
        mode = Mode.listMode;
        for (SerialData data : dataS) {
            send(data);
        }
    
        LogUtil.e(tag, " thread  over ");
    }

    @Override
    public void close() {
        closed = true;
        super.close();
        closeTask();
        singlePool.shutdownNow();
        singlePool = Executors.newSingleThreadExecutor();
        taskList.clear();
        closed = false;
    }
    boolean closed = false;
    public void closeTask() {
        for (SerialDataTask task : taskList) {
            if (task != null)
                task.close();
        }
    }

    @Override
    public void onOver(Runnable runnable) {
        if (taskList.contains(runnable) && !closed) {
            ((SerialDataTask) runnable).close();
            taskList.remove(runnable);
        }
    }


    public enum Mode {
        signleMode, listMode;
    }
}

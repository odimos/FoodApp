package com.example.foodapp.repository;

import androidx.annotation.NonNull;

import data.Answer;
import data.Client;
import data.Pending;
import data.Store;
import data.Task;

import data.GlobalConfig;
import data.ResponseHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketRepository implements ResponseHandler, StoreRepository {

    private final Map<Integer, Pending> pendings = new HashMap<Integer, Pending>();
    private int taskIDcount=0;
    public Pending getPending(int id) {
        synchronized (pendings) {
            return pendings.get(id);
        }
    }
    public void addPending(int id, Pending p) {
        synchronized (pendings) {
            pendings.put(id, p);
        }
    }
    public synchronized int nextID(){
        taskIDcount++;
        return taskIDcount;
    }
    public void sendToMaster(Task task)  {
        (new Client<SocketRepository>(task, GlobalConfig.MASTER_HOST_IP, GlobalConfig.MASTER_PORT_FOR_CLIENTS, this))
                .start();
    }

    @NonNull
    public List<Store> getStores(){
        int localTaskID = nextID();
        Task req = new Task(localTaskID, GlobalConfig.FILTER_STORES, true,
                Map.of(
                        "category", "",
                        "stars", 0,
                        "price", "",
                        "latitude", 0.0,
                        "longitude", 0.0
                )
        );


        Pending pending = new Pending();
        addPending(localTaskID, pending);
        req.clientTaskID = localTaskID;
        System.out.println("Sending Request to Master");
        sendToMaster(req);
        Answer answer = pending.waitForAnswer();

        System.out.println("Sending Response back to UI"+answer);
        return (List<Store>) answer.arguments.get("result");
    }
    @Override
    public void handleResponseFromServer(Answer res) {
        int localTaskID = res.clientTaskID;
        System.out.println("Receives response from Master ");
        Pending pending = getPending(localTaskID);
        System.out.println("Pending: "+pending);
        pending.setAnswer(res);
    }

    @Override
    public Store getStoreById(int id) {
        return null;
    }
}

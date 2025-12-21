package com.example.foodapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import data.Answer;
import data.Client;
import data.Pending;
import data.Store;
import data.Task;

import data.GlobalConfig;
import data.ResponseHandler;

import java.io.Serializable;
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
    public List<Store> getStores(
            @NonNull List<String> categories,
            @NonNull List<Integer> stars,
            @NonNull List<String> prices,
            double lat, double lon
    ){
        int localTaskID = nextID();
        Task req = new Task(localTaskID, GlobalConfig.FILTER_STORES, false,
                Map.of(
                        "categories", (Serializable) new java.util.ArrayList<>(categories),
                        "stars",      (Serializable) new java.util.ArrayList<>(stars),
                        "price",      (Serializable) new java.util.ArrayList<>(prices),
                        "latitude", lat,
                        "longitude", lon
                )
        );


        Pending pending = new Pending();
        addPending(localTaskID, pending);
        req.clientTaskID = localTaskID;
        System.out.println("Sending Request to Master");
        sendToMaster(req);
        Answer answer = pending.waitForAnswer();

        //System.out.println("Sending Response back to UI"+answer);
        return (List<Store>) answer.arguments.get("result");
    }

    @NonNull
    @Override
    public Answer buy(
            @NonNull String storeName, @NonNull String productName, int quantity
    ){
        int localTaskID = nextID();
        Task req = new Task(localTaskID, GlobalConfig.BUY, true,
                Map.of(
                        "storeName", storeName,
                        "productName", productName,
                        "quantity", quantity
                )
        );
        Pending pending = new Pending();
        addPending(localTaskID, pending);
        System.out.println("req "+req);
        req.clientTaskID = localTaskID;
        Log.d("DEB","Sending Request to Master");
        sendToMaster(req);
        Answer answer = pending.waitForAnswer();

        Log.d("DEB","Sending Response back to UI"+answer);
        return answer;
    }
    @Override
    public void handleResponseFromServer(Answer res) {
        int localTaskID = res.clientTaskID;
        System.out.println("Receives response from Master ");
        Pending pending = getPending(localTaskID);
        System.out.println("LocalID: "+localTaskID);
        System.out.println("Pending: "+pending);
        pending.setAnswer(res);
    }

    @Override
    public Store getStoreById(int id) {
        return null;
    }


    @Override
    public @NotNull Answer rate(@NotNull String storeName, int stars) {
        Log.d("DEB","Rate");
        int localTaskID = nextID();
        Task req = new Task(0, GlobalConfig.RATE, true,
                Map.of(
                        "storeName", storeName,
                        "stars", stars
                )
        );
        Pending pending = new Pending();
        addPending(localTaskID, pending);
        System.out.println("req "+req);
        req.clientTaskID = localTaskID;
        Log.d("DEB","Sending Request to Master");
        sendToMaster(req);
        Answer answer = pending.waitForAnswer();

        Log.d("DEB","Sending Response back to UI"+answer);
        return answer;
    }
}

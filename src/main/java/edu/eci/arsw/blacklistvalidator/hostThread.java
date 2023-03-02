package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class hostThread extends Thread{

    private Integer count;

    private int init;

    private int finish;

    private HostBlacklistsDataSourceFacade skds;

    private String ipaddress;

    private Object op;

    private boolean sleep = false;
    public hostThread(int init, int finish, HostBlacklistsDataSourceFacade skds, String ipaddress, Object op){
        this.init = init;
        this.finish = finish;
        this.skds = skds;
        this.count = new Integer(0);
        this.ipaddress = ipaddress;
        this.op = op;

    }


    public Integer getCount() {
        return count;
    }

    public void setCount( int count) {
        this.count = count;
    }

    public int getInit() {
        return init;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public HostBlacklistsDataSourceFacade getSkds() {
        return skds;
    }

    public void setSkds(HostBlacklistsDataSourceFacade skds) {
        this.skds = skds;
    }


    @Override
    public void run(){
        for (int i=this.init;i<=this.finish;i++){
            if (this.skds.isInBlackListServer(i, this.ipaddress)){
                synchronized (op){
                    if(sleep){
                        op.wait();
                    }

                }

                this.count += 1;
            }
        }
    }
}

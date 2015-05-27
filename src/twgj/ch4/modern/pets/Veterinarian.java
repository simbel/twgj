package twgj.ch4.modern.pets;

import java.util.concurrent.BlockingDeque;

/**
 * Created by pramodh on 3/14/14.
 */
public class Veterinarian extends Thread {
    protected final BlockingDeque<Appointment<Pet>> appointments;
    protected String text = "";
    protected final int restTime;
    private boolean shutdown = false;

    public Veterinarian(BlockingDeque<Appointment<Pet>> blq, int restTime){
        this.appointments = blq;
        this.restTime = restTime;
    }

    public synchronized void shutdown(){
        shutdown = true;
    }

    @Override
    public void run() {
        while(!shutdown){
            seePatient();
            try{
                Thread.sleep(restTime);
            }catch (InterruptedException ex){
                shutdown = true;
            }
        }
    }

    public void seePatient(){
        try {
            Appointment<Pet> appointment = this.appointments.take();
            Pet patient = appointment.getPatient();
            patient.examine();
        } catch (InterruptedException e) {
            shutdown = true;
        }
    }

    public static void main(String[] args) {

    }
}
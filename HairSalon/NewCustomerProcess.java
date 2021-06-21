import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

import java.util.Random;

public class NewCustomerProcess extends SimProcess {

    private HairSalon myModel;
    private int id = 0;

    public NewCustomerProcess(Model owner, String name, boolean showInTrace){

        super(owner, name, showInTrace);
        myModel = (HairSalon) owner;
    }
    private boolean getdyeHair(){
        int min = 0;
        int max = 10;

        int value = (int) (Math.random()*(max-min+1)+min);
        if(value > 8){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void lifeCycle() throws SuspendExecution {
        while (true) {
            hold(new TimeSpan(myModel.getCustomerArivalTime()));

            CustomerProcess newCustomer = new CustomerProcess(myModel,"Customer", id++,"",getdyeHair(), true);

            newCustomer.activateAfter(this);
        }

    }
}

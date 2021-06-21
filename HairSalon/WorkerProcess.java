import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class WorkerProcess extends SimProcess {

    HairSalon myModel;

    public WorkerProcess(Model owner, String name, boolean showInTrace) {

        super(owner, name, showInTrace);

        myModel = (HairSalon) owner;
    }


    @Override
    public void lifeCycle() throws SuspendExecution {
        while (true){

            if(myModel.customerQueue.isEmpty()){
                passivate();
            } else if(myModel.getActiveCustomerprocess().equals("A")){

                if(myModel.soakInQueueA.isEmpty()){

                    CustomerProcess customer = myModel.customerQueue.first();
                    myModel.customerQueue.remove(customer);

                    if(customer.dyeHair){
                        myModel.soakInQueueA.insert(customer);
                    }

                    hold(new TimeSpan(myModel.getServeTime()));

                    myModel.freeWorkerQueueA.insert(this);

                    customer.activate(new TimeSpan(0.0));
                } else {
                    CustomerProcess customer = myModel.soakInQueueA.first();
                    myModel.soakInQueueA.remove(customer);

                    hold(new TimeSpan(myModel.getServeTime()));

                    myModel.freeWorkerQueueA.insert(this);

                    sendTraceNote("Customer has a new haircolor");

                    customer.activate(new TimeSpan(0.0));

                }

            } else if(myModel.getActiveCustomerprocess().equals("B")){
                if( myModel.soakInQueueB.isEmpty()){

                    CustomerProcess customer = myModel.customerQueue.first();
                    myModel.customerQueue.remove(customer);

                    if(customer.dyeHair){
                        myModel.soakInQueueB.insert(customer);
                    }

                    hold(new TimeSpan(myModel.getServeTime()));

                    myModel.freeWorkerQueueB.insert(this);

                    customer.activate(new TimeSpan(0.0));
                } else {
                    CustomerProcess customer = myModel.soakInQueueB.first();
                    myModel.soakInQueueB.remove(customer);

                    hold(new TimeSpan(myModel.getServeTime()));

                    myModel.freeWorkerQueueB.insert(this);

                    sendTraceNote("Customer has a new haircolor");

                    customer.activate(new TimeSpan(0.0));


                }
            } else if(myModel.getActiveCustomerprocess().equals("C")){
                if(myModel.soakInQueueC.isEmpty()){

                    CustomerProcess customer = myModel.customerQueue.first();
                    myModel.customerQueue.remove(customer);

                    if(customer.dyeHair){
                        myModel.soakInQueueC.insert(customer);
                    }

                    hold(new TimeSpan(myModel.getServeTime()));

                    myModel.freeWorkerQueueC.insert(this);

                    customer.activate(new TimeSpan(0.0));
                } else {
                    CustomerProcess customer = myModel.soakInQueueC.first();
                    myModel.soakInQueueC.remove(customer);

                    hold(new TimeSpan(myModel.getServeTime()));

                    myModel.freeWorkerQueueC.insert(this);

                    sendTraceNote("Customer has a new haircolor");

                    customer.activate(new TimeSpan(0.0));

                }
            } else {
                passivate();
            }


      /*      if (myModel.customerQueue.isEmpty()){
                myModel.freeWorkerQueueA.insert(this);
                passivate();

            }else {
                CustomerProcess customer = myModel.customerQueue.first();
                myModel.customerQueue.remove(customer);

                hold(new TimeSpan(myModel.getServeTime()));

                customer.activate(new TimeSpan(0.0));

            }

       */
        }

    }
}

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class CustomerProcess extends SimProcess {

    private HairSalon myModel;
    private int id = 0;
    String employeeid;
    public boolean dyeHair;

    public CustomerProcess(Model owner, String name, int id, String employeeid, boolean dyeHair , boolean showInTrace){
        super(owner, name, showInTrace);

        myModel = (HairSalon) owner;
        this.id = id;
        this.employeeid = employeeid;
        this.dyeHair = dyeHair;
    }

    public int getId(){return id;}
    public String getEmployeeid(){return employeeid;}
    public void setEmployeeid(String id) {employeeid = id;}


    @Override
    public void lifeCycle() throws SuspendExecution {
        myModel.customerQueue.insert(this);

        sendTraceNote("Length of the customerqueue: " +
                myModel.customerQueue.length());

        if(!myModel.freeWorkerQueueA.isEmpty()){
            setEmployeeid("A");
            myModel.setActiveCustomerprocess("A");

            WorkerProcess workerProcess = myModel.freeWorkerQueueA.first();

            myModel.freeWorkerQueueA.remove(workerProcess);

            workerProcess.activateAfter(this);

            passivate();

        } else if (!myModel.freeWorkerQueueB.isEmpty()){
            setEmployeeid("B");
            myModel.setActiveCustomerprocess("B");

            WorkerProcess workerProcess = myModel.freeWorkerQueueB.first();

            myModel.freeWorkerQueueB.remove(workerProcess);

            workerProcess.activateAfter(this);

            passivate();

        }else if (!myModel.freeWorkerQueueC.isEmpty()){
            setEmployeeid("C");
            myModel.setActiveCustomerprocess("C");


            WorkerProcess workerProcess = myModel.freeWorkerQueueC.first();

            myModel.freeWorkerQueueC.remove(workerProcess);

            workerProcess.activateAfter(this);

            passivate();

        } else {
            passivate();
        }

        sendTraceNote("Customer has a new haircut from " + getEmployeeid() + " and leaves the hair salon");

    }
}

import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.*;

public class HairSalon extends Model {


    protected ProcessQueue <CustomerProcess> customerQueue;

    protected ProcessQueue <WorkerProcess> freeWorkerQueueA;
    protected ProcessQueue <WorkerProcess> freeWorkerQueueB;
    protected ProcessQueue <WorkerProcess> freeWorkerQueueC;

    protected ProcessQueue <CustomerProcess> soakInQueueA;
    protected ProcessQueue <CustomerProcess> soakInQueueB;
    protected ProcessQueue <CustomerProcess> soakInQueueC;

    private ContDistExponential customerArrivalTime;
    private ContDistUniform serveTime;

    private String activeCustomerprocess;

    public void setActiveCustomerprocess(String activeCustomerprocess) {
        this.activeCustomerprocess = activeCustomerprocess;
    }

    public String getActiveCustomerprocess() {
        return activeCustomerprocess;
    }

    public double getCustomerArivalTime() {
        return customerArrivalTime.sample();
    }

    public double getServeTime() {
        return serveTime.sample();
    }


    public HairSalon(Model model, String name, boolean showInReport, boolean showIntrace) {
        super(model, name, showInReport, showIntrace);
    }

    @Override
    public String description() {
        return "Ein Friseursalon hat 3 Angestellte: A, B, C. Kunden kommen im Mittel alle 20min an, die Bedienung dauert" +
                " im Durchschnitt 30min. Von den Kunden wird jedoch A gegenüber B bevorzugt und B gegenüber C. " +
                "Ist die oder der gewünschte Angestellte nicht frei, so wird die oder der nächste frei werdende Angestellte " +
                "ausgewählt.";
    }

    @Override
    public void doInitialSchedules() {

        NewCustomerProcess newCustomer =
                new NewCustomerProcess(this,"Customer creation",true );

        newCustomer.activate(new TimeSpan(0.0));


        WorkerProcess workerA = new WorkerProcess(this, "A", true);
        WorkerProcess workerB = new WorkerProcess(this, "B", true);
        WorkerProcess workerC = new WorkerProcess(this, "C", true);

        freeWorkerQueueA.insert(workerA);
        freeWorkerQueueB.insert(workerB);
        freeWorkerQueueC.insert(workerC);

        workerA.activate(new TimeSpan(0.0));
        workerB.activate(new TimeSpan(0.0));
        workerC.activate(new TimeSpan(0.0));

    }

    @Override
    public void init() {
        customerArrivalTime = new ContDistExponential(this, "arrival time interval", 20.0, true, true);
        customerArrivalTime.setNonNegative(true);

        serveTime = new ContDistUniform(this, "Serve Time",20.0, 50.0, true, true);

        customerQueue = new ProcessQueue<>(this, "Customer Queue", true, true);

        freeWorkerQueueA = new ProcessQueue<>(this, "Free Worker Queue A", true, true);
        freeWorkerQueueB = new ProcessQueue<>(this, "Free Worker Queue B", true, true);
        freeWorkerQueueC = new ProcessQueue<>(this, "Free Worker Queue C", true, true);

        soakInQueueA =  new ProcessQueue<>(this, "Soak in Queue A", true, true);
        soakInQueueB =  new ProcessQueue<>(this, "Soak in Queue B", true, true);
        soakInQueueC =  new ProcessQueue<>(this, "Soak in Queue C", true, true);


    }

    public static void main(java.lang.String[] args) {
        Experiment hairSalonExperiment =
                new Experiment("HairSalon-Prozess");

        HairSalon hairSalonModel =
                new HairSalon(null,"HairSalon Model",true,true);

        hairSalonModel.connectToExperiment(hairSalonExperiment);

        hairSalonExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(1000));
        hairSalonExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(1000));

        hairSalonExperiment.stop(new TimeInstant(10080));

        hairSalonExperiment.start();
        hairSalonExperiment.report();
        hairSalonExperiment.finish();

    }
}

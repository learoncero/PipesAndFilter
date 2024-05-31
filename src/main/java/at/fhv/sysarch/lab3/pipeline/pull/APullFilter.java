package at.fhv.sysarch.lab3.pipeline.pull;

public abstract class APullFilter<I, O> implements IPullFilter<I, O> {
    protected IPullPipe<I> predecessor;

    @Override
    public void setPipePredecessor(IPullPipe<I> pipePredecessor) {
        this.predecessor = pipePredecessor;
    }
}

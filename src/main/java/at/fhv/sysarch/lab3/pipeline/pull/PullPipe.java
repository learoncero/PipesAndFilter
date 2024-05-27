package at.fhv.sysarch.lab3.pipeline.pull;

public class PullPipe<I> implements IPullPipe<I> {

    private IPullFilter<?, I> predecessor;

    @Override
    public void setFilterPredecessor(IPullFilter<?, I> filterPredecessor) {
        this.predecessor = filterPredecessor;
    }

    @Override
    public I read() {
        return this.predecessor.read();
    }

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }
}
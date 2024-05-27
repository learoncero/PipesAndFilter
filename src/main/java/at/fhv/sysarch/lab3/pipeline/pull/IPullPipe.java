package at.fhv.sysarch.lab3.pipeline.pull;

public interface IPullPipe<I> {
    void setFilterPredecessor(IPullFilter<?, I> filterPredecessor);

    I read();
    boolean hasNext();
}

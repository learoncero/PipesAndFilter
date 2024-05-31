package at.fhv.sysarch.lab3.pipeline.pull;

import java.util.Optional;

public class Pipe<I> implements IPullPipe<I> {

    private IPullFilter<?, I> predecessor;

    @Override
    public void setFilterPredecessor(IPullFilter<?, I> filterPredecessor) {
        this.predecessor = filterPredecessor;
    }

    @Override
    public Optional<I> read() {
        return this.predecessor.read();
    }
}
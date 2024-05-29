package at.fhv.sysarch.lab3.pipeline.pull;

import java.util.Optional;

public interface IPullPipe<I> {
    void setFilterPredecessor(IPullFilter<?, I> filterPredecessor);

    Optional<I> read();
}

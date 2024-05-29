package at.fhv.sysarch.lab3.pipeline.pull;

import java.util.Optional;

public interface IPullFilter<I, O> {
    void setPipePredecessor(IPullPipe<I> pipePredecessor);

    Optional<O> read();

    O process(I data);
}

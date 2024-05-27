package at.fhv.sysarch.lab3.pipeline.pull;

public interface IPullFilter<I, O> {
    void setPipePredecessor(IPullPipe<I> pipePredecessor);

    O read();

    O process(I data);
    boolean hasNext();
}

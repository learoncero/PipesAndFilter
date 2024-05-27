package at.fhv.sysarch.lab3.pipeline.push;

public interface IPushPipe<I> {
    void setFilterSuccessor(IPushFilter<I,?> filterSuccessor);

    void write(I data);
}

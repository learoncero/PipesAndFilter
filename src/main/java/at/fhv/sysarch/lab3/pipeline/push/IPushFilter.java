package at.fhv.sysarch.lab3.pipeline.push;

public interface IPushFilter<I, O> {
    void setPipeSuccessor(IPushPipe<O> pipeSuccessor);

    void write(I data);

    O process(I data);
}

package at.fhv.sysarch.lab3.pipeline.push;

public interface IPushFilter<I, O> {

    public void setPipeSuccessor(IPushPipe<O> pipeSuccessor);

    public void write(I input);

    O process(I input);
}

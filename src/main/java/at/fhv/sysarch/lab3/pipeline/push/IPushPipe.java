package at.fhv.sysarch.lab3.pipeline.push;

public interface IPushPipe<I> {
    public void setFilterSuccessor(IPushFilter<I,?> filterSuccessor);

    public void write(I data);
}

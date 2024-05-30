package at.fhv.sysarch.lab3.pipeline.push;

public abstract class APushFilter<I, O> implements IPushFilter<I, O> {
    protected IPushPipe<O> successor;

    @Override
    public void setPipeSuccessor(IPushPipe<O> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }
}
